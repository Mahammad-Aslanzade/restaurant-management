package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.meal.MealDto;
import com.example.restaurantmanagement.model.meal.MealReqDto;
import com.example.restaurantmanagement.service.ImageService;
import com.example.restaurantmanagement.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/meal")
public class MealController {

    private final MealService mealService;
    private final ImageService imageService;

    @GetMapping
    public List<MealDto> getAllMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping("/{mealId}")
    public MealDto getMealById(@PathVariable String mealId) {
        return mealService.getMealById(mealId);
    }

    @GetMapping("/search/{title}")
    public List<MealDto> searchByTitle(@PathVariable String title) {
        return mealService.elasticSearchMeal(title);
    }

    @PostMapping("/elastic")
    public MealDto elasticAddMeal(@RequestPart("image") MultipartFile image, @RequestBody @Valid MealReqDto mealReqDto) {
        return mealService.elasticAddMeal(image, mealReqDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMeal(@RequestPart("image") MultipartFile image, @RequestPart("mealDetails") @Valid MealReqDto mealReqDto) {
        imageService.imageValidator(image);
        mealService.createMeal(image, mealReqDto);
    }

    @PutMapping("/{mealId}")
    public void updateMeal(@PathVariable String mealId, @RequestPart(value = "image", required = false) MultipartFile image, @RequestPart("mealDetails") @Valid MealReqDto mealReqDto) {
        mealService.updateMeal(mealId, image, mealReqDto);
    }

    @DeleteMapping("/{mealId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeal(@PathVariable String mealId) {
        mealService.deleteMeal(mealId);
    }

}
