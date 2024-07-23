package com.example.restaurantmanagement.model.mealCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealCategoryReqDto {
    private String id;
    private String title;
    private MultipartFile image;
}
