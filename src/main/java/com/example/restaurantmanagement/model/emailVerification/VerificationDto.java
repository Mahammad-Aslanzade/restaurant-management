package com.example.restaurantmanagement.model.emailVerification;

import com.example.restaurantmanagement.enums.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationDto {
    private String id;
    private String email;
    private String verificationCode;
    private LocalDateTime issueDate;
    private VerificationStatus verificationStatus;
}
