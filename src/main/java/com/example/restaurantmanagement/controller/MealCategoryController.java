package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.mealCategory.MealCategoryDto;
import com.example.restaurantmanagement.model.mealCategory.MealCategoryReqDto;
import com.example.restaurantmanagement.model.mealCategory.MealCategoryWithMealsDto;
import com.example.restaurantmanagement.service.MealCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/meal-category")
public class MealCategoryController {

    private final MealCategoryService mealCategoryService;

    @GetMapping
    public List<MealCategoryDto> getAllCategories() {
        return mealCategoryService.getAllCategories();
    }

    @GetMapping("/{category-id}")
    public MealCategoryDto getCategoryById(@PathVariable("category-id") String categoryId) {
        return mealCategoryService.getCategoryById(categoryId);
    }

    @GetMapping("/meals-included")
    public List<MealCategoryWithMealsDto> getAllCategoriesWithMeals() {
        return mealCategoryService.getAllCategoriesWithMeals();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@ModelAttribute @Valid MealCategoryReqDto mealCategoryReqDto) {
        mealCategoryService.createMealCategory(mealCategoryReqDto);
    }

    @PutMapping("/{category-id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCategory(@PathVariable("category-id") String mealCategoryId, @ModelAttribute @Valid MealCategoryReqDto mealCategoryReqDto) {
        mealCategoryService.updateMealCategory(mealCategoryId, mealCategoryReqDto);
    }


    @DeleteMapping("/{category-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("category-id") String categoryId) {
        mealCategoryService.deleteMealCategory(categoryId);
    }

}
