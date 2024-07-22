package com.example.restaurantmanagement.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlreadyExistException extends RuntimeException{
    private String message;
    private String logMessage;

    public AlreadyExistException(String message, String logMessage) {
        super(message);
        this.message = message;
        this.logMessage = logMessage;
    }
}
