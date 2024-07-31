package com.example.restaurantmanagement.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tables")
public class TableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Integer no;
    private Integer floor;
    private Integer capacity;
    private Boolean cigarette;
    private Boolean windowView;
}
