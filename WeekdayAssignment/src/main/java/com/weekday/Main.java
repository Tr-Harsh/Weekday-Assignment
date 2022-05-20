package com.weekday;

import com.weekday.Models.Order.OrderContext;
import com.weekday.Models.OrderRequest;
import com.weekday.Models.SlotStatus;
import com.weekday.Service.OrderService;
import com.weekday.Utils.OrderUtils;
import com.weekday.Utils.RestaurantUtils;

import java.util.List;
import java.util.Map;

public class Main {
    private static final OrderService orderService = new OrderService();

    public static void main( String[] args ) throws Exception {
        List<OrderRequest> orderRequests = OrderUtils.initOrderRequests();
        Map<Integer, SlotStatus> slots = RestaurantUtils.initRestaurant();
        Map<Integer, OrderContext> orderContextMap = RestaurantUtils.initOrderContext(orderRequests);
        orderService.setCookingSlots(slots);
        orderService.setOrderContextMap(orderContextMap);
        List<OrderContext> orderContexts = orderService.processOrders(orderRequests);
        OrderUtils.prettyPrint(orderContexts);
    }
}
