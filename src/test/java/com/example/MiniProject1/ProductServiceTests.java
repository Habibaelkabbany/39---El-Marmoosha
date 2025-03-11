package com.example.MiniProject1;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Add Product Tests
    @Test
    void addProduct_ValidProduct_Success() {
        Product product = new Product("Test Product", 100.0);
        when(productRepository.addProduct(any(Product.class))).thenReturn(product);

        Product result = productService.addProduct(product);

        assertNotNull(result);
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getPrice(), result.getPrice());
    }

    @Test
    void addProduct_NullProduct_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.addProduct(null);
        });
    }

    @Test
    void addProduct_ZeroPrice_Success() {
        Product product = new Product("Free Product", 0.0);
        when(productRepository.addProduct(any(Product.class))).thenReturn(product);

        Product result = productService.addProduct(product);

        assertEquals(0.0, result.getPrice());
    }

    // Get Products Tests
    @Test
    void getProducts_ExistingProducts_ReturnsProductList() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Product 1", 10.0));
        products.add(new Product("Product 2", 20.0));
        when(productRepository.getProducts()).thenReturn(products);

        ArrayList<Product> result = productService.getProducts();

        assertEquals(2, result.size());
    }

    @Test
    void getProducts_EmptyRepository_ReturnsEmptyList() {
        when(productRepository.getProducts()).thenReturn(new ArrayList<>());

        ArrayList<Product> result = productService.getProducts();

        assertTrue(result.isEmpty());
    }

    @Test
    void getProducts_NullFromRepository_ReturnsEmptyList() {
        when(productRepository.getProducts()).thenReturn(null);

        ArrayList<Product> result = productService.getProducts();

        assertNull(result);
    }

    // Get Product By Id Tests
    @Test
    void getProductById_ExistingId_ReturnsProduct() {
        UUID id = UUID.randomUUID();
        Product product = new Product("Test Product", 100.0);
        when(productRepository.getProductById(id)).thenReturn(product);

        Product result = productService.getProductById(id);

        assertNotNull(result);
    }

    @Test
    void getProductById_NonExistingId_ReturnsNull() {
        UUID id = UUID.randomUUID();
        when(productRepository.getProductById(id)).thenReturn(null);

        Product result = productService.getProductById(id);

        assertNull(result);
    }

    @Test
    void getProductById_NullId_ReturnsNull() {
        Product result = productService.getProductById(null);

        assertNull(result);
    }

    // Update Product Tests
    @Test
    void updateProduct_ValidUpdate_Success() {
        UUID id = UUID.randomUUID();
        Product updatedProduct = new Product("Updated Product", 150.0);
        when(productRepository.updateProduct(id, "Updated Product", 150.0)).thenReturn(updatedProduct);

        Product result = productService.updateProduct(id, "Updated Product", 150.0);

        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
    }

    @Test
    void updateProduct_NonExistingId_ReturnsNull() {
        UUID id = UUID.randomUUID();
        when(productRepository.updateProduct(id, "New Name", 100.0)).thenReturn(null);

        Product result = productService.updateProduct(id, "New Name", 100.0);

        assertNull(result);
    }

    @Test
    void updateProduct_NegativePrice_ThrowsException() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(id, "Test", -10.0);
        });
    }

    // Apply Discount Tests
    @Test
    void applyDiscount_ValidDiscount_Success() {
        ArrayList<UUID> ids = new ArrayList<>();
        ids.add(UUID.randomUUID());

        productService.applyDiscount(10.0, ids);

        verify(productRepository).applyDiscount(10.0, ids);
    }

    @Test
    void applyDiscount_EmptyProductList_NoAction() {
        ArrayList<UUID> ids = new ArrayList<>();

        productService.applyDiscount(10.0, ids);

        verify(productRepository).applyDiscount(10.0, ids);
    }

    @Test
    void applyDiscount_InvalidDiscount_ThrowsException() {
        ArrayList<UUID> ids = new ArrayList<>();
        ids.add(UUID.randomUUID());

        assertThrows(IllegalArgumentException.class, () -> {
            productService.applyDiscount(101.0, ids);
        });
    }

    // Delete Product Tests
    @Test
    void deleteProductById_ExistingId_Success() {
        UUID id = UUID.randomUUID();

        productService.deleteProductById(id);

        verify(productRepository).deleteProduct(id);
    }

    @Test
    void deleteProductById_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.deleteProductById(null);
        });
    }

    @Test
    void deleteProductById_NonExistingId_NoException() {
        UUID id = UUID.randomUUID();
        doNothing().when(productRepository).deleteProduct(id);

        productService.deleteProductById(id);

        verify(productRepository).deleteProduct(id);
    }
}