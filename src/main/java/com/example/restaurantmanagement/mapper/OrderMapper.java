package com.example.restaurantmanagement.mapper;

import com.example.restaurantmanagement.dao.entity.OrderEntity;
import com.example.restaurantmanagement.model.order.OrderCreateDto;
import com.example.restaurantmanagement.model.order.OrderDto;
import com.example.restaurantmanagement.model.order.OrderUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    List<OrderDto> listToDto(List<OrderEntity> orderEntityList);
    @Mapping(source = "address.id" , target = "addressId")
    OrderDto mapToDto(OrderEntity orderEntity);
    OrderEntity mapToEntity(OrderCreateDto orderCreateDto);
    OrderEntity mapToEntity(OrderUpdateDto orderUpdateDto);
}
