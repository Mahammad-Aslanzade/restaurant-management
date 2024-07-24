package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.MealCategoryEntity;
import com.example.restaurantmanagement.dao.entity.MealEntity;
import com.example.restaurantmanagement.dao.repository.MealCategoryRepository;
import com.example.restaurantmanagement.dao.repository.MealRepository;
import com.example.restaurantmanagement.mapper.MealMapper;
import com.example.restaurantmanagement.model.meal.MealDto;
import com.example.restaurantmanagement.model.meal.MealReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class MealService {

    private final MealMapper mealMapper;
    private final MealRepository mealRepository;
    private final ImageService imageService;
    private final MealCategoryService mealCategoryService;

    public List<MealDto> getAllMeals(){
        List<MealEntity> mealEntities = mealRepository.findAll();
        List<MealDto> mealDtos = mealMapper.listToDto(mealEntities);
        return mealDtos;
    }

    public void createMeal(MealReqDto mealReqDto){
        mealReqDto.getIngredientsList().forEach(System.out::println);
        MealEntity mealEntity = mealMapper.mapToEntity(mealReqDto);

        String imageUrl = imageService.upLoadImageAndGetPath(mealReqDto.getImage());
        mealEntity.setImage(imageUrl);

        MealCategoryEntity category = mealCategoryService.getCategoryEntity(mealReqDto.getCategoryId());
        mealEntity.setCategory(category);

        mealRepository.save(mealEntity);
    }



}
