package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.user.UserDto;
import com.example.restaurantmanagement.service.EmailVerificationService;
import com.example.restaurantmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/verificateEmail")
    public void verificateEmail(@RequestParam String email){
        emailVerificationService.verificateEmail(email);
    }
}
