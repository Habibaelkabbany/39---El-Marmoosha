package com.example.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.repository.CartRepository;

@Service
@SuppressWarnings("rawtypes")
public class CartService extends MainService<Cart> {

    private final CartRepository repository;

    public CartService() {
        this.repository = new CartRepository();
    }

    public Cart addCart(Cart cart) {
        return repository.addCart(cart);
    }

    public ArrayList<Cart> getCarts() {
        return repository.getCarts();
    }

    public Cart getCartById(UUID cartId) {
        return repository.getCartById(cartId);
    }
    public Cart getCartByUserId(UUID userId) {
        return repository.getCartByUserId(userId);
    }

    public void addProductToCart(UUID cartId, Product product) {
        repository.addProductToCart(cartId, product);
    }

    public void deleteProductFromCart(UUID cartId, Product product) {
        repository.deleteProductFromCart(cartId, product);
    }

    public void deleteCart(UUID cartId) {
        repository.deleteCartById(cartId);
    }
}
