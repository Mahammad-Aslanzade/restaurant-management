package com.example.restaurantmanagement.model.emailVerification;

import jakarta.validation.constraints.Email;


public class VerificationCreateDto {
    @Email
    private String email;
}
