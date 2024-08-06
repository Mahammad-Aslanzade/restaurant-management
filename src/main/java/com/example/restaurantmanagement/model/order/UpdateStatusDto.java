package com.example.restaurantmanagement.model.order;

import com.example.restaurantmanagement.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusDto {
    private OrderStatus status;
}
