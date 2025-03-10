package com.example.repository;

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
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new ClassPathResource(getDataPath()).getFile();
            ArrayList<Order> orders = objectMapper.readValue(file, new TypeReference<ArrayList<Order>>() {});

            orders.add(order);

            objectMapper.writeValue(file, orders);
            return order;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to write to orders.json");
        }
    }

    public  ArrayList<Order> getOrders() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new ClassPathResource(getDataPath()).getFile();
            ArrayList<Order> orders = objectMapper.readValue(file, new TypeReference<ArrayList<Order>>() {});

            return orders;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to write to orders.json");
        }
    }

    public Order getOrderById(UUID orderId){
        return getOrders().stream().filter(order->order.getId().equals(orderId)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
    }


    public void deleteOrderById(UUID orderId) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new ClassPathResource(getDataPath()).getFile();
            ArrayList<Order> orders = objectMapper.readValue(file, new TypeReference<ArrayList<Order>>() {
            });

            orders.removeIf(order -> order.getId().equals(orderId));

            objectMapper.writeValue(file, orders);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to write to carts.json");
        }
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
