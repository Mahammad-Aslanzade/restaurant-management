package com.example.restaurantmanagement.model.reservation;

import com.example.restaurantmanagement.model.table.TableDto;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCUDto {
    @NotNull
    private String tableId;
    @NotNull
    private String userId;
    private String note;
    @Min(0)
    private Integer peopleCount;
    @Future
    private LocalDateTime time;
}