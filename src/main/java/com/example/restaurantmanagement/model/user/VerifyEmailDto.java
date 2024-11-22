package com.example.restaurantmanagement.model.user;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyEmailDto {
    @Email(message = "email field must be valid")
    private String email;
}
