package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.reservation.ReservationCUDto;
import com.example.restaurantmanagement.model.reservation.ReservationDto;
import com.example.restaurantmanagement.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public List<ReservationDto> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{reservationId}")
    public ReservationDto getReservationById(@PathVariable String reservationId) {
        return reservationService.getReservationById(reservationId);
    }

    @GetMapping("/user/{userId}")
    public List<ReservationDto> getUserReservations(@PathVariable String userId){
        return reservationService.getUserReservations(userId);
    }

    @GetMapping("/table/{tableId}")
    public List<ReservationDto> getTableReservations(@PathVariable String tableId){
        return reservationService.getTableReservations(tableId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReservation(@RequestBody @Valid ReservationCUDto reservationCUDto) {
        reservationService.createReservation(reservationCUDto);
    }

    @PutMapping("/{reservationId}")
    public void updateReservation(@PathVariable String reservationId, @RequestBody @Valid ReservationCUDto reservationCUDto) {
        reservationService.updateReservation(reservationId, reservationCUDto);
    }

    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable String reservationId) {
        reservationService.deleteReservation(reservationId);
    }

}
