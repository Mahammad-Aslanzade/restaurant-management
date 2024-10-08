package com.example.restaurantmanagement.dao.repository.jpa;

import com.example.restaurantmanagement.dao.entity.UserEntity;
import com.example.restaurantmanagement.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllByRole(Role role);
//    Optional<UserEntity> findByUser(String username);

}
