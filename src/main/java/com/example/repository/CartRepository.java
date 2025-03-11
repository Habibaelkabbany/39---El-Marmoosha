package com.example.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
@SuppressWarnings("rawtypes")
public class CartRepository extends MainRepository<Cart> {

    public Cart addCart(Cart cart) {
        try {
            save(cart);
            return cart;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart not added");
        }
    }

    public ArrayList<Cart> getCarts() {

        return findAll();
    }

    public Cart getCartById(UUID cartId) {
        ArrayList<Cart> carts = getCarts();
        for(Cart cart : carts){
            if(cart.getId().equals(cartId)) {
                return cart;
            }
        }
        return null;
    }

    public Cart getCartByUserId(UUID userId) {
        ArrayList<Cart> carts = getCarts();
        for(Cart cart : carts){
            if(cart.getUserId().equals(userId)) {
                return cart;
            }
        }
        return null;
    }

    public void addProductToCart(UUID cartId, Product product) {
        ArrayList<Cart> carts = getCarts();

        for(Cart cart : carts){
            if(cart.getId().equals(cartId)) {
                cart.getProducts().add(product);
                overrideData(carts);

                System.out.println("Product added");
                return;
            }
        }
        System.out.println("Product not added: Cart not found");
    }

    public void deleteProductFromCart(UUID cartId, Product product) {
        ArrayList<Cart> carts = getCarts();

        for(Cart cart : carts){
            if(cart.getId().equals(cartId)) {
                boolean exists = cart.getProducts().remove(product);
                overrideData(carts);
                if (exists) {
                    System.out.println("Product removed");
                    return;
                } else {
                    System.out.println("Product not removed: Product not found");
                    return;
                }
            }
        }
        System.out.println("Product not removed: Cart not found");
    }

    public void deleteCartById(UUID cartId) {
        ArrayList<Cart> carts = getCarts();

        boolean exists = carts.removeIf(cart -> cart.getId().equals(cartId));
        
        if (exists) {
            overrideData(carts);
            System.out.println("Cart removed");
        } else {
            System.out.println("Cart not removed: Cart not found");
        }
    }

    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/carts.json";
    }

    @Override
    protected Class<Cart[]> getArrayType() {
        return Cart[].class;
    }
}
