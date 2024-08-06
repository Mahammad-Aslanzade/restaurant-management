package com.example.restaurantmanagement.enums;

public enum OrderStatus {
    PREPARING, READY , DELIVERING, TAKEN, PLANNED , COMPLETED;

    public static OrderStatus convertToEnum(String status) {
        for (OrderStatus item : OrderStatus.values()) {
            if (item.name().equalsIgnoreCase(status)) {
                return item;
            }
        }
        throw new IllegalArgumentException(String.format("%s is not enum constraint", status));
    }
}
