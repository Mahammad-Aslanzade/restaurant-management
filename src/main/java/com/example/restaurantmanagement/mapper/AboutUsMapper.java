package com.example.restaurantmanagement.mapper;

import com.example.restaurantmanagement.dao.entity.AboutUsEntity;
import com.example.restaurantmanagement.model.aboutUs.AboutUsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AboutUsMapper {

    AboutUsDto mapToDto(AboutUsEntity aboutUsEntity);

    AboutUsEntity mapToEntity(AboutUsDto aboutUsDto);
}
