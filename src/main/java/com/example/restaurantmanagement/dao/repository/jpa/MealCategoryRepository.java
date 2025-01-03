package com.example.restaurantmanagement.dao.repository.jpa;

import com.example.restaurantmanagement.dao.entity.MealCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MealCategoryRepository extends JpaRepository<MealCategoryEntity, String> {

    boolean existsByPosition(Integer position);
}
