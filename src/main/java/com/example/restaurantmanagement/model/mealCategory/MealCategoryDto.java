package com.example.restaurantmanagement.model.mealCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealCategoryDto {
    private String id;
    private String title;
    private String image;
}
