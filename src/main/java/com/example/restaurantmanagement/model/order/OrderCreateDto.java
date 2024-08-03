package com.example.restaurantmanagement.model.order;

import com.example.restaurantmanagement.enums.OrderStatus;
import com.example.restaurantmanagement.enums.OrderType;
import com.example.restaurantmanagement.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDto {
    private String userId;
    private String addressId;
    private OrderType type;
    private PaymentType paymentType;
    private List<String> mealList;

}
