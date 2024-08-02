package com.example.restaurantmanagement.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "reservations")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "table_id")
    private TableEntity table;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String note;
    private Integer peopleCount;
    private LocalDateTime time;
}
