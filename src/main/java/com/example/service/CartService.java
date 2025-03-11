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
            throw new RuntimeException("Cart cannot be null");
        }
       try {
           return repository.addCart(cart);
       } catch (Exception e) {
           throw new RuntimeException("Cannot add cart");
       }

    }

    public ArrayList<Cart> getCarts() {
        ArrayList<Cart> carts = repository.getCarts();
        if (carts == null) {
            return new ArrayList<>();
        }
        return carts;
    }

    public Cart getCartById(UUID cartId) {
        if (cartId == null) {
            throw new RuntimeException("Cart ID cannot be null");
        }
        Cart cart = repository.getCartById(cartId);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        return cart;

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
