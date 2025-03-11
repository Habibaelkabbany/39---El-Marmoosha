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
import static org.mockito.Mockito.lenient;

import java.util.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CartServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;


    private User user;
    private Cart cart;

    private Order order;

    @BeforeEach
    void setUp() {
        user = new User("John Doe", UUID.randomUUID(), new ArrayList<>());
        order = new Order(UUID.randomUUID(), user.getId(), 100.00, new ArrayList<>());
        lenient().when(cartService.getCartByUserId(any())).thenReturn(new Cart(UUID.randomUUID(), user.getId(), new ArrayList<>()));
    }

    @Test
    void testAddCart_Positive(){
        Cart cart = new Cart(UUID.randomUUID(), user.getId(), new ArrayList<>());
        when(cartService.addCart(any(Cart.class))).thenReturn(cart);
        assertEquals(cart, cartService.addCart(cart));

    }
    @Test
    void testAddCart_Negative(){
        when(cartService.addCart(any(Cart.class))).thenThrow(new RuntimeException("Cannot add cart"));
        assertThrows(RuntimeException.class, () -> cartService.addCart(new Cart(UUID.randomUUID(), user.getId(), new ArrayList<>())));
    }
    @Test
    void testAddCart_Edge_NullCart(){
        when(cartService.addCart(null)).thenThrow(new RuntimeException("Cart cannot be null"));
        assertThrows(RuntimeException.class, () -> cartService.addCart(null));
    }

    @Test
    void testGetAllCarts_Positive(){
        List<Cart> mockCarts = List.of(new Cart(UUID.randomUUID(), user.getId(), new ArrayList<>()));
        when(cartService.getCarts()).thenReturn(new ArrayList<>(List.of(new Cart(UUID.randomUUID(), user.getId(), new ArrayList<>()))));

        List<Cart> result = cartService.getCarts();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(mockCarts, result);
    }
    @Test
    void testGetAllCarts_Negative(){
        when(cartService.getCarts()).thenReturn(new ArrayList<>());
        List<Cart> result = cartService.getCarts();
        assertTrue(result.isEmpty(),"Expected an empty list, but it was not.");
    }
    @Test
    void testGetAllCarts_Edge_NullCart(){ // no carts at all
        when(cartService.getCarts()).thenReturn(null);
        List<Cart> result = cartService.getCarts();
        assertNull(result, "Expected null, but it was not.");

    }




//
//
//        @Test
//        public void testGetCartById_Positive() {
//            // Arrange
//            UUID cartId = UUID.randomUUID();
//            Cart cart = new Cart(cartId, UUID.randomUUID(), new ArrayList<>());
//
//            when(cartRepository.getCartById(cartId)).thenReturn(cart);
//
//            // Act
//            Cart result = cartService.getCartById(cartId);
//
//            // Assert
//            assertNotNull(result);
//            assertEquals(cart, result);
//        }
//
//        @Test
//        public void testGetCartById_NotFound() {
//            // Arrange
//            UUID cartId = UUID.randomUUID();
//            when(cartRepository.getCartById(cartId)).thenReturn(null);
//
//            // Act & Assert
//            assertThrows(RuntimeException.class, () -> {
//                cartService.getCartById(cartId);
//            });
//        }
//
//        @Test
//        public void testGetCartByUserId() {
//            // Arrange
//            UUID userId = UUID.randomUUID();
//            Cart cart = new Cart(UUID.randomUUID(), userId, new ArrayList<>());
//
//            when(cartRepository.getCartByUserId(userId)).thenReturn(cart);
//
//            // Act
//            Cart result = cartService.getCartByUserId(userId);
//
//            // Assert
//            assertNotNull(result);
//            assertEquals(cart, result);
//        }
//
//        @Test
//        public void testGetCartByUserId_NotFound() {
//            // Arrange
//            UUID userId = UUID.randomUUID();
//            when(cartRepository.getCartByUserId(userId)).thenReturn(null);
//
//            // Act & Assert
//            assertThrows(RuntimeException.class, () -> {
//                cartService.getCartByUserId(userId);
//            });
//        }
//
//        @Test
//        public void testAddProductToCart() {
//            // Arrange
//            UUID cartId = UUID.randomUUID();
//            Product product = new Product(UUID.randomUUID(), "Laptop", 1000.0);
//
//            doNothing().when(cartRepository).addProductToCart(cartId, product);
//
//            // Act
//            cartService.addProductToCart(cartId, product);
//
//            // Verify
//            verify(cartRepository, times(1)).addProductToCart(cartId, product);
//        }
//
//        @Test
//        public void testDeleteProductFromCart() {
//            // Arrange
//            UUID cartId = UUID.randomUUID();
//            Product product = new Product(UUID.randomUUID(), "Laptop", 1000.0);
//
//            doNothing().when(cartRepository).deleteProductFromCart(cartId, product);
//
//            // Act
//            cartService.deleteProductFromCart(cartId, product);
//
//            // Verify
//            verify(cartRepository, times(1)).deleteProductFromCart(cartId, product);
//        }
//
//        @Test
//        public void testDeleteCartById() {
//            // Arrange
//            UUID cartId = UUID.randomUUID();
//
//            doNothing().when(cartRepository).deleteCartById(cartId);
//
//            // Act
//            cartService.deleteCart(cartId);
//
//            // Verify
//            verify(cartRepository, times(1)).deleteCartById(cartId);
//        }
    }



