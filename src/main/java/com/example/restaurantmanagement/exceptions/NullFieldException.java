package com.example.restaurantmanagement.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NullFieldException extends RuntimeException {
    private String field;
    private String message;
    private String logMessage;

    public NullFieldException(String message, String logMessage) {
        super(message);
        this.message = message;
        this.logMessage = logMessage;
    }

}
