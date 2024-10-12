package com.example.restaurantmanagement.model.reservation;

import com.example.restaurantmanagement.model.table.TableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationWithoutUser {
    private String id;
    private TableDto table;
    private String note;
    private Integer peopleCount;
    private LocalDateTime arrivalTime;
    private LocalDateTime leavingTime;
}
