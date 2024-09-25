package com.example.restaurantmanagement.model.reservation;


import com.example.restaurantmanagement.model.table.TableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private String id;
    private TableDto table;
    private UserInfoDto user;
    private String note;
    private Integer peopleCount;
    private LocalDateTime time;
    private LocalDateTime arrivalTime;
    private LocalDateTime leavingTime;

    @Data
    public static class UserInfoDto{
        private String id;
        private String name;
        private String surname;
        private String email;
        private String phoneNumber;
    }
}
