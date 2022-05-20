package com.weekday.Models.Order;

import java.util.ArrayList;
import java.util.List;

public class Cancelled extends OrderState{

    public OrderStateEnum orderStateEnum = OrderStateEnum.CANCELLED;
    private static final Cancelled instance = new Cancelled();

    private Cancelled() {}
    public static Cancelled instance() {
        return instance;
    }

    @Override
    public OrderStateEnum returnOrderStateEnum() {
        return orderStateEnum;
    }

    @Override
    public List<String> nextStates() {
        return new ArrayList<>();
    }

    @Override
    public void updateState(OrderContext context, OrderState orderState) throws Exception {
        if(!nextStates().contains(orderState.returnOrderStateEnum().name())) throw new Exception("Invalid State Change");
        context.setCurrentState(orderState);
    }
}
