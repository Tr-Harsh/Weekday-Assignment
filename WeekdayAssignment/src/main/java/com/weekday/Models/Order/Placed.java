package com.weekday.Models.Order;

import java.util.Arrays;
import java.util.List;

public class Placed extends OrderState{

    public OrderStateEnum orderStateEnum = OrderStateEnum.PLACED;
    private static final Placed instance = new Placed();

    private Placed() {}
    public static Placed instance() {
        return instance;
    }

    @Override
    public OrderStateEnum returnOrderStateEnum() {
        return orderStateEnum;
    }

    @Override
    public List<String> nextStates() {
        return Arrays.asList(OrderStateEnum.ACCEPTED.name(), OrderStateEnum.CANCELLED.name(), OrderStateEnum.DENIED.name());
    }

    @Override
    public void updateState(OrderContext context, OrderState orderState) throws Exception {
        if(!nextStates().contains(orderState.returnOrderStateEnum().name())) throw new Exception("Invalid State Change");
        context.setCurrentState(orderState);
    }
}
