package com.example.restaurantmanagement.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassReqDto {
    private String validationCode;
    private String password;
    private String confirmPassword;
}
