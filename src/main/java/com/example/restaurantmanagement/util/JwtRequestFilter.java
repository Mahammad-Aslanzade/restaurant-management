package com.example.restaurantmanagement.util;

import com.example.restaurantmanagement.service.MyUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final MyUserDetailService userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;
    private final HashMap<String, AtomicInteger> requestCountsPerIpAddress = new HashMap<>();
    private final int REQUEST_LIMIT = 100;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String clientIp = request.getRemoteAddr();
        AtomicInteger count = requestCountsPerIpAddress.getOrDefault(clientIp, new AtomicInteger(0));

        if (count.incrementAndGet() > REQUEST_LIMIT) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            String message = String.format("Too many request from ip : %s", clientIp);
            response.getWriter().write("{ \"error\": \"" + message + "\" }");
            response.getWriter().flush();
        }

        requestCountsPerIpAddress.put(clientIp, count);

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtTokenUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        chain.doFilter(request, response);

        Executors.newSingleThreadScheduledExecutor().schedule(this::clearRequestHistoryInMinute, 1 , TimeUnit.MINUTES);

    }

    private void clearRequestHistoryInMinute(){
        requestCountsPerIpAddress.forEach((e , c)->{
            if(c.get() < REQUEST_LIMIT){
                c.set(0);
            }
        });
    }

}