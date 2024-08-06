package com.example.restaurantmanagement.enums;

public enum OrderType {
    TAKEAWAY, DELIVERY, PLANNED;

    public static OrderType convertToEnum(String type) {
        for (OrderType item : OrderType.values()) {
            if (item.name().equalsIgnoreCase(type)) {
                return item;
            }
        }
        throw new IllegalArgumentException(String.format("%s is not enum constraint", type));
    }
}
