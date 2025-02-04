package com.example.restaurantmanagement.model.mealCategory;

import com.example.restaurantmanagement.model.meal.MealWithoutCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealCategoryWithMealsDto {
    private String id;
    private Integer position;
    private String title;
    private String image;
    private List<MealWithoutCategoryDto> meal;

}
