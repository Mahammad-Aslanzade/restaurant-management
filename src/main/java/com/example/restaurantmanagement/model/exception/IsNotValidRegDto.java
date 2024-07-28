package com.example.restaurantmanagement.model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IsNotValidRegDto {
    private String verificationCode;
    private String userEmail;
    private String message;
}
