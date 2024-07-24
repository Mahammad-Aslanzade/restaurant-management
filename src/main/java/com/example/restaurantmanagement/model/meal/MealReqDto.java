package com.example.restaurantmanagement.model.meal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealReqDto {
    @NotNull(message = "title cann't be null")
    private String title;
    private MultipartFile image;
    @NotNull(message = "ingredientsList cann't be null , you can set empty [] if nothing exist")
    private List<String> ingredientsList;
    private String categoryId;
    private String description;
    private double carbohydrates;
    private double calories;
    private double fat;
    private double gram;
    private double protein;
    private double price;
    private double salePrice;
    private double rate;

}
