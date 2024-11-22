package com.example.restaurantmanagement.configuration;

public enum MinioBuckets {

    MEALS("meals"),
    BANNER("banners"),
    MEAL_CATEGORIES("meal-categories")
    ;


    private final String label;

    public String label(){
        return this.label;
    }

    MinioBuckets(String label){
        this.label = label;
    }

}
