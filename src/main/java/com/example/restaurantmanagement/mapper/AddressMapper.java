package com.example.restaurantmanagement.mapper;

import com.example.restaurantmanagement.dao.entity.AddressEntity;
import com.example.restaurantmanagement.model.address.AddressReqDto;
import com.example.restaurantmanagement.model.address.AddressDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    List<AddressDto> listToDto(List<AddressEntity> addressEntityList);

    AddressEntity mapToEntity(AddressReqDto addressReqDto);

    AddressDto mapToDto(AddressEntity addressEntity);
}
