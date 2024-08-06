package com.example.restaurantmanagement.enums;

public enum PaymentType {
    CASH , CARD;

    public static PaymentType convertToEnum(String type) {
        for (PaymentType item : PaymentType.values()) {
            if (item.name().equalsIgnoreCase(type)) {
                return item;
            }
        }
        throw new IllegalArgumentException(String.format("%s is not enum constraint", type));
    }
}
