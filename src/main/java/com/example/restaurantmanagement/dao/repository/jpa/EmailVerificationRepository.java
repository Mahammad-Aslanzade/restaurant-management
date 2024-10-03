package com.example.restaurantmanagement.dao.repository.jpa;

import com.example.restaurantmanagement.dao.entity.EmailVerificationEntity;
import com.example.restaurantmanagement.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerificationEntity, String> {

    @Query(value = "SELECT * FROM email_verifications WHERE email = :email ORDER BY issue_date DESC LIMIT 1", nativeQuery = true)
    Optional<EmailVerificationEntity> findLatestEntity(@Param("email") String email);

    Optional<EmailVerificationEntity> findByEmail(String email);

    List<EmailVerificationEntity> findAllByVerificationStatusIsNot(VerificationStatus verificationStatus);


}
