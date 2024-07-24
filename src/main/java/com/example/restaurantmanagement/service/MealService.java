package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.MealCategoryEntity;
import com.example.restaurantmanagement.dao.entity.MealEntity;
import com.example.restaurantmanagement.dao.repository.MealRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.exceptions.NotFoundException;
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

    public List<MealDto> getAllMeals() {
        log.info("ACTION.getAllMeals.start");
        List<MealEntity> mealEntities = mealRepository.findAll();
        List<MealDto> mealDtos = mealMapper.listToDto(mealEntities);
        log.info("ACTION.getAllMeals.end");
        return mealDtos;
    }

    private MealEntity getMealEntity(String mealId) {
        return mealRepository.findById(mealId).orElseThrow(() ->
                new NotFoundException(
                        ExceptionDetails.MEAL_NOT_FOUND.message(),
                        ExceptionDetails.MEAL_NOT_FOUND.createLogMessage("getMealById", mealId)
                )
        );
    }

    public MealDto getMealById(String mealId) {
        log.info("ACTION.getMealById.start mealId : {}", mealId);
        MealEntity mealEntity = getMealEntity(mealId);
        MealDto mealDto = mealMapper.mapToDto(mealEntity);
        log.info("ACTION.getMealById.end mealId : {}", mealId);
        return mealDto;
    }

    public void createMeal(MealReqDto mealReqDto) {
        log.info("ACTION.createMeal.start requestBody : {}", mealReqDto);
        mealReqDto.getIngredientsList().forEach(System.out::println);
        MealEntity mealEntity = mealMapper.mapToEntity(mealReqDto);

        String imageUrl = imageService.upLoadImageAndGetPath(mealReqDto.getImage());
        mealEntity.setImage(imageUrl);

        MealCategoryEntity category = mealCategoryService.getCategoryEntity(mealReqDto.getCategoryId());
        mealEntity.setCategory(category);

        mealRepository.save(mealEntity);
        log.info("ACTION.createMeal.end requestBody : {}", mealReqDto);
    }

    public void updateMeal(String mealId, MealReqDto mealReqDto) {
        log.info("ACTION.updateMeal.start requestBody : {}", mealReqDto);
        MealEntity oldMeal = getMealEntity(mealId);
        MealEntity updatedMeal = mealMapper.mapToEntity(mealReqDto);
        updatedMeal.setId(oldMeal.getId());

        MealCategoryEntity mealCategory = mealCategoryService.getCategoryEntity(mealReqDto.getCategoryId());
        updatedMeal.setCategory(mealCategory);

        if (mealReqDto.getImage() == null) {
            updatedMeal.setImage(oldMeal.getImage());
        } else {
            String imageUrl = imageService.upLoadImageAndGetPath(mealReqDto.getImage());
            updatedMeal.setImage(imageUrl);
            imageService.deleteImage(oldMeal.getImage());
        }

        mealRepository.save(updatedMeal);
        log.info("ACTION.updateMeal.end requestBody : {}", mealReqDto);
    }

    public void deleteMeal(String mealId) {
        log.info("ACTION.deleteMeal.start mealId : {}", mealId);
        MealEntity meal = getMealEntity(mealId);
        imageService.deleteImage(meal.getImage());
        mealRepository.delete(meal);
        log.info("ACTION.deleteMeal.end mealId : {}", mealId);
    }


}