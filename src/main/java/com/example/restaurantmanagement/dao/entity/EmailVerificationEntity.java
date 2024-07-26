package com.example.restaurantmanagement.dao.entity;

import com.example.restaurantmanagement.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "email_verifications")
public class EmailVerificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String email;
    private String verificationCode;
    private LocalDateTime issueDate;
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;
}
