package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.meal.MealDto;
import com.example.restaurantmanagement.model.meal.MealReqDto;
import com.example.restaurantmanagement.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/meal")
public class MealController {

    private final MealService mealService;

    @GetMapping
    public List<MealDto> getAllMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping("/{mealId}")
    public MealDto getMealById(@PathVariable String mealId) {
        return mealService.getMealById(mealId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMeal(@ModelAttribute @Valid MealReqDto mealReqDto) {
        mealService.createMeal(mealReqDto);
    }

    @PutMapping("/{mealId}")
    public void updateMeal(@PathVariable String mealId, @Valid @ModelAttribute MealReqDto mealReqDto , BindingResult bindingResult) {
        mealService.updateMeal(mealId, mealReqDto);
    }

    @DeleteMapping("/{mealId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeal(@PathVariable String mealId) {
        mealService.deleteMeal(mealId);
    }

}
