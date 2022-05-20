package com.weekday.Service;

import com.weekday.Models.Restaurant;

import java.util.HashMap;
import java.util.Map;

public class RestaurantService {

    private Map<Integer, Restaurant> restaurantMap = new HashMap<>();


    public void register(Restaurant restaurant){
        restaurantMap.put(restaurant.getRestaurantID(), restaurant);
    }

    public void revoke(Restaurant restaurant){
        restaurantMap.remove(restaurant.getRestaurantID());
    }

}
