package com.weekday.Models.Order;

import java.util.Arrays;
import java.util.List;

public class Accepted extends OrderState{

    public OrderStateEnum orderStateEnum = OrderStateEnum.ACCEPTED;
    private static final Accepted instance = new Accepted();

    private Accepted() {}
    public static Accepted instance() {
        return instance;
    }

    @Override
    public OrderStateEnum returnOrderStateEnum() {
        return orderStateEnum;
    }

    @Override
    public List<String> nextStates() {
        return Arrays.asList(OrderStateEnum.PREPARING.name());
    }

    @Override
    public void updateState(OrderContext context, OrderState orderState) throws Exception {
        if(!nextStates().contains(orderState.returnOrderStateEnum().name())) throw new Exception("Invalid State Change");
        context.setCurrentState(orderState);
    }
}
