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
    @NotNull(message = "categoryId cann't be null")
    private String categoryId;
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
