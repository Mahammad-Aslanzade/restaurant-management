package com.example.restaurantmanagement.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidException extends RuntimeException {
    private String parameter;
    private String message;
    private String logMessage;

    public InvalidException(String parameter, String message, String logMessage) {
        super(message);
        this.parameter = parameter;
        this.message = message;
        this.logMessage = logMessage;
    }

}
