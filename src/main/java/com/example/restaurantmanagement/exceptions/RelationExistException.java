package com.example.restaurantmanagement.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelationExistException extends RuntimeException{
    private String message;
    private String logMessage;

    public RelationExistException (String message , String logMessage){
        super(message);
        this.message = message;
        this.logMessage = logMessage;
    }
}
