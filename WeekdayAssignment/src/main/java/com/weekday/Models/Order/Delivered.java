package com.weekday.Models.Order;

import java.util.ArrayList;
import java.util.List;

public class Delivered extends OrderState{

    public OrderStateEnum orderStateEnum = OrderStateEnum.DELIVERED;
    private static final Delivered instance = new Delivered();

    private Delivered() {}
    public static Delivered instance() {
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
