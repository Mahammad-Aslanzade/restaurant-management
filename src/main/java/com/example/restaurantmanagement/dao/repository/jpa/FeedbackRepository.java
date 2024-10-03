package com.example.restaurantmanagement.dao.repository.jpa;

import com.example.restaurantmanagement.dao.entity.FeedbackEntity;
import com.example.restaurantmanagement.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, String> {

    List<FeedbackEntity> findAllByUser(UserEntity user);
}
