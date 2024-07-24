package com.example.restaurantmanagement.mapper;

import com.example.restaurantmanagement.dao.entity.MealEntity;
import com.example.restaurantmanagement.model.meal.MealDto;
import com.example.restaurantmanagement.model.meal.MealReqDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MealMapper {

    List<MealDto> listToDto(List<MealEntity> mealEntities);

    @Mapping(target = "image" , ignore = true)
    MealEntity mapToEntity(MealReqDto mealReqDto);
}
