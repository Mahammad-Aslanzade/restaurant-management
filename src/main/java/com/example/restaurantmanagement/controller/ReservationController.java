package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.reservation.ReservationCUDto;
import com.example.restaurantmanagement.model.reservation.ReservationDto;
import com.example.restaurantmanagement.model.reservation.ReservationWithoutUser;
import com.example.restaurantmanagement.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public List<ReservationDto> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{reservation-id}")
    public ReservationDto getReservationById(@PathVariable("reservation-id") String reservationId) {
        return reservationService.getReservationById(reservationId);
    }

    @GetMapping("/current-user")
    public List<ReservationWithoutUser> getCurrentReservations() {
        return reservationService.getCurrentReservations();
    }

    @GetMapping("/user/{user-id}")
    public List<ReservationDto> getUserReservations(@PathVariable("user-id") String userId) {
        return reservationService.getUserReservations(userId);
    }

    @GetMapping("/table/{table-id}")
    public List<ReservationDto> getTableReservations(@PathVariable("table-id") String tableId) {
        return reservationService.getTableReservations(tableId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReservation(@RequestBody @Valid ReservationCUDto reservationCUDto) {
        reservationService.createReservation(reservationCUDto);
    }

    @PutMapping("/{reservation-id}")
    public void updateReservation(@PathVariable("reservation-id") String reservationId, @RequestBody @Valid ReservationCUDto reservationCUDto) {
        reservationService.updateReservation(reservationId, reservationCUDto);
    }

    @DeleteMapping("/{reservation-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable("reservation-id") String reservationId) {
        reservationService.deleteReservation(reservationId);
    }

}
