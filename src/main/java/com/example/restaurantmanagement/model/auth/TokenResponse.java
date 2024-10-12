package com.example.restaurantmanagement.model.auth;

import com.example.restaurantmanagement.enums.Role;
import com.example.restaurantmanagement.model.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private Role role;
}
