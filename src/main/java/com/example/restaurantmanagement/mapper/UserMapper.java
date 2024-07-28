package com.example.restaurantmanagement.mapper;

import com.example.restaurantmanagement.dao.entity.UserEntity;
import com.example.restaurantmanagement.model.user.UserCreateDto;
import com.example.restaurantmanagement.model.user.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    List<UserDto> listToDto(List<UserEntity> userEntityList);

    UserEntity mapToEntity(UserCreateDto userCreateDto);

}
