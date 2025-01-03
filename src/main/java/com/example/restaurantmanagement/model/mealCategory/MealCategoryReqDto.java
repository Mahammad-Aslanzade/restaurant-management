package com.example.restaurantmanagement.model.mealCategory;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealCategoryReqDto {
    @Positive(message = "position must be positive")
    private Integer position;
    private String title;
    private MultipartFile image;
}
