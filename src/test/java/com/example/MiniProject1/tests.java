package com.example.MiniProject1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.model.*;
import com.example.repository.*;
import com.example.service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;



@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @InjectMocks private UserService userService;

    private User user;
    private Order order;

    @BeforeEach
    void setUp() {
        user = new User("John Doe", UUID.randomUUID(), new ArrayList<>());
        order = new Order(UUID.randomUUID(), user.getId(), 100.00, new ArrayList<>());
    }

    // TEST: addUser()
    @Test void testAddUser_Positive() { when(userRepository.addUser(any(User.class))).thenReturn(user); assertEquals(user, userService.addUser(user)); }
    @Test void testAddUser_Negative() { when(userRepository.addUser(any(User.class))).thenThrow(new RuntimeException("Cannot add user")); assertThrows(RuntimeException.class, () -> userService.addUser(user)); }
    @Test void testAddUser_EdgeCase() { User emptyUser = new User(null, null, new ArrayList<>()); when(userRepository.addUser(emptyUser)).thenReturn(emptyUser); assertEquals("", userService.addUser(emptyUser).getName()); }

    // TEST: getUsers()
    @Test
    void testGetUsers_Positive() {
        List<User> mockUsers = List.of(user);
        when(userRepository.getUsers()).thenReturn((ArrayList<User>) mockUsers);

        List<User> result = userService.getUsers();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(user, result.getFirst());
    }

    @Test
    void testGetUsers_Negative() {
        when(userRepository.getUsers()).thenReturn(new ArrayList<>());

        List<User> result = userService.getUsers();

        assertTrue(result.isEmpty(), "Expected an empty list, but it was not.");
    }


    @Test
    void testGetUsers_EdgeCase() {
        when(userRepository.getUsers()).thenReturn(new ArrayList<>());

        List<User> result = userService.getUsers();

        assertEquals(0, result.size());
    }

    // TEST: getUserById()
    @Test void testGetUserById_Positive() { when(userRepository.getUserById(user.getId())).thenReturn(user); assertEquals(user, userService.getUserById(user.getId())); }
    @Test void testGetUserById_Negative() { when(userRepository.getUserById(any())).thenReturn(null); assertNull(userService.getUserById(UUID.randomUUID())); }
    @Test void testGetUserById_EdgeCase() { when(userRepository.getUserById(new UUID(0, 0))).thenReturn(null); assertNull(userService.getUserById(new UUID(0, 0))); }

    // TEST: deleteUserById()
    @Test void testDeleteUserById_Positive() { doNothing().when(userRepository).deleteUserById(user.getId()); assertDoesNotThrow(() -> userService.deleteUserById(user.getId())); }
    @Test void testDeleteUserById_Negative() { doThrow(new RuntimeException("User not found")).when(userRepository).deleteUserById(any()); assertThrows(RuntimeException.class, () -> userService.deleteUserById(UUID.randomUUID())); }
    @Test void testDeleteUserById_EdgeCase() { doNothing().when(userRepository).deleteUserById(new UUID(0, 0)); assertDoesNotThrow(() -> userService.deleteUserById(new UUID(0, 0))); }

    // TEST: getOrdersByUserId()
    @Test void testGetOrdersByUserId_Positive() { when(userRepository.getOrdersByUserId(user.getId())).thenReturn(List.of(order)); assertFalse(userService.getOrdersByUserId(user.getId()).isEmpty()); }
    @Test void testGetOrdersByUserId_Negative() { when(userRepository.getOrdersByUserId(any())).thenReturn(Collections.emptyList()); assertTrue(userService.getOrdersByUserId(UUID.randomUUID()).isEmpty()); }
    @Test void testGetOrdersByUserId_EdgeCase() { when(userRepository.getOrdersByUserId(new UUID(0, 0))).thenReturn(Collections.emptyList()); assertTrue(userService.getOrdersByUserId(new UUID(0, 0)).isEmpty()); }

    // TEST: addOrderToUser()
    @Test void testAddOrderToUser_Positive() { doNothing().when(userRepository).addOrderToUser(user.getId(), order); assertDoesNotThrow(() -> userService.addOrderToUser(user.getId())); }
    @Test void testAddOrderToUser_Negative() { doThrow(new RuntimeException("User not found")).when(userRepository).addOrderToUser(any(), any()); assertThrows(RuntimeException.class, () -> userService.addOrderToUser(UUID.randomUUID())); }
    @Test void testAddOrderToUser_EdgeCase() { doNothing().when(userRepository).addOrderToUser(new UUID(0, 0), order); assertDoesNotThrow(() -> userService.addOrderToUser(new UUID(0, 0))); }

    // TEST: removeOrderFromUser()
    @Test void testRemoveOrderFromUser_Positive() { doNothing().when(userRepository).removeOrderFromUser(user.getId(), order.getId()); assertDoesNotThrow(() -> userService.removeOrderFromUser(user.getId(), order.getId())); }
    @Test void testRemoveOrderFromUser_Negative() { doThrow(new RuntimeException("Order not found")).when(userRepository).removeOrderFromUser(any(), any()); assertThrows(RuntimeException.class, () -> userService.removeOrderFromUser(UUID.randomUUID(), UUID.randomUUID())); }
    @Test void testRemoveOrderFromUser_EdgeCase() { doNothing().when(userRepository).removeOrderFromUser(new UUID(0, 0), new UUID(0, 0)); assertDoesNotThrow(() -> userService.removeOrderFromUser(new UUID(0, 0), new UUID(0, 0))); }
}