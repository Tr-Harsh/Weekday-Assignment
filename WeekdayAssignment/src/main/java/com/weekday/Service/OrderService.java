package com.weekday.Service;

import com.weekday.Models.Order.*;
import com.weekday.Models.SlotStatus;
import com.weekday.Utils.Constants;
import com.weekday.Models.OrderRequest;

import java.util.*;

public class OrderService {

    private Map<Integer, SlotStatus> cookingSlots;
    private final Map<Integer, List<Integer>> orderSlotMap = new HashMap<>();
    private Map<Integer, OrderContext> orderContextMap;
    PriorityQueue<OrderContext> orderQueue = new PriorityQueue<>(7, new OrderComparator());

    public OrderService() {
    }

    public void setCookingSlots(Map<Integer, SlotStatus> cookingSlots) {
        this.cookingSlots = cookingSlots;
    }

    public void setOrderContextMap(Map<Integer, OrderContext> orderContextMap) {
        this.orderContextMap = orderContextMap;
    }

    public List<OrderContext> processOrders(List<OrderRequest> orderRequests) throws Exception {
        for(OrderRequest orderRequest : orderRequests) {
            if (!validateOrder(orderRequest)) {
                changeOrderStatus(orderRequest.getOrderId(), Denied.instance());
                continue;
            }
            OrderContext orderContext = orderContextMap.get(orderRequest.getOrderId());
            changeOrderStatus(orderRequest.getOrderId(), Accepted.instance());
            Boolean vacancyNotAvailable = true;
            Double waitingETA = 0D;
            while (vacancyNotAvailable) {
                if (findVacantCookingSlots(orderRequest)) {
                    Double eta = calculateETA(orderRequest, waitingETA);
                    if(eta>=Constants.ETA_LIMIT_EXCEEDED) {
                        changeOrderStatus(orderRequest.getOrderId(), Denied.instance());
                    }
                    else {
                        assignVacantCookingSlots(orderRequest);
                        changeOrderStatus(orderRequest.getOrderId(), Preparing.instance());
                        orderContext.setEta(eta);
                        orderQueue.add(orderContext);
                    }
                    vacancyNotAvailable = false;
                } else {
                    if(orderQueue.isEmpty()) throw new IllegalStateException("Application stuck in illegal state");
                    else waitingETA+=createVacancy();
                }
            }
        }
        return createFinalOrderContext();
    }

    private Boolean validateOrder(OrderRequest orderRequest) {
        List<String> meals = orderRequest.getMeals();
        if (!meals.contains("A") && !meals.contains("M")) return false;
        return (Collections.frequency(meals, "A") * Constants.APPETIZER_SLOTS_COUNT + Collections.frequency(meals, "M") * Constants.MC_SLOTS_COUNT) <= 7;
    }

    private Boolean findVacantCookingSlots(OrderRequest orderRequest){
        Integer requirement = getCookingSlotReqForOrder(orderRequest);
        Integer vacancy = 0;
        for(Map.Entry<Integer, SlotStatus> slot: cookingSlots.entrySet()){
            if(slot.getValue().equals(SlotStatus.VACANT)) {
                vacancy+=1;
                if(vacancy>=requirement) return true;
            }
        }
        return false;
    }

    private void assignVacantCookingSlots(OrderRequest orderRequest) {
        List<Integer> assignedSlots = new ArrayList<>();
        Integer requirement = getCookingSlotReqForOrder(orderRequest);
        for(Map.Entry<Integer, SlotStatus> slot: cookingSlots.entrySet()){
            if(slot.getValue().equals(SlotStatus.VACANT)){
                assignedSlots.add(slot.getKey());
                slot.setValue(SlotStatus.OCCUPIED);
                requirement--;
            }
            if(requirement<=0) break;
        }
        orderSlotMap.put(orderRequest.getOrderId(), assignedSlots);
    }

    private void changeOrderStatus(Integer orderId, OrderState orderState) throws Exception {
        OrderContext orderContext = orderContextMap.get(orderId);
        orderContext.getCurrentState().updateState(orderContext, orderState);
        orderContextMap.put(orderId, orderContext);
    }

    private Integer getCookingSlotReqForOrder(OrderRequest orderRequest){
        List<String> meals = orderRequest.getMeals();
        return Collections.frequency(meals, "A") * Constants.APPETIZER_SLOTS_COUNT + Collections.frequency(meals, "M") * Constants.MC_SLOTS_COUNT;
    }

    private Double calculateETA(OrderRequest orderRequest, Double waitingETA) {
        List<String> meals = orderRequest.getMeals();
        Double cookingMinutes = 0D;
        if (meals.contains("M")) cookingMinutes = Constants.MC_TIME;
        else cookingMinutes = Constants.APPETIZER_TIME;
        Double deliveryTime = orderRequest.getDistance()*Constants.TIME_PER_KM;
        return cookingMinutes+deliveryTime+waitingETA;
    }

    private Double createVacancy() throws Exception {
        if(!orderQueue.isEmpty()) {
            OrderContext orderContext = orderQueue.poll();
            changeOrderStatus(orderContext.getOrderId(), Delivered.instance());
            List<Integer> slots = orderSlotMap.get(orderContext.getOrderId());
            slots.forEach(slotId-> cookingSlots.put(slotId, SlotStatus.VACANT));
            orderSlotMap.remove(orderContext.getOrderId());
            return orderContext.getEta();
        }
        return 0D;
    }

    private List<OrderContext> createFinalOrderContext() throws Exception {
        List<OrderContext> orderContexts = new ArrayList<>();
        while(!orderQueue.isEmpty()) createVacancy();
        orderContextMap.forEach((k,v)-> orderContexts.add(v));
        return orderContexts;
    }

    static class OrderComparator implements Comparator<OrderContext>{
        public int compare(OrderContext o1, OrderContext o2) {
            if (o1.getEta() > o2.getEta())
                return 1;
            else if (o1.getEta() < o2.getEta())
                return -1;
            return 0;
        }
    }

    private List<OrderContext> initOrderContexts(List<OrderRequest> orderRequests) {
        List<OrderContext> orderContexts = new ArrayList<>();
        orderRequests.forEach(orderRequest -> orderContexts.add(new OrderContext(orderRequest.getOrderId(), 0.0, Placed.instance())));
        return orderContexts;
    }
}
