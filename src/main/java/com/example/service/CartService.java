package com.example.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.repository.CartRepository;

@Service
@SuppressWarnings("rawtypes")
public class CartService extends MainService<Cart> {

    private final CartRepository repository;

    @Autowired
    public CartService() {
        this.repository = new CartRepository();
    }

    public Cart addCart(Cart cart) {
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }
        return repository.addCart(cart);
    }

    public ArrayList<Cart> getCarts() {
        return repository.getCarts();
    }

    public Cart getCartById(UUID cartId) {
        if (cartId == null) {
            throw new IllegalArgumentException("Cart ID cannot be null");
        }
        return repository.getCartById(cartId);
    }
    public Cart getCartByUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Cart ID cannot be null");
        }
        return repository.getCartByUserId(userId);
    }

    public void addProductToCart(UUID cartId, Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        repository.addProductToCart(cartId, product);
    }

    public void deleteProductFromCart(UUID cartId, Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (getCartById(cartId) == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        repository.deleteProductFromCart(cartId, product);
    }

    public void deleteCart(UUID cartId) {
        if (getCartById(cartId) == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        repository.deleteCartById(cartId);
    }
}
