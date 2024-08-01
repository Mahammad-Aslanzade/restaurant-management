package com.example.restaurantmanagement.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reservations")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
}
