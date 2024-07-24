package com.example.restaurantmanagement.enums;

public enum ExceptionDetails {
    BANNER_NOT_FOUND, CATEGORY_NOT_FOUND, MEAL_NOT_FOUND,
    ALREADY_EXIST;

    public String message() {
        return this.toString();
    }

    public String createLogMessage(String methodName, String identifierKey, String identifierValue) {
        return String.format("ACTION.ERROR.%s %s : %s", methodName, identifierKey, identifierValue);
    }

    public String createLogMessage(String methodName, String identifierValue) {
        return String.format("ACTION.ERROR.%s id : %s", methodName, identifierValue);
    }

    public String createLogMessage(String methodName) {
        return String.format("ACTION.ERROR.%s", methodName);
    }
}
