//package com.example.MiniProject1;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.example.model.Cart;
//import com.example.model.Product;
//import com.example.repository.CartRepository;
//
//import com.example.service.CartService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.UUID;
//
//@ExtendWith(MockitoExtension.class)
//class CartServiceTest {
//
//    @Mock
//    private CartRepository cartRepository;
//
//    @InjectMocks
//    private CartService cartService;
//
//    private Cart cart;
//    private UUID cartId;
//    private UUID userId;
//    private Product product;
//
//    @BeforeEach
//    void setUp() {
//        cartId = UUID.randomUUID();
//        userId = UUID.randomUUID();
//        product = new Product(UUID.randomUUID(), "Test Product", 10.0);
//        cart = new Cart(cartId, userId, new ArrayList<>());
//    }
//
//    // TEST: addCart()
//    @Test
//    void testAddCart_Positive() {
//        when(cartRepository.addCart(any(Cart.class))).thenReturn(cart);
//        assertEquals(cart, cartService.addCart(cart));
//    }
//
//    @Test
//    void testAddCart_Negative() {
//        when(cartRepository.addCart(any(Cart.class))).thenThrow(new RuntimeException("Database Error"));
//        assertThrows(RuntimeException.class, () -> cartService.addCart(cart));
//    }
//
//    @Test
//    void testAddCart_EdgeCase() {
//        Cart emptyCart = new Cart(null, null, new ArrayList<>());
//        when(cartRepository.addCart(emptyCart)).thenReturn(emptyCart);
//        assertNotNull(cartService.addCart(emptyCart));
//    }
//
//    // TEST: getCarts()
//    @Test
//    void testGetCarts_Positive() {
//        when(cartRepository.getCarts()).thenReturn(new ArrayList<>(Collections.singletonList(cart)));
//        assertFalse(cartService.getCarts().isEmpty());
//    }
//
//    @Test
//    void testGetCarts_Negative() {
//        when(cartRepository.getCarts()).thenReturn(new ArrayList<>());
//        assertTrue(cartService.getCarts().isEmpty());
//    }
//
//    @Test
//    void testGetCarts_EdgeCase() {
//        when(cartRepository.getCarts()).thenReturn(new ArrayList<>());
//        assertEquals(0, cartService.getCarts().size());
//    }
//
//    // TEST: getCartById()
//    @Test
//    void testGetCartById_Positive() {
//        when(cartRepository.getCartById(cartId)).thenReturn(cart);
//        assertEquals(cart, cartService.getCartById(cartId));
//    }
//
//    @Test
//    void testGetCartById_Negative() {
//        when(cartRepository.getCartById(any())).thenReturn(null);
//        assertNull(cartService.getCartById(UUID.randomUUID()));
//    }
//
//    @Test
//    void testGetCartById_EdgeCase() {
//        when(cartRepository.getCartById(new UUID(0, 0))).thenReturn(null);
//        assertNull(cartService.getCartById(new UUID(0, 0)));
//    }
//
//    // TEST: getCartByUserId()
//    @Test
//    void testGetCartByUserId_Positive() {
//        when(cartRepository.getCartByUserId(userId)).thenReturn(cart);
//        assertEquals(cart, cartService.getCartByUserId(userId));
//    }
//
//    @Test
//    void testGetCartByUserId_Negative() {
//        when(cartRepository.getCartByUserId(any())).thenReturn(null);
//        assertNull(cartService.getCartByUserId(UUID.randomUUID()));
//    }
//
//    @Test
//    void testGetCartByUserId_EdgeCase() {
//        when(cartRepository.getCartByUserId(new UUID(0, 0))).thenReturn(null);
//        assertNull(cartService.getCartByUserId(new UUID(0, 0)));
//    }
//
//    // TEST: addProductToCart()
//    @Test
//    void testAddProductToCart_Positive() {
//        doNothing().when(cartRepository).addProductToCart(cartId, product);
//        assertDoesNotThrow(() -> cartService.addProductToCart(cartId, product));
//    }
//
//    @Test
//    void testAddProductToCart_Negative() {
//        doThrow(new RuntimeException("Product not found")).when(cartRepository).addProductToCart(any(), any());
//        assertThrows(RuntimeException.class, () -> cartService.addProductToCart(UUID.randomUUID(), product));
//    }
//
//    @Test
//    void testAddProductToCart_EdgeCase() {
//        doNothing().when(cartRepository).addProductToCart(new UUID(0, 0), new Product(new UUID(0, 0), "", 0.0));
//        assertDoesNotThrow(() -> cartService.addProductToCart(new UUID(0, 0), new Product(new UUID(0, 0), "", 0.0)));
//    }
//
//    // TEST: deleteProductFromCart()
//    @Test
//    void testDeleteProductFromCart_Positive() {
//        doNothing().when(cartRepository).deleteProductFromCart(cartId, product);
//        assertDoesNotThrow(() -> cartService.deleteProductFromCart(cartId, product));
//    }
//
//    @Test
//    void testDeleteProductFromCart_Negative() {
//        doThrow(new RuntimeException("Product not found")).when(cartRepository).deleteProductFromCart(any(), any());
//        assertThrows(RuntimeException.class, () -> cartService.deleteProductFromCart(UUID.randomUUID(), product));
//    }
//
//    @Test
//    void testDeleteProductFromCart_EdgeCase() {
//        doNothing().when(cartRepository).deleteProductFromCart(new UUID(0, 0), new Product(new UUID(0, 0), "", 0.0));
//        assertDoesNotThrow(() -> cartService.deleteProductFromCart(new UUID(0, 0), new Product(new UUID(0, 0), "", 0.0)));
//    }
//
//    // TEST: deleteCart()
//    @Test
//    void testDeleteCart_Positive() {
//        doNothing().when(cartRepository).deleteCartById(cartId);
//        assertDoesNotThrow(() -> cartService.deleteCart(cartId));
//    }
//
//    @Test
//    void testDeleteCart_Negative() {
//        doThrow(new RuntimeException("Cart not found")).when(cartRepository).deleteCartById(any());
//        assertThrows(RuntimeException.class, () -> cartService.deleteCart(UUID.randomUUID()));
//    }
//
//    @Test
//    void testDeleteCart_EdgeCase() {
//        doNothing().when(cartRepository).deleteCartById(new UUID(0, 0));
//        assertDoesNotThrow(() -> cartService.deleteCart(new UUID(0, 0)));
//    }
//}
