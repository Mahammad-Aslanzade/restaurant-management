package com.example.restaurantmanagement.model.meal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealWithoutCategoryDto {
    private String id;
    private String title;
    private String image;
    private List<String> ingredientsList;
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
