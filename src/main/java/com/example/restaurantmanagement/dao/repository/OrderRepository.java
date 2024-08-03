package com.example.restaurantmanagement.dao.repository;

import com.example.restaurantmanagement.dao.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {

}
