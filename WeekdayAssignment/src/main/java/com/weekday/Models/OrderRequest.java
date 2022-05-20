package com.weekday.Models;

import java.util.List;

public class OrderRequest {
    private Integer orderId;
    private List<String> meals;
    private Double distance;

    public OrderRequest(Integer orderId, List<String> meals, Double distance) {
        this.orderId = orderId;
        this.meals = meals;
        this.distance = distance;
    }

    public OrderRequest() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public List<String> getMeals() {
        return meals;
    }

    public void setMeals(List<String> meals) {
        this.meals = meals;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
