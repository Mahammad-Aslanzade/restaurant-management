package com.example.restaurantmanagement.model.order;

import com.example.restaurantmanagement.enums.OrderStatus;
import com.example.restaurantmanagement.enums.OrderType;
import com.example.restaurantmanagement.enums.PaymentType;
import com.example.restaurantmanagement.model.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String id;
    private Double totalPrice;
    private UserDto user;
    private String addressId;
    private OrderStatus status;
    private OrderType type;
    private PaymentType paymentType;
    private List<String> mealList;

}
