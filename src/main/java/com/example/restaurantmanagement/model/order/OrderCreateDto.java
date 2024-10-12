package com.example.restaurantmanagement.model.order;

import com.example.restaurantmanagement.enums.OrderType;
import com.example.restaurantmanagement.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDto {
    @NotNull(message = "Address cann't be null")
    private String addressId;
    @NotNull(message = "Order Type cann't be null")
    private OrderType type;
    @NotNull(message = "Payment cann't be null")
    private PaymentType paymentType;
    @NotNull(message = "Meal list is empty")
    private List<String> mealList;

}