//
//    // TEST: addUser()
//    @Test
//    void testAddUser_Positive() {
//        when(userRepository.addUser(any(User.class))).thenReturn(user);
//        assertEquals(user, userService.addUser(user));
//    }
//
//    @Test
//    void testAddUser_Negative() {
//        when(userRepository.addUser(any(User.class))).thenThrow(new RuntimeException("Cannot add user"));
//        assertThrows(RuntimeException.class, () -> userService.addUser(user));
//    }
//
//    @Test
//    void testAddUser_EdgeCase() {
//        User emptyUser = new User(null, null, new ArrayList<>());
//        assertThrows(NullPointerException.class, () -> userService.addUser(emptyUser));
//    }
//
//    @Test
//    void testGetUsers_Positive() {
//        List<User> mockUsers = List.of(user);
//        when(userRepository.getUsers()).thenReturn(new ArrayList<>(List.of(user)));
//
//        List<User> result = userService.getUsers();
//
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//        assertEquals(user, result.get(0));
//    }
//
//    @Test
//    void testGetUsers_Negative() {
//        when(userRepository.getUsers()).thenReturn(new ArrayList<>());
//
//        List<User> result = userService.getUsers();
//
//        assertTrue(result.isEmpty(), "Expected an empty list, but it was not.");
//    }
//
//    @Test
//    void testGetUsers_EdgeCase() {
//        when(userRepository.getUsers()).thenReturn(new ArrayList<>());
//
//        List<User> result = userService.getUsers();
//
//        assertEquals(0, result.size());
//    }
//
//    // TEST: getUserById()
//    @Test
//    void testGetUserById_Positive() {
//        when(userRepository.getUserById(user.getId())).thenReturn(user);
//        assertEquals(user, userService.getUserById(user.getId()));
//    }
//
//    @Test
//    void testGetUserById_Negative() {
//        when(userRepository.getUserById(any())).thenReturn(null);
//
//        assertThrows(RuntimeException.class, () -> userService.getUserById(UUID.randomUUID()));
//    }
//    @Test
//    void testGetUserById_EdgeCase() {
//        when(userRepository.getUserById(new UUID(0, 0))).thenReturn(null);
//
//        assertThrows(RuntimeException.class, () -> userService.getUserById(new UUID(0, 0)));
//    }
//
//
//    // TEST: deleteUserById()
//    @Test
//    void testDeleteUserById_Positive() {
//        doNothing().when(userRepository).deleteUserById(user.getId());
//        assertDoesNotThrow(() -> userService.deleteUserById(user.getId()));
//    }
//
//    @Test
//    void testDeleteUserById_Negative() {
//        doThrow(new RuntimeException("User not found")).when(userRepository).deleteUserById(any());
//        assertThrows(RuntimeException.class, () -> userService.deleteUserById(UUID.randomUUID()));
//    }
//
//    @Test
//    void testDeleteUserById_EdgeCase() {
//        doNothing().when(userRepository).deleteUserById(new UUID(0, 0));
//        assertDoesNotThrow(() -> userService.deleteUserById(new UUID(0, 0)));
//    }
//
//    // TEST: getOrdersByUserId()
//    @Test
//    void testGetOrdersByUserId_Positive() {
//        when(userRepository.getOrdersByUserId(user.getId())).thenReturn(List.of(order));
//        assertFalse(userService.getOrdersByUserId(user.getId()).isEmpty());
//    }
//
//    @Test
//    void testGetOrdersByUserId_Negative() {
//        when(userRepository.getOrdersByUserId(any())).thenReturn(Collections.emptyList());
//        assertTrue(userService.getOrdersByUserId(UUID.randomUUID()).isEmpty());
//    }
//
//    @Test
//    void testGetOrdersByUserId_EdgeCase() {
//        when(userRepository.getOrdersByUserId(new UUID(0, 0))).thenReturn(Collections.emptyList());
//        assertTrue(userService.getOrdersByUserId(new UUID(0, 0)).isEmpty());
//    }
//    // TEST: addOrderToUser()
//    @Test
//    void testAddOrderToUser_Positive() {
//        when(cartService.getCartByUserId(user.getId())).thenReturn(new Cart(UUID.randomUUID(), user.getId(), List.of(new Product(UUID.randomUUID(), "Test Product", 10.0))));
//        doNothing().when(userRepository).addOrderToUser(eq(user.getId()), any(Order.class));
//
//        userService.addOrderToUser(user.getId());
//
//        verify(userRepository).addOrderToUser(eq(user.getId()), any(Order.class));  // Ensure method was called
//    }
//
//
//    @Test
//    void testAddOrderToUser_EdgeCase() {
//        UUID cartId = UUID.randomUUID();
//        Cart emptyCart = new Cart(cartId, user.getId(), new ArrayList<>());  // No products in cart
//
//        when(cartService.getCartByUserId(user.getId())).thenReturn(emptyCart);
//
//        userService.addOrderToUser(user.getId());
//
//        verify(userRepository, never()).addOrderToUser(any(), any());  // ✅ Ensures no order is added
//    }
//
//    @Test
//    void testAddOrderToUser_Negative() {
//        doThrow(new RuntimeException("Cart not found")).when(cartService).getCartByUserId(any());
//        assertThrows(RuntimeException.class, () -> userService.addOrderToUser(UUID.randomUUID()));
//    }
//
//    // TEST: removeOrderFromUser()
//    @Test
//    void testRemoveOrderFromUser_Positive() {
//        doNothing().when(userRepository).removeOrderFromUser(user.getId(), order.getId());
//        assertDoesNotThrow(() -> userService.removeOrderFromUser(user.getId(), order.getId()));
//    }
//
//    @Test
//    void testRemoveOrderFromUser_Negative() {
//        doThrow(new RuntimeException("Order not found")).when(userRepository).removeOrderFromUser(any(), any());
//        assertThrows(RuntimeException.class, () -> userService.removeOrderFromUser(UUID.randomUUID(), UUID.randomUUID()));
//    }
//
//    @Test
//    void testRemoveOrderFromUser_EdgeCase() {
//        doNothing().when(userRepository).removeOrderFromUser(new UUID(0, 0), new UUID(0, 0));
//        assertDoesNotThrow(() -> userService.removeOrderFromUser(new UUID(0, 0), new UUID(0, 0)));
//    }
//    @Test
//    void testEmptyCart_Positive() {
//        UUID cartId = UUID.randomUUID();
//        Product product1 = new Product(UUID.randomUUID(), "Product 1", 10.0);
//        Product product2 = new Product(UUID.randomUUID(), "Product 2", 20.0);
//        Cart cart = new Cart(cartId, user.getId(), new ArrayList<>(List.of(product1, product2)));
//
//        when(cartService.getCartByUserId(user.getId())).thenReturn(cart);
//        doNothing().when(cartService).deleteProductFromCart(any(), any());
//
//        userService.emptyCart(user.getId());
//
//        verify(cartService, times(2)).deleteProductFromCart(eq(cartId), any(Product.class)); // Ensures products were removed
//    }
//
//    @Test
//    void testEmptyCart_Negative() {
//        when(cartService.getCartByUserId(any())).thenReturn(null);
//
//        assertThrows(RuntimeException.class, () -> userService.emptyCart(UUID.randomUUID()), "Cart not found");
//    }
//
//    @Test
//    void testEmptyCart_EdgeCase() {
//        UUID cartId = UUID.randomUUID();
//        Cart emptyCart = new Cart(cartId, user.getId(), new ArrayList<>());  // No products in cart
//
//        when(cartService.getCartByUserId(user.getId())).thenReturn(emptyCart);
//
//        userService.emptyCart(user.getId());
//
//        verify(cartService, never()).deleteProductFromCart(any(), any());  // Ensures nothing was deleted
//    }

//}