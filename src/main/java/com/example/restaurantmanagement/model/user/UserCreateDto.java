package com.example.restaurantmanagement.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    @Pattern(regexp = "^\\d{4}-\\d{4}$", message = "The field must be in the format XXXX-XXXX where X is a digit")
    private String verificationCode;
    @Size(min = 3)
    private String name;
    @Size(min = 3)
    private String surname;
    @Email(message = "email cann't be null")
    private String email;
    @Pattern(regexp = "\\+994\\d{9}", message = "Phone number must be in the format +994xxxxxxxxx")
    private String phoneNumber;
    private String password;
    @Past
    private LocalDate birthDate;
}
