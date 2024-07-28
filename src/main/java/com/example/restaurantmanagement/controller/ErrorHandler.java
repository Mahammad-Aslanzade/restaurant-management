package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.exceptions.AlreadyExistException;
import com.example.restaurantmanagement.exceptions.IsNotValidForRegister;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.model.exception.AlreadyExistDto;
import com.example.restaurantmanagement.model.exception.IsNotValidRegDto;
import com.example.restaurantmanagement.model.exception.NotFoundDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HashMap<String, String> handleValidations(MethodArgumentNotValidException ex) {
        HashMap<String, String> errorList = new HashMap<>();
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        errors.forEach((e) -> {
            errorList.put(
                    e.getField(), e.getDefaultMessage()
            );
        });
        return errorList;
    }

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

    @ExceptionHandler(IsNotValidForRegister.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IsNotValidRegDto handleNotValidRegister(IsNotValidForRegister exception) {
        log.error(exception.getLogMessage());
        return new IsNotValidRegDto(
                exception.getVerificationCode(),
                exception.getUserEmail(),
                exception.getMessage()
        );
    }


}
