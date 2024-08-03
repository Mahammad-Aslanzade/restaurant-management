package com.example.restaurantmanagement.dao.entity;

import com.example.restaurantmanagement.enums.OrderStatus;
import com.example.restaurantmanagement.enums.OrderType;
import com.example.restaurantmanagement.enums.PaymentType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Double totalPrice;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Enumerated(EnumType.STRING)
    private OrderType type;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Transient
    private List<String> mealList;
    private String meals;

    @Transient
    private static ObjectMapper objectMapper = new ObjectMapper();


    public void setMealList(List<String> mealList) {
        this.mealList = mealList;
        this.meals = serializeMeals(this.mealList);
    }
    @PostConstruct
    public List<String> getMealList(){
        return deserializeMeals(this.meals);
    }

    private String serializeMeals(List<String> meals) {
        try {
            return objectMapper.writeValueAsString(meals);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<String> deserializeMeals(String mealList) {
        try {
            return objectMapper.readValue(mealList, List.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
