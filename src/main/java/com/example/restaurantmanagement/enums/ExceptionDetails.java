package com.example.restaurantmanagement.enums;

public enum ExceptionDetails {
    BANNER_NOT_FOUND,
    CATEGORY_NOT_FOUND,
    MEAL_NOT_FOUND,
    EMAIL_NOT_FOUND,
    USER_NOT_FOUND,
    VERIFICATION_NOT_FOUND,
    FEEDBACK_NOT_FOUND,
    TABLE_NOT_FOUND,
    RESERVATION_NOT_FOUND,
    ORDER_NOT_FOUND,
    ALREADY_EXIST,
    THIS_EMAIL_IS_ALREADY_EXIST,
    TABLE_IS_ALREADY_EXIST,
    POSITION_IS_FULL,
    INVALID_ADDRESS,
    INVALID_VALIDATION_CODE;

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
