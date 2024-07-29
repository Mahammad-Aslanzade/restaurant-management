package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.user.UserCreateDto;
import com.example.restaurantmanagement.model.user.UserDto;
import com.example.restaurantmanagement.service.EmailVerificationService;
import com.example.restaurantmanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;


import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final EmailVerificationService emailVerificationService;

    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/verifyEmail")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String verifyEmail(@RequestParam String email) {
        return emailVerificationService.verifyEmail(email);
    }

    @PostMapping
    public void createUser(@RequestBody @Valid UserCreateDto userCreateDto){
        userService.createUser(userCreateDto);
    }


}
