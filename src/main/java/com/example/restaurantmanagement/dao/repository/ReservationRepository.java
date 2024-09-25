package com.example.restaurantmanagement.dao.repository;

import com.example.restaurantmanagement.dao.entity.ReservationEntity;
import com.example.restaurantmanagement.dao.entity.TableEntity;
import com.example.restaurantmanagement.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, String> {

    List<ReservationEntity> findByUser(UserEntity user);
    List<ReservationEntity> findByTable(TableEntity table);

    @Query("SELECT r FROM ReservationEntity r WHERE r.table = :table AND DATE(r.arrivalTime) = :date")
    List<ReservationEntity> findAllByTableAndArrivalDate(
            @Param("table") TableEntity table,
            @Param("date") LocalDate date
    );

}
