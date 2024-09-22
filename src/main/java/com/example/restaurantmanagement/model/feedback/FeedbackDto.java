package com.example.restaurantmanagement.model.feedback;

import com.example.restaurantmanagement.model.meal.MealDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {
    private String id;
    private String text;
    private Float rate;
    private MealDto meal;
    private UserInfoDto user;
    private Instant createdAt;
    private Instant updatedAt;

    @Data
    public static class UserInfoDto{
        private String id;
        private String name;
        private String surname;
        private String email;
    }
}
