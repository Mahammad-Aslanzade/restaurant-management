package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.auth.LoginRequest;
import com.example.restaurantmanagement.model.auth.ResponseMessage;
import com.example.restaurantmanagement.model.auth.TokenResponse;
import com.example.restaurantmanagement.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;
    @PostMapping("/login")
    public TokenResponse createAuthenticationToken(@RequestBody LoginRequest authenticationRequest , HttpServletResponse response) {
        return authService.login(authenticationRequest , response);
    }

    @PostMapping("/logout")
    public ResponseMessage logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return authService.logout(request, response , authentication);
    }



}
