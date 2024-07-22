package com.example.restaurantmanagement.enums;

public enum ExceptionDetails {
    BANNER_NOT_FOUND, ALREADY_EXIST;

    public String message() {
        return this.toString();
    }

    public String createLogMessage(String methodName, String identifierKey, String identifierName) {
        return String.format("ACTION.ERROR.%s %s : %s", methodName, identifierKey, identifierName);
    }

    public String createLogMessage(String methodName) {
        return String.format("ACTION.ERROR.%s", methodName);
    }
}
