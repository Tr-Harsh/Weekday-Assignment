package com.weekday.Models.Order;

import java.util.List;

public abstract class OrderState {
    public abstract OrderStateEnum returnOrderStateEnum();
    public abstract List<String> nextStates();
    public abstract void updateState(OrderContext context, OrderState orderState) throws Exception;
}
