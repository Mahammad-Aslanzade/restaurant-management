package com.example.restaurantmanagement.dao.repository;

import com.example.restaurantmanagement.dao.entity.EmailVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerificationEntity, String> {

    @Query(value = "SELECT * FROM email_verifications e ORDER BY e.issue_date DESC limit 1" , nativeQuery = true)
    EmailVerificationEntity findLatestEntity();

    Optional<EmailVerificationEntity> findByEmail(String email);


}
