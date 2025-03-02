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

import com.example.models.Cart;
import com.example.models.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
@SuppressWarnings("rawtypes")
public class CartRepository extends MainRepository<Cart> {

    public Cart addCart(Cart cart) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new ClassPathResource(getDataPath()).getFile();
            ArrayList<Cart> carts = objectMapper.readValue(file, new TypeReference<ArrayList<Cart>>() {});

            carts.add(cart);

            objectMapper.writeValue(file, carts);
            return cart;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to write to carts.json");
        }
    }

    public ArrayList<Cart> getCarts() {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            File file = new ClassPathResource(getDataPath()).getFile();
            ArrayList<Cart> carts = objectMapper.readValue(file, new TypeReference<ArrayList<Cart>>() {
            });

            return carts;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to read carts.json");
        }
    }

    public Cart getCartById(UUID cartId) {
        return getCarts().stream().filter(cart -> cart.getId().equals(cartId)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
    }

    public Cart getCartByUserId(UUID userId) {
        return getCarts().stream().filter(cart -> cart.getUserId().equals(userId)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
    }

    public void addProductToCart(UUID cartId, Product product) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new ClassPathResource(getDataPath()).getFile();
            ArrayList<Cart> carts = objectMapper.readValue(file, new TypeReference<ArrayList<Cart>>() {});

            carts.stream()
                .filter(cart -> cart.getId().equals(cartId))
                .findFirst()
                .ifPresent(cart -> cart.getProducts().add(product));

            objectMapper.writeValue(file, carts);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to write to carts.json");
        }
    }

    public void deleteProductFromCart(UUID cartId, Product product) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new ClassPathResource(getDataPath()).getFile();
            ArrayList<Cart> carts = objectMapper.readValue(file, new TypeReference<ArrayList<Cart>>() {});

            carts.stream()
                .filter(cart -> cart.getId().equals(cartId))
                .findFirst()
                .ifPresent(cart -> cart.getProducts().removeIf(p -> p.getId().equals(product.getId())));

            objectMapper.writeValue(file, carts);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to write to carts.json");
        }
    }

    public void deleteCartById(UUID cartId) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new ClassPathResource(getDataPath()).getFile();
            ArrayList<Cart> carts = objectMapper.readValue(file, new TypeReference<ArrayList<Cart>>() {});

            carts.removeIf(cart -> cart.getId().equals(cartId));

            objectMapper.writeValue(file, carts);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to write to carts.json");
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
