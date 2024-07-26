package com.example.restaurantmanagement.dao.repository;

import com.example.restaurantmanagement.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

}
