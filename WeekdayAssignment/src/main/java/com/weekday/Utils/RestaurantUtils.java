package com.weekday.Utils;

import com.weekday.Models.Order.OrderContext;
import com.weekday.Models.Order.Placed;
import com.weekday.Models.OrderRequest;
import com.weekday.Models.SlotStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantUtils {

    public static Map<Integer, SlotStatus> initRestaurant() {
        Map<Integer, SlotStatus> slots = new HashMap<>();
        for(int i=0;i<7;i++) slots.put(i+1, SlotStatus.VACANT);
        return slots;
    }

    public static Map<Integer, OrderContext> initOrderContext(List<OrderRequest> orderRequests) {
        Map<Integer, OrderContext> orderContextMap = new HashMap<>();
        orderRequests.forEach(orderRequest-> orderContextMap.put(orderRequest.getOrderId(), new OrderContext(orderRequest.getOrderId(), 0.0, Placed.instance())));
        return orderContextMap;
    }
}
