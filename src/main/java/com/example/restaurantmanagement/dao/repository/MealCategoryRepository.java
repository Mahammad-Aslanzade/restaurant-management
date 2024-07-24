package com.example.restaurantmanagement.dao.repository;

import com.example.restaurantmanagement.dao.entity.MealCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MealCategoryRepository extends JpaRepository<MealCategoryEntity, String> {
}
