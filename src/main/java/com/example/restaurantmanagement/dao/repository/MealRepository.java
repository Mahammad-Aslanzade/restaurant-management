package com.example.restaurantmanagement.dao.repository;

import com.example.restaurantmanagement.dao.entity.MealCategoryEntity;
import com.example.restaurantmanagement.dao.entity.MealEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<MealEntity, String> {

    List<MealEntity> findAllByCategory(MealCategoryEntity mealCategoryEntity);

}
