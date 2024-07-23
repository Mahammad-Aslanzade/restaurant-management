package com.example.restaurantmanagement.mapper;

import com.example.restaurantmanagement.dao.entity.MealCategoryEntity;
import com.example.restaurantmanagement.model.mealCategory.MealCategoryDto;
import com.example.restaurantmanagement.model.mealCategory.MealCategoryReqDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MealCategoryMapper {

    List<MealCategoryDto> listToDto(List<MealCategoryEntity> mealCategoryEntities);

    MealCategoryDto mapToDto(MealCategoryEntity mealCategoryEntity);

    @Mapping(target = "image", ignore = true)
    MealCategoryEntity mapToEntity(MealCategoryReqDto mealCategoryReqDto);
}
