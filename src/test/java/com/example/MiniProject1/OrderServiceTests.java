package com.example.MiniProject1;

import com.example.model.Order;
import com.example.model.Product;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order testOrder;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        List<Product> products = List.of(
                new Product("Test Product", 100.0)
        );
        testOrder = new Order(userId, 100.0, products);
    }

    // Add Order Tests
    @Test
    void testAddOrder_Positive() {
        when(orderRepository.addOrder(any(Order.class))).thenReturn(testOrder);

        Order result = orderService.addOrder(testOrder);

        assertNotNull(result);
        assertEquals(testOrder.getUserId(), result.getUserId());
        assertEquals(testOrder.getTotalPrice(), result.getTotalPrice());
        verify(orderRepository).addOrder(any(Order.class));
    }

    @Test
    void testAddOrder_Negative_EmptyProducts() {
        Order orderWithEmptyProducts = new Order(userId, 100.0, new ArrayList<>());
        
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.addOrder(orderWithEmptyProducts);
        });
        
        verify(orderRepository, never()).addOrder(any());
    }

    @Test
    void testAddOrder_Edge_NullOrder() {
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.addOrder(null);
        });

        verify(orderRepository, never()).addOrder(any());
    }

    // Get Orders Tests
    @Test
    void testGetOrders_Positive() {
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(testOrder);
        when(orderRepository.getOrders()).thenReturn(orders);

        ArrayList<Order> result = orderService.getOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testOrder.getId(), result.get(0).getId());
    }

    @Test
    void testGetOrders_Negative_EmptyList() {
        lenient().when(orderRepository.getOrders()).thenReturn(new ArrayList<>());

        ArrayList<Order> result = orderService.getOrders();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetOrders_Edge_NullList() {
        lenient().when(orderRepository.getOrders()).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> {
            orderService.getOrders();
        });
    }

    // Get Order By Id Tests
    @Test
    void testGetOrderById_Positive() {
        UUID orderId = testOrder.getId();
        when(orderRepository.getOrderById(eq(orderId))).thenReturn(testOrder);

        Order result = orderService.getOrderById(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
    }

    @Test
    void testGetOrderById_Negative_NonExistentId() {
        UUID orderId = UUID.randomUUID();
        lenient().when(orderRepository.getOrderById(orderId)).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> {
            orderService.getOrderById(orderId);
        });
    }

    @Test
    void testGetOrderById_Edge_NullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.getOrderById(null);
        });

        verify(orderRepository, never()).getOrderById(any());
    }

    // Delete Order Tests
    @Test
    void testDeleteOrderById_Positive() {
        UUID orderId = testOrder.getId();
        when(orderRepository.getOrderById(eq(orderId))).thenReturn(testOrder);
        doNothing().when(orderRepository).deleteOrderById(orderId);

        assertDoesNotThrow(() -> {
            orderService.deleteOrderById(orderId);
        });

        verify(orderRepository).deleteOrderById(orderId);
    }

    @Test
    void testDeleteOrderById_Negative_NonExistentId() {
        UUID orderId = UUID.randomUUID();
        lenient().when(orderRepository.getOrderById(orderId)).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> {
            orderService.deleteOrderById(orderId);
        });

        verify(orderRepository, never()).deleteOrderById(any());
    }

    @Test
    void testDeleteOrderById_Edge_NullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.deleteOrderById(null);
        });

        verify(orderRepository, never()).deleteOrderById(any());
    }
}