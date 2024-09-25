package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.ReservationEntity;
import com.example.restaurantmanagement.dao.entity.TableEntity;
import com.example.restaurantmanagement.dao.entity.UserEntity;
import com.example.restaurantmanagement.dao.repository.ReservationRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.exceptions.InvalidException;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.mapper.ReservationMapper;
import com.example.restaurantmanagement.model.reservation.ReservationCUDto;
import com.example.restaurantmanagement.model.reservation.ReservationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReservationService {

    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;

    private final EmailService emailService;
    private final UserService userService;
    private final MyUserDetailService myUserDetailService;
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

    public List<ReservationDto> getTableReservations(String tableId) {
        log.info("ACTION.getTableReservations.start tableId : {}", tableId);
        TableEntity table = tableService.getTableEntity(tableId);
        List<ReservationEntity> reservationEntities = reservationRepository.findByTable(table);
        List<ReservationDto> reservationDtos = reservationMapper.listToDto(reservationEntities);
        log.info("ACTION.getTableReservations.end tableId : {}", tableId);
        return reservationDtos;
    }

    public boolean intersects(LocalDateTime arrivalOne, LocalDateTime leavingTimeOne, LocalDateTime arrivalTwo, LocalDateTime leavingTimeTwo) {
        // Check if one interval starts before the other ends, and ends after the other starts.
        return !(leavingTimeOne.isBefore(arrivalTwo) || arrivalOne.isAfter(leavingTimeTwo));
    }

    public void createReservation(ReservationCUDto reservationCUDto) {
        log.info("ACTION.reserveTable.start requestBody : {}", reservationCUDto);
        if (reservationCUDto.getLeavingTime().isBefore(reservationCUDto.getArrivalTime())) {
            throw new InvalidException(
                    "LEAVING_TIME",
                    "LEVAING_TIME_IS_BEFORE_THAN_ARRIVAL",
                    String.format("ACTION.ERROR.createReservation arrivalTime : %s | leavingTime : %s", reservationCUDto.getArrivalTime(), reservationCUDto.getLeavingTime())
            );
        }

        UserEntity user = myUserDetailService.getCurrentAuthenticatedUser();
        TableEntity table = tableService.getTableEntity(reservationCUDto.getTableId());
        LocalDateTime arrival = reservationCUDto.getArrivalTime();
        LocalDateTime leaving = reservationCUDto.getLeavingTime();
        LocalDate date = LocalDate.of(arrival.getYear(), arrival.getMonthValue(), arrival.getDayOfMonth());

        List<ReservationEntity> tablesReserveInThisDay = reservationRepository.findAllByTableAndArrivalDate(table, date);
        for (ReservationEntity reserveItem : tablesReserveInThisDay) {
            boolean available = !intersects(
                    reserveItem.getArrivalTime(),
                    reserveItem.getLeavingTime(),
                    arrival,
                    leaving
            );
            if (!available) {
                throw new InvalidException(
                        "RESERVATION_TIME",
                        "TABLE_IS_NOT_AVAILABLE_IN_THIS_TIME",
                        String.format("ACTION.ERROR.createReservation reservations_intersect id: %s", reserveItem.getId())
                );
            }

        }

        ReservationEntity reservation = reservationMapper.mapToEntity(reservationCUDto);
        reservation.setId(UUID.randomUUID().toString());
        reservation.setUser(user);
        reservation.setTable(table);
        reservationRepository.save(reservation);

        //Settings of html template and pass it to emailService
        String templateName = "reservation-details";
        Context context = new Context();
        context.setVariable("reservationId", reservation.getId());
        context.setVariable("reserverName", reservation.getUser().getName());
        context.setVariable("arrivalTime", reservation.getArrivalTime());
        context.setVariable("leavingTime", reservation.getLeavingTime());
        context.setVariable("tableNo", reservation.getTable().getNo());
        context.setVariable("cigarette", reservation.getTable().getCigarette());
        context.setVariable("windowView", reservation.getTable().getWindowView());

        emailService.sendEmailWithHtmlTemplate(
                reservation.getUser().getEmail(),
                "Dear User, Your reservation have recieved !",
                templateName, context
        );

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
