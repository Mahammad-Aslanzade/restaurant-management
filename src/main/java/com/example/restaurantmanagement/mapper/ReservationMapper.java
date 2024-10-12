package com.example.restaurantmanagement.mapper;

import com.example.restaurantmanagement.dao.entity.ReservationEntity;
import com.example.restaurantmanagement.model.reservation.ReservationCUDto;
import com.example.restaurantmanagement.model.reservation.ReservationDto;
import com.example.restaurantmanagement.model.reservation.ReservationWithoutUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    List<ReservationDto> listToDto(List<ReservationEntity> reservationEntityList);
    List<ReservationWithoutUser> listToDtoForUser(List<ReservationEntity> reservationEntityList);

    ReservationDto mapToDto(ReservationEntity reservationEntity);

    ReservationEntity mapToEntity(ReservationCUDto reservationCUDto);

}
