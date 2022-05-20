package com.weekday.Models.Order;

public class OrderContext {
    private Integer orderId;
    private Double eta;
    private OrderState currentState;

    public OrderContext(){
        this.currentState = Placed.instance();
    }

    public OrderContext(Integer orderId, Double eta, OrderState currentState) {
        this.orderId = orderId;
        this.eta = eta;
        this.currentState = currentState;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Double getEta() {
        return eta;
    }

    public void setEta(Double eta) {
        this.eta = eta;
    }

    public OrderState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(OrderState currentState) {
        this.currentState = currentState;
    }
}
