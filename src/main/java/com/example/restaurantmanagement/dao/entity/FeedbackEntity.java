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
@Table(name = "feedbacks")
public class FeedbackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String text;
    private Float rate;
    @ManyToOne
    @JoinColumn(name = "meal_id")
    private MealEntity meal;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
