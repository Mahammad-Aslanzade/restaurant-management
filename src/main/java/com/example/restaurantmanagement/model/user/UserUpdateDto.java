package com.example.restaurantmanagement.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    @Size(min = 3)
    private String name;
    @Size(min = 3)
    private String surname;
    @Pattern(regexp = "\\+994\\d{9}", message = "Phone number must be in the format +994xxxxxxxxx")
    private String phoneNumber;
    private LocalDate birthDate;
}
