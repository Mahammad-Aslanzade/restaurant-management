package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.ReservationEntity;
import com.example.restaurantmanagement.dao.entity.TableEntity;
import com.example.restaurantmanagement.dao.entity.UserEntity;
import com.example.restaurantmanagement.dao.repository.ReservationRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.mapper.ReservationMapper;
import com.example.restaurantmanagement.model.reservation.ReservationCUDto;
import com.example.restaurantmanagement.model.reservation.ReservationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReservationService {

    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;

    private final UserService userService;
    private final TableService tableService;

    public List<ReservationDto> getAllReservations() {
        log.info("ACTION.getAllReservations.start");
        List<ReservationEntity> reservationEntityList = reservationRepository.findAll();
        List<ReservationDto> reservationDtoList = reservationMapper.listToDto(reservationEntityList);
        log.info("ACTION.getAllReservations.end");
        return reservationDtoList;
    }

    private ReservationEntity getReservationEntity(String reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(() ->
                new NotFoundException(
                        ExceptionDetails.RESERVATION_NOT_FOUND.message(),
                        ExceptionDetails.RESERVATION_NOT_FOUND.createLogMessage("getReservationById", reservationId)
                )
        );
    }

    public ReservationDto getReservationById(String reservationId) {
        log.info("ACTION.getReservationById.start id : {}", reservationId);
        ReservationEntity reservation = getReservationEntity(reservationId);
        ReservationDto reservationDto = reservationMapper.mapToDto(reservation);
        log.info("ACTION.getReservationById.end id : {}", reservationId);
        return reservationDto;
    }

    public List<ReservationDto> getUserReservations(String userId) {
        log.info("ACTION.getUserReservations.start userId : {}", userId);
        // It will throw exception if don't find user
        UserEntity user = userService.getUserEntity(userId);
        List<ReservationEntity> reservationEntities = reservationRepository.findByUser(user);
        List<ReservationDto> reservationDtos = reservationMapper.listToDto(reservationEntities);
        log.info("ACTION.getUserReservations.end userId : {}", userId);
        return reservationDtos;
    }

    public List<ReservationDto> getTableReservations(String tableId){
        log.info("ACTION.getTableReservations.start tableId : {}", tableId);
        TableEntity table = tableService.getTableEntity(tableId);
        List<ReservationEntity> reservationEntities = reservationRepository.findByTable(table);
        List<ReservationDto> reservationDtos = reservationMapper.listToDto(reservationEntities);
        log.info("ACTION.getTableReservations.end tableId : {}", tableId);
        return reservationDtos;
    }

    public void createReservation(ReservationCUDto reservationCUDto) {
        log.info("ACTION.reserveTable.start requestBody : {}", reservationCUDto);
        ReservationEntity reservation = reservationMapper.mapToEntity(reservationCUDto);
        UserEntity user = userService.getUserEntity(reservationCUDto.getUserId());
        TableEntity table = tableService.getTableEntity(reservationCUDto.getTableId());
        reservation.setUser(user);
        reservation.setTable(table);
        reservationRepository.save(reservation);
        log.info("ACTION.reserveTable.end requestBody : {}", reservationCUDto);
    }

    public void updateReservation(String reservationId, ReservationCUDto reservationCUDto) {
        log.info("ACTION.updateReservation.start requestBody : {}", reservationCUDto);
        ReservationEntity oldReservation = getReservationEntity(reservationId);
        ReservationEntity updatedReservation = reservationMapper.mapToEntity(reservationCUDto);
        UserEntity user = userService.getUserEntity(reservationCUDto.getUserId());
        TableEntity table = tableService.getTableEntity(reservationCUDto.getTableId());
        updatedReservation.setId(oldReservation.getId());
        updatedReservation.setUser(user);
        updatedReservation.setTable(table);
        reservationRepository.save(updatedReservation);
        log.info("ACTION.updateReservation.end requestBody : {}", reservationCUDto);
    }

    public void deleteReservation(String reservationId) {
        log.info("ACTION.deleteReservation.start reservationId : {}", reservationId);
        ReservationEntity reservation = getReservationEntity(reservationId);
        reservationRepository.delete(reservation);
        log.info("ACTION.deleteReservation.end reservationId : {}", reservationId);
    }
}
