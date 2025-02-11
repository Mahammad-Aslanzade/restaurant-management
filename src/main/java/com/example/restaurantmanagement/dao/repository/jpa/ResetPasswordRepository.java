package com.example.restaurantmanagement.dao.repository.jpa;

import com.example.restaurantmanagement.dao.entity.ResetPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordRepository extends JpaRepository<ResetPasswordEntity, String> {

    Optional<ResetPasswordEntity> findByValidationCode(String validationCode);
}
