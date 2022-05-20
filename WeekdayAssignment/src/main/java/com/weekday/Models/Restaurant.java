package com.weekday.Models;

import java.util.List;

public class Restaurant {
    private Integer restaurantID;
    private String restaurantName;
    private List<Slot> cookingSlots;

    public Restaurant(Integer restaurantID, String restaurantName, List<Slot> cookingSlots) {
        this.restaurantID = restaurantID;
        this.restaurantName = restaurantName;
        this.cookingSlots = cookingSlots;
    }

    public Restaurant() {
    }

    public Integer getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Integer restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public List<Slot> getCookingSlots() {
        return cookingSlots;
    }

    public void setCookingSlots(List<Slot> cookingSlots) {
        this.cookingSlots = cookingSlots;
    }
}
