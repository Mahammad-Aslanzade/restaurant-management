package com.example.restaurantmanagement.model.meal;

import com.example.restaurantmanagement.model.mealCategory.MealCategoryDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealDto {
    private String id;
    @NotNull(message = "title cann't be null")
    private String title;
    private String image;
    @NotNull(message = "ingredientsList cann't be null , you can set empty [] if nothing exist")
    private List<String> ingredientsList;
    private MealCategoryDto category;
    private String description;
    private Double carbohydrates;
    private Double calories;
    private Double fat;
    private Double gram;
    private Double protein;
    private Double price;
    private Double salePrice;
    private Double rate;
}
