package com.example.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Cart;
import com.example.models.Product;
import com.example.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @PostMapping("/")
    public Cart addCart(@RequestBody Cart cart) {
        CartService cartService = new CartService();
        cartService.addCart(cart);
        return cart;
    }

    @GetMapping("/")
    public ArrayList<Cart> getCarts() {
        CartService cartService = new CartService();
        return cartService.getCarts();
    }

    @GetMapping("/{cartId}")
    public Cart getCartById(@PathVariable UUID cartId) {
        CartService cartService = new CartService();
        return cartService.getCartById(cartId);
    }

    @PutMapping("/addProduct/{cartId}")
    public String addProductToCart(@PathVariable UUID cartId, @RequestBody Product product) {
        CartService cartService = new CartService();
        cartService.addProductToCart(cartId, product);
        return "Product added to cart";
    }

    @DeleteMapping("/delete/{cartId}")
    public String deleteCartById(@PathVariable UUID cartId) {
        CartService cartService = new CartService();
        cartService.deleteCart(cartId);
        return "Cart deleted";
    }

}
