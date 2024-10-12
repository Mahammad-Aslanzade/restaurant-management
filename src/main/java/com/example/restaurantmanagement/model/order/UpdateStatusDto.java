package com.example.restaurantmanagement.model.order;

import com.example.restaurantmanagement.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusDto {
    @NotNull(message = "Status cann't be null")
    private OrderStatus status;
}
