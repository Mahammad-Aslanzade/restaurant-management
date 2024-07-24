package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.meal.MealDto;
import com.example.restaurantmanagement.model.meal.MealReqDto;
import com.example.restaurantmanagement.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/meal")
public class MealController {

    private final MealService mealService;

    @GetMapping
    public List<MealDto> getAllMeals(){
        return mealService.getAllMeals();
    }

    @PostMapping
    public void createMeal(@ModelAttribute @Valid MealReqDto mealReqDto){
        mealService.createMeal(mealReqDto);
    }
}
