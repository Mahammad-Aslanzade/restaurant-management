package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.exceptions.*;
import com.example.restaurantmanagement.model.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, String> handleValidations(MethodArgumentNotValidException ex) {
        HashMap<String, String> errorList = new HashMap<>();
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        errors.forEach((e) ->
            errorList.put(
                    e.getField(), e.getDefaultMessage()
            )
        );
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

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageDto handleMissingRequestPart(MissingServletRequestPartException exception){
        log.error(exception.getMessage());
        return new ExceptionMessageDto(exception.getMessage());
    }

    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InvalidExceptionDto handleInvalidParameter(InvalidException exception) {
        log.error(exception.getLogMessage());
        return new InvalidExceptionDto(
                exception.getParameter(),
                exception.getMessage()
        );
    }

    @ExceptionHandler(NotAllowedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public HashMap<String, String> handleNotAllowedMessage(NotAllowedException exception) {
        log.error(exception.getLogMessage());
        HashMap<String , String> response = new HashMap<>();
        response.put("message", exception.getMessage());
        return response;
    }

    @ExceptionHandler(NullFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, String> handleNullField(NullFieldException exception) {
        log.error(exception.getLogMessage());
        HashMap<String, String> response = new HashMap<>();
        response.put(exception.getField(), exception.getMessage());
        return response;
    }

    @ExceptionHandler(RelationExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageDto handleRelationExistException(RelationExistException exception){
        log.error(exception.getLogMessage());
        return new ExceptionMessageDto(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageDto handleInvalidEnumException(MethodArgumentTypeMismatchException exception){
        log.error("INCORRECT.ENUM");
        String message = String.format("Invalid value for field '%s': '%s'. Please provide one of the valid values.", exception.getName(), exception.getValue());
        return new ExceptionMessageDto(message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessageDto globalExceptionHandler(Exception e){
        log.error(e.getMessage());
        return new ExceptionMessageDto(e.getMessage());
    }


}
