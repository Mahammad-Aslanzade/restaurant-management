package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.enums.Role;
import com.example.restaurantmanagement.model.auth.ResponseMessage;
import com.example.restaurantmanagement.model.user.UserCreateDto;
import com.example.restaurantmanagement.model.user.UserDto;
import com.example.restaurantmanagement.model.user.UserUpdateDto;
import com.example.restaurantmanagement.model.user.VerifyEmailDto;
import com.example.restaurantmanagement.service.EmailVerificationService;
import com.example.restaurantmanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final EmailVerificationService emailVerificationService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{user-id}")
    public UserDto getUserById(@PathVariable("user-id") String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/customer")
    public List<UserDto> getCustomers() {
        return userService.getCustomers();
    }

    @PostMapping("/verify-email")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseMessage verifyEmail(@RequestBody @Valid VerifyEmailDto email) {
        return emailVerificationService.verifyEmail(email);
    }

    @PostMapping("/register")
    public UserDto createNormalUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        return userService.createUser(userCreateDto, Role.USER);
    }

    @PostMapping("/register/moderator")
    public UserDto createAmin(@RequestBody @Valid UserCreateDto userCreateDto) {
        return userService.createUser(userCreateDto, Role.MODERATOR);
    }


    @PutMapping("/{user-id}")
    public void updateUser(@PathVariable("user-id") String userId, @RequestBody @Valid UserUpdateDto userUpdateDto) {
        userService.updateUser(userId, userUpdateDto);
    }

    @DeleteMapping("/{user-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("user-id") String userId) {
        userService.deleteUser(userId);
    }


}
