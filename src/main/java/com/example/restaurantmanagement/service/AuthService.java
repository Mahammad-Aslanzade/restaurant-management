package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.UserEntity;
import com.example.restaurantmanagement.exceptions.NotAllowedException;
import com.example.restaurantmanagement.mapper.UserMapper;
import com.example.restaurantmanagement.model.auth.LoginRequest;
import com.example.restaurantmanagement.model.auth.TokenResponse;
import com.example.restaurantmanagement.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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


    public TokenResponse createAuthenticationToken(@RequestBody LoginRequest authenticationRequest , HttpServletResponse response) {

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

}


