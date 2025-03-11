package com.example.MiniProject1;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.repository.ProductRepository;
import com.example.service.CartService;
import com.example.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartServiceTests {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService  cartService;

    // @InjectMocks
    // private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Add Cart Tests

    @Test
    void addCart_ValidCart_Success() {
        User user1 = new User("Test User1");
        Cart cart = new Cart(user1.getId(), new ArrayList<>());
        when(cartRepository.addCart(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.addCart(cart);

        assertNotNull(result);
        assertEquals(cart.getId(), result.getId());
        assertEquals(cart.getUserId(), result.getUserId());
    }

    @Test
    void addCart_ValidCart1_Success() {
        User user1 = new User("Test User1");
        Cart cart = new Cart(user1.getId(), new ArrayList<>());
        when(cartRepository.addCart(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.addCart(cart);

        assertNotNull(result);
        assertEquals(cart.getId(), result.getId());
        assertEquals(cart.getUserId(), result.getUserId());
    }

    @Test
    void addCart_NullCart_ThrowsException() {
        assertThrows(   RuntimeException.class, () -> {
            cartService.addCart(null);
        });
    }

	@Test
	void getCarts_ExistingCarts_ReturnsCartList() {
		Cart cart = new Cart(UUID.randomUUID(), new ArrayList<>());
        cartService.addCart(cart);

		ArrayList<Cart> result = cartService.getCarts();
        Cart resultCart = null;
        for (Cart c : result) {
            if (c == null) continue;
            if (c.getId().equals(cart.getId())) {
                resultCart = c;
            }   
        }

		assertNotNull(resultCart);
	}

	@Test
	void getCarts_EmptyRepository_ReturnsEmptyList() {
        cartService.getRepository().overrideData(new ArrayList<>());

		ArrayList<Cart> result = cartService.getCarts();
		assertTrue(result.isEmpty());
	}

	@Test
	void getCarts_NullFromRepository_ReturnsEmptyList() {
        cartService.getRepository().overrideData(null);
		when(cartRepository.getCarts()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> cartService.getCarts());

	}

	// Tests for cartService.getCartById()
	@Test
	void getCartById_ExistingId_ReturnsCart() {
		UUID id = UUID.randomUUID();
		UUID userId = UUID.randomUUID();
		Cart cart = new Cart(id, userId, new ArrayList<>());
        cartService.addCart(cart);

		Cart result = cartService.getCartById(id);

		assertEquals(cart.getId(), result.getId());
	}

	@Test
	void getCartById_NonExistingId_ReturnsEmpty() {
		UUID id = UUID.randomUUID();


        assertThrows(RuntimeException.class, () -> cartService.getCartById(id));
	}

	@Test
	void getCartById_NullId_ThrowsException() {
		assertThrows(RuntimeException.class, () -> {
			cartService.getCartById(null);
		});
	}

	// Tests for cartService.getCartByUserId()
	@Test
	void getCartByUserId_ExistingId_ReturnsCart() {
		UUID id = UUID.randomUUID();
		Cart cart = new Cart(id, new ArrayList<>());
        cartService.addCart(cart);

		Cart result = cartService.getCartByUserId(id);

		assertEquals(cart.getUserId(), result.getUserId());
	}

	@Test
	void getCartByUserId_NonExistingId_ReturnsEmpty() {
		UUID id = UUID.randomUUID();

		Cart result = cartService.getCartByUserId(id);

		assertNull(result);
	}

	@Test
	void getCartByUserId_NullId_ThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> {
			cartService.getCartByUserId(null);
		});
	}

	// Tests for cartService.addProductToCart()
	@Test
	void addProductToCart_ValidProduct_Success() {
		UUID userId = UUID.randomUUID();
		Product product = new Product("Test Product", 100.0);
		Cart cart = new Cart(userId, new ArrayList<>());

        cartService.addCart(cart);
		cartService.addProductToCart(cart.getId(), product);
        Cart result = cartService.getCartById(cart.getId());
        Product productResult = null;
        for (Product prod : result.getProducts()) {
            if (prod.getId().equals(product.getId())) {
                productResult = prod;
            }
        }
        assertNotNull(productResult, "Product not found in cart");
	}

	@Test
	void addProductToCart_NonExistingCart_ThrowsException() {
		UUID cartId = UUID.randomUUID();
		Product product = new Product("Test Product", 100.0);

		assertThrows(IllegalArgumentException.class, () -> {
			cartService.addProductToCart(cartId, product);
		});
	}

	@Test
	void addProductToCart_NullProduct_ThrowsException() {
		UUID cartId = UUID.randomUUID();
		assertThrows(IllegalArgumentException.class, () -> {
			cartService.addProductToCart(cartId, null);
		});
	}

    // Delete Product Tests

    @Test
    void deleteProductFromCart_ValidProduct_Success() {
        UUID userId = UUID.randomUUID();
        Product product = new Product("Test Product1", 120.0);
        Cart cart = new Cart(userId, new ArrayList<>());
        cartService.addCart(cart);
        cartService.addProductToCart(cart.getId(), product);
        // ArrayList<Cart> carts = new ArrayList<>();
        // carts.add(cart);
        // cartRepository.overrideData(carts);
        cartService.getCartById(cart.getId());
        cartService.deleteProductFromCart(cart.getId(), product);
        Cart result = cartService.getCartById(cart.getId());
        Product productResult = null;
        for (Product prod : result.getProducts()) {
            if (prod.getId().equals(product.getId())) {
                productResult = prod;
            }
        }
        assertNull(productResult, "Product not removed from cart");
    }

    @Test
    void deleteProductFromCart_NonExistingCart_ThrowsException() {
        UUID cartId = UUID.randomUUID();
        Product product = new Product("Test Product", 100.0);

        assertThrows(RuntimeException.class, () -> {
            cartService.deleteProductFromCart(cartId, product);
        });
    }

    @Test
    void deleteProductFromCart_NullProduct_ThrowsException() {
        UUID cartId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> {
            cartService.deleteProductFromCart(cartId, null);
        });
    }

    // Delete Cart Tests
    @Test
    void deleteCart_ExistingCart_Success() {
        UUID userId = UUID.randomUUID();
        UUID cartId = UUID.randomUUID();
        Cart cart = new Cart(cartId,userId, new ArrayList<>());
        cartService.addCart(cart);

        cartService.deleteCart(cartId);

        assertThrows(RuntimeException.class,() -> cartService.getCartById(cartId));
    }

    @Test
    void deleteCart_NonExistingCart_ThrowsException() {
        UUID id = UUID.randomUUID();

        assertThrows(RuntimeException.class, () -> {
            cartService.deleteCart(id);
        });
    }

    @Test
    void deleteCart_NullId_ThrowsException() {
        assertThrows(RuntimeException.class, () -> {
            cartService.deleteCart(null);
        });
    }


    // Get Products Tests
    // @Test
    // void getProducts_ExistingProducts_ReturnsProductList() {
    //     ArrayList<Product> products = new ArrayList<>();
    //     products.add(new Product("Product 1", 10.0));
    //     products.add(new Product("Product 2", 20.0));
    //     when(cartRepository.getProducts()).thenReturn(products);

    //     ArrayList<Product> result = cartService.getProducts();

    //     assertEquals(2, result.size());
    // }

    // @Test
    // void getProducts_EmptyRepository_ReturnsEmptyList() {
    //     when(cartRepository.getProducts()).thenReturn(new ArrayList<>());

    //     ArrayList<Product> result = cartService.getProducts();

    //     assertTrue(result.isEmpty());
    // }

    // @Test
    // void getProducts_NullFromRepository_ReturnsEmptyList() {
    //     when(cartRepository.getProducts()).thenReturn(null);

    //     ArrayList<Product> result = cartService.getProducts();

    //     assertNull(result);
    // }

    // // Get Product By Id Tests
    // @Test
    // void getProductById_ExistingId_ReturnsProduct() {
    //     UUID id = UUID.randomUUID();
    //     Product product = new Product("Test Product", 100.0);
    //     when(cartRepository.getProductById(id)).thenReturn(product);

    //     Product result = cartService.getProductById(id);

    //     assertNotNull(result);
    // }

    // @Test
    // void getProductById_NonExistingId_ReturnsNull() {
    //     UUID id = UUID.randomUUID();
    //     when(cartRepository.getProductById(id)).thenReturn(null);

    //     Product result = cartService.getProductById(id);

    //     assertNull(result);
    // }

    // @Test
    // void getProductById_NullId_ReturnsNull() {
    //     Product result = cartService.getProductById(null);

    //     assertNull(result);
    // }

    // // Update Product Tests
    // @Test
    // void updateProduct_ValidUpdate_Success() {
    //     UUID id = UUID.randomUUID();
    //     Product updatedProduct = new Product("Updated Product", 150.0);
    //     when(cartRepository.updateProduct(id, "Updated Product", 150.0)).thenReturn(updatedProduct);

    //     Product result = cartService.updateProduct(id, "Updated Product", 150.0);

    //     assertNotNull(result);
    //     assertEquals("Updated Product", result.getName());
    // }

    // @Test
    // void updateProduct_NonExistingId_ReturnsNull() {
    //     UUID id = UUID.randomUUID();
    //     when(cartRepository.updateProduct(id, "New Name", 100.0)).thenReturn(null);

    //     Product result = cartService.updateProduct(id, "New Name", 100.0);

    //     assertNull(result);
    // }

    // @Test
    // void updateProduct_NegativePrice_ThrowsException() {
    //     UUID id = UUID.randomUUID();
    //     assertThrows(IllegalArgumentException.class, () -> {
    //         cartService.updateProduct(id, "Test", -10.0);
    //     });
    // }

    // // Apply Discount Tests
    // @Test
    // void applyDiscount_ValidDiscount_Success() {
    //     ArrayList<UUID> ids = new ArrayList<>();
    //     ids.add(UUID.randomUUID());

    //     cartService.applyDiscount(10.0, ids);

    //     verify(cartRepository).applyDiscount(10.0, ids);
    // }

    // @Test
    // void applyDiscount_EmptyProductList_NoAction() {
    //     ArrayList<UUID> ids = new ArrayList<>();

    //     cartService.applyDiscount(10.0, ids);

    //     verify(cartRepository).applyDiscount(10.0, ids);
    // }

    // @Test
    // void applyDiscount_InvalidDiscount_ThrowsException() {
    //     ArrayList<UUID> ids = new ArrayList<>();
    //     ids.add(UUID.randomUUID());

    //     assertThrows(IllegalArgumentException.class, () -> {
    //         cartService.applyDiscount(101.0, ids);
    //     });
    // }

    // // Delete Product Tests
    // @Test
    // void deleteProductById_ExistingId_Success() {
    //     UUID id = UUID.randomUUID();

    //     cartService.deleteProductById(id);

    //     verify(cartRepository).deleteProduct(id);
    // }

    // @Test
    // void deleteProductById_NullId_ThrowsException() {
    //     assertThrows(IllegalArgumentException.class, () -> {
    //         cartService.deleteProductById(null);
    //     });
    // }

    // @Test
    // void deleteProductById_NonExistingId_NoException() {
    //     UUID id = UUID.randomUUID();
    //     doNothing().when(cartRepository).deleteProduct(id);

    //     cartService.deleteProductById(id);

    //     verify(cartRepository).deleteProduct(id);
    // }
}
    // @BeforeEach
    // void setUp() {
    //     user = new User("John Doe", UUID.randomUUID(), new ArrayList<>());
    //     order = new Order(UUID.randomUUID(), user.getId(), 100.00, new ArrayList<>());
    //     lenient().when(cartService.getCartByUserId(any())).thenReturn(new Cart(UUID.randomUUID(), user.getId(), new ArrayList<>()));
    // }

//    @Test
//    void testAddCart_Positive(){
//        Cart cart = new Cart(UUID.randomUUID(), user.getId(), new ArrayList<>());
//        when(cartService.addCart(any(Cart.class))).thenReturn(cart);
//        assertEquals(cart, cartService.addCart(cart));
//
//    }
//    @Test
//    void testAddCart_Negative(){
//        when(cartService.addCart(any(Cart.class))).thenThrow(new RuntimeException("Cannot add cart"));
//        assertThrows(RuntimeException.class, () -> cartService.addCart(new Cart(UUID.randomUUID(), user.getId(), new ArrayList<>())));
//    }
//    @Test
//    void testAddCart_Edge_NullCart(){
//        when(cartService.addCart(null)).thenThrow(new RuntimeException("Cart cannot be null"));
//        assertThrows(RuntimeException.class, () -> cartService.addCart(null));
//    }
//
//    @Test
//    void testGetAllCarts_Positive(){
//        List<Cart> mockCarts = List.of(new Cart(UUID.randomUUID(), user.getId(), new ArrayList<>()));
//        when(cartService.getCarts()).thenReturn(new ArrayList<>(List.of(new Cart(UUID.randomUUID(), user.getId(), new ArrayList<>()))));
//
//        List<Cart> result = cartService.getCarts();
//
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//        assertEquals(mockCarts, result);
//    }
//    @Test
//    void testGetAllCarts_Negative(){
//        when(cartService.getCarts()).thenReturn(new ArrayList<>());
//        List<Cart> result = cartService.getCarts();
//        assertTrue(result.isEmpty(),"Expected an empty list, but it was not.");
//    }
//    @Test
//    void testGetAllCarts_Edge_NullCart(){ // no carts at all
//        when(cartService.getCarts()).thenReturn(null);
//        List<Cart> result = cartService.getCarts();
//        assertNull(result, "Expected null, but it was not.");
//
//    }




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
