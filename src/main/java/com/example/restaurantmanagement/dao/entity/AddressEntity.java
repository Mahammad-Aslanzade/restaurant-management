package com.example.restaurantmanagement.dao.entity;

import com.example.restaurantmanagement.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "addresses")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String city;
    private String street;
    private String apartment;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
