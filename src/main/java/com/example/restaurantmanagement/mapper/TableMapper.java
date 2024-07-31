package com.example.restaurantmanagement.mapper;

import com.example.restaurantmanagement.dao.entity.TableEntity;
import com.example.restaurantmanagement.model.table.TableDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TableMapper {

    List<TableDto> listToDto(List<TableEntity> tableEntityList);

    TableDto mapToDto(TableEntity tableEntity);

    TableEntity mapToEntity(TableDto tableDto);

}
