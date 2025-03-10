package com.example.repository;

import com.example.model.Cart;
import com.example.model.Order;
import org.springframework.stereotype.Repository;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
@SuppressWarnings("rawtypes")


public class OrderRepository extends MainRepository<Order> {

    public OrderRepository() {

    }

    public Order addOrder(Order order) {
        save(order);
        return order;
    }

    public  ArrayList<Order> getOrders() {
        return findAll();
    }

    public Order getOrderById(UUID orderId){
        ArrayList<Order> orders = getOrders();
        for(Order order : orders){
            if(order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }


    public void deleteOrderById(UUID orderId) {
        ArrayList<Order> orders = getOrders();
        orders.removeIf(order -> order.getId().equals(orderId));
    }


    //overrides from the main repo
    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/orders.json";
    }

    @Override
    protected Class<Order[]> getArrayType() {
        return Order[].class;
    }

}
