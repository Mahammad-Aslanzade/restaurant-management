package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.meal.MealDto;
import com.example.restaurantmanagement.model.meal.MealReqDto;
import com.example.restaurantmanagement.service.ImageService;
import com.example.restaurantmanagement.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/meals")
public class MealController {

    private final MealService mealService;
    private final ImageService imageService;

    @GetMapping
    public List<MealDto> getAllMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping("/{meal-id}")
    public MealDto getMealById(@PathVariable("meal-id") String mealId) {
        return mealService.getMealById(mealId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMeal(@RequestPart("image") MultipartFile image, @RequestPart("mealDetails") @Valid MealReqDto mealReqDto) {
        imageService.imageValidator(image);
        mealService.createMeal(image, mealReqDto);
    }

    @PutMapping("/{meal-id}")
    public void updateMeal(@PathVariable("meal-id") String mealId, @RequestPart(value = "image", required = false) MultipartFile image, @RequestPart("mealDetails") @Valid MealReqDto mealReqDto) {
        mealService.updateMeal(mealId, image, mealReqDto);
    }

    @DeleteMapping("/{meal-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeal(@PathVariable("meal-id") String mealId) {
        mealService.deleteMeal(mealId);
    }

}
