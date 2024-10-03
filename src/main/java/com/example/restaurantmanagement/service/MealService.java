package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.MealCategoryEntity;
import com.example.restaurantmanagement.dao.entity.MealEntity;
import com.example.restaurantmanagement.dao.repository.elastic.MealElasticSearchRepository;
import com.example.restaurantmanagement.dao.repository.jpa.MealRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.mapper.MealMapper;
import com.example.restaurantmanagement.model.meal.MealDto;
import com.example.restaurantmanagement.model.meal.MealReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class MealService {

    private final MealMapper mealMapper;
    private final MealRepository mealRepository;
    private final ImageService imageService;
    private final MealCategoryService mealCategoryService;
    private final MealElasticSearchRepository mealElasticSearchRepository;

    public List<MealDto> getAllMeals() {
        log.info("ACTION.getAllMeals.start");
        List<MealEntity> mealEntities = mealRepository.findAll();
        List<MealDto> mealDtos = mealMapper.listToDto(mealEntities);
        log.info("ACTION.getAllMeals.end");
        return mealDtos;
    }

    public MealEntity getMealEntity(String mealId) {
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

    public List<MealDto> elasticSearchMeal(String title){
        log.info("ACTION.elasticSearchMeal.start title : {}", title);
        List<MealEntity> allMealEntites = mealElasticSearchRepository.searchAllByTitle(title);
        List<MealDto> allMealDtos = mealMapper.listToDto(allMealEntites);
        log.info("ACTION.elasticSearchMeal.start end : {}", title);
        return allMealDtos;
    }


    public void createMeal(MultipartFile image, MealReqDto mealReqDto) {
        log.info("ACTION.createMeal.start requestBody : {}", mealReqDto);
        mealReqDto.getIngredientsList().forEach(System.out::println);
        MealEntity mealEntity = mealMapper.mapToEntity(mealReqDto);

        String imageUrl = imageService.upLoadImageAndGetUrl(image);
        mealEntity.setImage(imageUrl);

        MealCategoryEntity category = mealCategoryService.getCategoryEntity(mealReqDto.getCategoryId());
        mealEntity.setCategory(category);

        mealRepository.save(mealEntity);
        log.info("ACTION.createMeal.end requestBody : {}", mealReqDto);
    }

    public void updateMeal(String mealId, MultipartFile image, MealReqDto mealReqDto) {
        log.info("ACTION.updateMeal.start requestBody : {}", mealReqDto);
        MealEntity oldMeal = getMealEntity(mealId);
        MealEntity updatedMeal = mealMapper.mapToEntity(mealReqDto);
        updatedMeal.setId(oldMeal.getId());

        MealCategoryEntity mealCategory = mealCategoryService.getCategoryEntity(mealReqDto.getCategoryId());
        updatedMeal.setCategory(mealCategory);

        if (image == null) {
            updatedMeal.setImage(oldMeal.getImage());
        } else {
            String imageUrl = imageService.upLoadImageAndGetUrl(image);
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

    public Double calculateTotalPrice(List<String> meals) {
        Double totalAmount = 0.0;
        for (String m : meals) {
            MealEntity thisMeal = getMealEntity(m);
            if (thisMeal.getPrice() != null)
                totalAmount += thisMeal.getPrice();
        }
        return totalAmount;
    }


    public MealDto elasticAddMeal(MultipartFile image, MealReqDto mealReqDto) {
        log.info("ACTION.elasticAddMeal.start requestBody : {}", mealReqDto);
        MealEntity mealEntity = mealMapper.mapToEntity(mealReqDto);

        String imageUrl = imageService.upLoadImageAndGetUrl(image);
        mealEntity.setImage(imageUrl);

        MealCategoryEntity category = mealCategoryService.getCategoryEntity(mealReqDto.getCategoryId());
        mealEntity.setCategory(category);
        mealElasticSearchRepository.save(mealEntity);
        mealRepository.save(mealEntity);
        log.info("ACTION.elasticAddMeal.end requestBody : {}", mealReqDto);
        return mealMapper.mapToDto(mealEntity);
    }
}
