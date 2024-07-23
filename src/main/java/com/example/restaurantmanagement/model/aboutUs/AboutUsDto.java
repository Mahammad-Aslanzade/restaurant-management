package com.example.restaurantmanagement.model.aboutUs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AboutUsDto {
    private Integer id;
    @Size(min = 5 , message = "Restaurant Name must be greater than 5 letter")
    private String restaurantName;
    private String address;
    @Pattern(regexp = "\\+994\\d{9}" , message = "Phone number must be in the format +994xxxxxxxxx")
    private String number;
    @Email
    private String email;
}
