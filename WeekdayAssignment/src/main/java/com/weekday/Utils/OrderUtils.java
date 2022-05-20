package com.weekday.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weekday.Models.Order.OrderContext;
import com.weekday.Models.Order.OrderStateEnum;
import com.weekday.Models.OrderRequest;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderUtils {

    public static List<OrderRequest> initOrderRequests(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return Arrays.asList(objectMapper.readValue(Paths.get("input.json").toFile(), OrderRequest[].class));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void prettyPrint(List<OrderContext> orderContexts) {
        orderContexts.forEach(orderContext->{
            if(orderContext.getCurrentState().returnOrderStateEnum().name().equals(OrderStateEnum.DELIVERED.name())) {
                System.out.println("Order:"+ orderContext.getOrderId() + " will get delivered in " + orderContext.getEta() +" minutes");
            }
            else if(Arrays.asList(OrderStateEnum.DENIED.name(), OrderStateEnum.CANCELLED.name()).contains(orderContext.getCurrentState().returnOrderStateEnum().name())){
                System.out.println("Order:"+ orderContext.getOrderId() + " is denied");
            }
        });
    }
}
