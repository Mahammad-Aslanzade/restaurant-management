package com.example.restaurantmanagement.model.feedback;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// This dto will be used for C-> CREATE
public class FeedbackCDto {
    @Size(min = 3)
    private String text;
    @Min(value = 0, message = "must be >= 0 and <= 5")
    @Max(value = 5, message = "must be >= 0 and <= 5")
    private Float rate;
    @NotNull
    private String mealId;
}


