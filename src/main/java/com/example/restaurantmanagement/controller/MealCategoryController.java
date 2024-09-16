package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.mealCategory.MealCategoryDto;
import com.example.restaurantmanagement.model.mealCategory.MealCategoryReqDto;
import com.example.restaurantmanagement.service.MealCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mealCategory")
@CrossOrigin("*")
public class MealCategoryController {

    private final MealCategoryService mealCategoryService;

    @GetMapping
    public List<MealCategoryDto> getAllCategories() {
        return mealCategoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public MealCategoryDto getCategoryById(@PathVariable String categoryId){
        return mealCategoryService.getCategoryById(categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@ModelAttribute @Valid MealCategoryReqDto mealCategoryReqDto){
        mealCategoryService.createMealCategory(mealCategoryReqDto);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable String categoryId){
        mealCategoryService.deleteMealCategory(categoryId);
    }

}
