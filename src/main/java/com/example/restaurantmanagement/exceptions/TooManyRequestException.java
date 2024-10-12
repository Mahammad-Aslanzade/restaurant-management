package com.example.restaurantmanagement.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TooManyRequestException extends RuntimeException {
    private String logMessage;
    private String message;

    public TooManyRequestException(String logMessage, String message) {
        super(message);
        this.logMessage = logMessage;
        this.message = message;
    }

}
