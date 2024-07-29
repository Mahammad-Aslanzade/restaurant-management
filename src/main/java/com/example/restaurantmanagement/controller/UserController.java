package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.user.UserCreateDto;
import com.example.restaurantmanagement.model.user.UserDto;
import com.example.restaurantmanagement.model.user.UserUpdateDto;
import com.example.restaurantmanagement.service.EmailVerificationService;
import com.example.restaurantmanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final EmailVerificationService emailVerificationService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/verifyEmail")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String verifyEmail(@RequestParam String email) {
        return emailVerificationService.verifyEmail(email);
    }

    @PostMapping
    public void createUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        userService.createUser(userCreateDto);
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable String userId, @RequestBody @Valid UserUpdateDto userUpdateDto) {
        userService.updateUser(userId, userUpdateDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }


}
