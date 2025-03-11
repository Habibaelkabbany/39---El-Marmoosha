package com.example.service;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.repository.ProductRepository;
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

    @InjectMocks
    private UserService userService;

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
        assertThrows(IllegalArgumentException.class, () -> {
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
        cartRepository.overrideData(new ArrayList<>());

		ArrayList<Cart> result = cartService.getCarts();
        System.out.println(result);
		assertTrue(result.isEmpty());
	}

	@Test
	void getCarts_NullFromRepository_ReturnsEmptyList() {
		when(cartRepository.getCarts()).thenReturn(null);

		ArrayList<Cart> result = cartService.getCarts();

		assertNull(result);
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

		Cart result = cartService.getCartById(id);

		assertNull(result);
	}

	@Test
	void getCartById_NullId_ThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> {
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

        assertThrows(IllegalArgumentException.class, () -> {
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

        Cart result = cartService.getCartById(cartId);
        assertNull(result, "Cart not deleted");
    }

    @Test
    void deleteCart_NonExistingCart_ThrowsException() {
        UUID id = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            cartService.deleteCart(id);
        });
    }

    @Test
    void deleteCart_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
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