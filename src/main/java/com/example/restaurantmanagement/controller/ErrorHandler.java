package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.exceptions.AlreadyExistException;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.model.exception.AlreadyExistDto;
import com.example.restaurantmanagement.model.exception.NotFoundDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NotFoundDto handleNotFound(NotFoundException exception) {
        log.error(exception.getLogMessage());
        return new NotFoundDto(exception.getMessage());
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AlreadyExistDto handleAlreadyExist(AlreadyExistException exception) {
        log.error(exception.getLogMessage());
        return new AlreadyExistDto(exception.getMessage());
    }


}
