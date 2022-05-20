package com.weekday.Models.Order;

import java.util.Arrays;
import java.util.List;

public class Preparing extends OrderState{

    public OrderStateEnum orderStateEnum = OrderStateEnum.PREPARING;
    private static final Preparing instance = new Preparing();

    private Preparing() {}
    public static Preparing instance() {
        return instance;
    }

    @Override
    public OrderStateEnum returnOrderStateEnum() {
        return orderStateEnum;
    }

    @Override
    public List<String> nextStates() {
        return Arrays.asList(OrderStateEnum.DELIVERED.name());
    }

    @Override
    public void updateState(OrderContext context, OrderState orderState) throws Exception {
        if(!nextStates().contains(orderState.returnOrderStateEnum().name())) throw new Exception("Invalid State Change");
        context.setCurrentState(orderState);
    }
}
