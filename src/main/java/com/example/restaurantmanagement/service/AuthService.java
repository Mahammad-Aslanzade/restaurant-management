package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.UserEntity;
import com.example.restaurantmanagement.exceptions.NotAllowedException;
import com.example.restaurantmanagement.mapper.UserMapper;
import com.example.restaurantmanagement.model.auth.LoginRequest;
import com.example.restaurantmanagement.model.auth.ResponseMessage;
import com.example.restaurantmanagement.model.auth.TokenResponse;
import com.example.restaurantmanagement.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    private final MyUserDetailService userDetailsService;
    private final UserService userService;
    private final UserMapper userMapper;


    public TokenResponse login(@RequestBody LoginRequest authenticationRequest , HttpServletResponse response) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new NotAllowedException(
                    "Incorrect username or password",
                    String.format("ACTION.ERROR.createAuthenticationToken request : %s", authenticationRequest)
            );
        }

        // Build our response and token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        UserEntity user = userService.getUserEntityByEmail(authenticationRequest.getEmail());

        return new TokenResponse(jwt, user.getRole());
    }

    public ResponseMessage logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return new ResponseMessage("You have been logged out successfully.");
    }

}


