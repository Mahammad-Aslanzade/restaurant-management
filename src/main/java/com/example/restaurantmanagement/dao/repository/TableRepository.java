package com.example.restaurantmanagement.dao.repository;

import com.example.restaurantmanagement.dao.entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<TableEntity, String> {
}
