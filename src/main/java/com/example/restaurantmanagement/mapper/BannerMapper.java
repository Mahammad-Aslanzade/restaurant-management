package com.example.restaurantmanagement.mapper;

import com.example.restaurantmanagement.dao.entity.BannerEntity;
import com.example.restaurantmanagement.model.banner.BannerDto;
import com.example.restaurantmanagement.model.banner.BannerReqDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BannerMapper {

    BannerDto mapToDto(BannerEntity bannerEntity);
    @Mapping(target = "image" , ignore = true)
    BannerEntity mapToEntity(BannerReqDto bannerReqDto);
    List<BannerDto> listToDto(List<BannerEntity> bannerEntityList);

}
