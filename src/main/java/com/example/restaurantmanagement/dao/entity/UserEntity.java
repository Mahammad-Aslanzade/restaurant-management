package com.example.restaurantmanagement.dao.entity;

import com.example.restaurantmanagement.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String surname;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String email;
    private String phoneNumber;
    @OneToMany(mappedBy = "user" , orphanRemoval = true)
    private List<AddressEntity> addressList;
    private LocalDate birthDate;
    @CreatedDate
    private Instant createdAt;

}
