package com.example.restaurantmanagement.dao.repository;

import com.example.restaurantmanagement.dao.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, String> {
}
