package com.weekday.Models.Order;

import java.util.ArrayList;
import java.util.List;

public class Denied extends OrderState{

    public OrderStateEnum orderStateEnum = OrderStateEnum.DENIED;
    private static final Denied instance = new Denied();

    private Denied() {}
    public static Denied instance() {
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
