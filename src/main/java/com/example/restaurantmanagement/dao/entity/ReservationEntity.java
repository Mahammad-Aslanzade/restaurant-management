package com.example.restaurantmanagement.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "reservations")
public class ReservationEntity {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "table_id")
    private TableEntity table;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private String note;
    private Integer peopleCount;
    private LocalDateTime arrivalTime;
    private LocalDateTime leavingTime;
    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;


}
