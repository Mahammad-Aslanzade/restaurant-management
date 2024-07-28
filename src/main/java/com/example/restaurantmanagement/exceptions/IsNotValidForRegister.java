package com.example.restaurantmanagement.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IsNotValidForRegister extends RuntimeException {
    private String verificationCode;
    private String userEmail;
    private String message;
    private String logMessage;

    public IsNotValidForRegister(String verificationCode, String userEmail, String message, String logMessage) {
        super(message);
        this.verificationCode = verificationCode;
        this.userEmail = userEmail;
        this.message = message;
        this.logMessage = logMessage;
    }
}
