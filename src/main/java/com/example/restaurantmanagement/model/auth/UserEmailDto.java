package com.example.restaurantmanagement.model.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEmailDto {
    @NotNull(message = "email can't be null")
    private String email;
}
