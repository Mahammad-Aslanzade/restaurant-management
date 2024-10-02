package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.MealCategoryEntity;
import com.example.restaurantmanagement.dao.repository.MealCategoryRepository;
import com.example.restaurantmanagement.dao.repository.MealRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.exceptions.RelationExistException;
import com.example.restaurantmanagement.mapper.MealCategoryMapper;
import com.example.restaurantmanagement.model.mealCategory.MealCategoryDto;
import com.example.restaurantmanagement.model.mealCategory.MealCategoryReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j

@Service
public class MealCategoryService {

    private final MealCategoryMapper mealCategoryMapper;
    private final MealCategoryRepository mealCategoryRepository;
    private final ImageService imageService;
    private final MealRepository mealRepository;

    public List<MealCategoryDto> getAllCategories() {
        log.info("ACTION.getAllCategories.start");
        List<MealCategoryEntity> mealCategoryEntities = mealCategoryRepository.findAll();
        List<MealCategoryDto> mealCategoryDtos = mealCategoryMapper.listToDto(mealCategoryEntities);
        log.info("ACTION.getAllCategories.end");
        return mealCategoryDtos;
    }

    public MealCategoryEntity getCategoryEntity(String categoryId) {
        return mealCategoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new NotFoundException(
                                ExceptionDetails.CATEGORY_NOT_FOUND.message(),
                                String.format("ACTION.ERROR.getCategoryById categoryID : %s", categoryId)
                        )
                );
    }

    public MealCategoryDto getCategoryById(String categoryId) {
        log.info("ACTION.getCategoryById.start categoryId : {}", categoryId);
        MealCategoryEntity mealCategoryEntity = getCategoryEntity(categoryId);
        MealCategoryDto mealCategoryDto = mealCategoryMapper.mapToDto(mealCategoryEntity);
        log.info("ACTION.getCategoryById.end categoryId : {}", categoryId);
        return mealCategoryDto;
    }

    public void createMealCategory(MealCategoryReqDto mealCategoryReqDto) {
        log.info("ACTION.createMealCategory.start requestBody : {}", mealCategoryReqDto);
        MealCategoryEntity mealCategoryEntity = mealCategoryMapper.mapToEntity(mealCategoryReqDto);
        String imageUrl = imageService.upLoadImageAndGetUrl(mealCategoryReqDto.getImage());
        mealCategoryEntity.setImage(imageUrl);
        mealCategoryRepository.save(mealCategoryEntity);
        log.info("ACTION.createMealCategory.end requestBody : {}", mealCategoryReqDto);
    }

    public void updateMealCategory(String categoryId, MealCategoryReqDto mealCategoryReqDto) {
        log.info("ACTION.updateMealCategory.start id : {} | requestBody : {}", categoryId, mealCategoryReqDto);
        MealCategoryEntity oldMealCategory = getCategoryEntity(categoryId);
        MealCategoryEntity updatedMealCategory = mealCategoryMapper.mapToEntity(mealCategoryReqDto);
        updatedMealCategory.setId(oldMealCategory.getId());

        if (mealCategoryReqDto.getImage() == null) {
            updatedMealCategory.setImage(oldMealCategory.getImage());
        } else {
            String imageUrl = imageService.upLoadImageAndGetUrl(mealCategoryReqDto.getImage());
            updatedMealCategory.setImage(imageUrl);
            imageService.deleteImage(oldMealCategory.getImage());
        }
        mealCategoryRepository.save(updatedMealCategory);
        log.info("ACTION.updateMealCategory.end id : {} | requestBody : {}", categoryId, mealCategoryReqDto);
    }

    public void deleteMealCategory(String categoryId) {
        log.info("ACTION.deleteMealCategory.start categoryId : {}", categoryId);
        MealCategoryEntity mealCategoryEntity = getCategoryEntity(categoryId);
        if (!mealRepository.findAllByCategory(mealCategoryEntity).isEmpty()) {
            throw new RelationExistException(
                    String.format("%s contains some meals !", mealCategoryEntity.getTitle()),
                    String.format("ACTION.ERROR.deleteMealCategory id : %s | has relation", mealCategoryEntity.getId())
            );
        }
        mealCategoryRepository.delete(mealCategoryEntity);
        imageService.deleteImage(mealCategoryEntity.getImage());

        log.info("ACTION.deleteMealCategory.end categoryId : {}", categoryId);
    }

}
