package com.example.restaurantmanagement.dao.repository;

import com.example.restaurantmanagement.dao.entity.MealEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<MealEntity, String> {

}
