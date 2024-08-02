package com.example.restaurantmanagement.dao.repository;

import com.example.restaurantmanagement.dao.entity.ReservationEntity;
import com.example.restaurantmanagement.dao.entity.TableEntity;
import com.example.restaurantmanagement.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, String> {

    List<ReservationEntity> findByUser(UserEntity user);
    List<ReservationEntity> findByTable(TableEntity table);

}
