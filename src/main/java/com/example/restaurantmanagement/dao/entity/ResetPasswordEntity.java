package com.example.restaurantmanagement.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "reset_password_details")
public class ResetPasswordEntity {
    @Id
    private String validationCode;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime lastAttempt;
    private LocalDateTime confirmedAt;
}
