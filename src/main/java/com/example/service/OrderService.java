package com.example.service;
import com.example.model.Order;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class OrderService extends MainService<Order> {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (order.getProducts() == null) {
            throw new IllegalArgumentException("Order cannot have null products");
        }
        return orderRepository.addOrder(order);
    }

    public ArrayList<Order> getOrders() {
        ArrayList<Order> orders = orderRepository.getOrders();
        if (orders == null) {
            throw new IllegalStateException("Failed to retrieve orders");
        }
        return orders;
    }

    public Order getOrderById(UUID orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        Order order = orderRepository.getOrderById(orderId);
        if (order == null) {
            throw new IllegalStateException("Order not found with ID: " + orderId);
        }
        return order;
    }

    public void deleteOrderById(UUID orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        Order order = orderRepository.getOrderById(orderId);
        if (order == null) {
            throw new IllegalStateException("Order not found with ID: " + orderId);
        }
        orderRepository.deleteOrderById(orderId);
    }
}

