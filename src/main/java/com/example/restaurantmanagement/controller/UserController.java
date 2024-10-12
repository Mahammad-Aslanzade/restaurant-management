package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.enums.Role;
import com.example.restaurantmanagement.model.auth.ResetPassReqDto;
import com.example.restaurantmanagement.model.auth.ResponseMessage;
import com.example.restaurantmanagement.model.auth.UserEmailDto;
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

    @GetMapping("/customer")
    public List<UserDto> getCustomers() {
        return userService.getCustomers();
    }

    @PostMapping("/verifyEmail")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseMessage verifyEmail(@RequestParam String email) {
        return emailVerificationService.verifyEmail(email);
    }

    @PostMapping("/register")
    public UserDto createNormalUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        return userService.createUser(userCreateDto , Role.USER);
    }

    @PostMapping("/register/moderator")
    public UserDto createAmin(@RequestBody @Valid UserCreateDto userCreateDto) {
        return userService.createUser(userCreateDto , Role.MODERATOR);
    }

    @PostMapping("/resetPassword/getToken")
    public ResponseMessage getResetPasswordToken(@RequestBody UserEmailDto reqDto) {
        return userService.getResetPasswordToken(reqDto);
    }

    @PostMapping("/resetPassword")
    public ResponseMessage resetPassoword(@RequestBody ResetPassReqDto resetPassReqDto){
        return userService.resetPassword(resetPassReqDto);
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
