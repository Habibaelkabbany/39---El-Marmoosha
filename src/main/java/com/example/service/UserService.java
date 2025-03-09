package com.example.service;

<<<<<<< Updated upstream
public class UserService {
=======
import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;

import com.example.repository.UserRepository;
import com.example.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class UserService extends MainService<User>{
    UserRepository userRepository;
    CartService cartService;
    @Autowired
    public UserService(UserRepository userRepository, CartService cartService) {
        this.userRepository = userRepository;
        this.cartService = cartService;
    }
    public User addUser(User user) {
        return userRepository.addUser(user);
    }
    public ArrayList<User> getUsers() {
        return userRepository.getUsers();
    }
    public User getUserById(UUID userId){
        User user = userRepository.getUserById(userId);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        else {
            return user;
        }
    }
    public List<Order> getOrdersByUserId(UUID userId){
//        return userRepository.getOrdersByUserId(userId);
        List<Order> orders = userRepository.getOrdersByUserId(userId);
        if(orders == null){
            throw new RuntimeException("User with Id " + userId+ " not found");
        }
        if (orders.isEmpty()) {
            System.out.println("User has no orders yet");
        }
        return orders;
    }
    public void deleteUserById(UUID userId){
        userRepository.deleteUserById(userId);
    }
    public void removeOrderFromUser(UUID userId, UUID orderId){
        userRepository.removeOrderFromUser(userId, orderId);
    }
    public void emptyCart(UUID userId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        UUID cartId = cart.getId();
        List<Product> products = cart.getProducts();
        if (products.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        for (Product product : products) {
            cartService.deleteProductFromCart(cartId, product);
        }
        System.out.println("Cart emptied");
    }
    public void addOrderToUser(UUID userId){
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        UUID cartId = cart.getId();
        List<Product> products = cart.getProducts();
        if (products.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getPrice();
        }
        emptyCart(userId);


        Order order = new Order(userId,totalPrice,products);
        userRepository.addOrderToUser(userId, order);
    }




>>>>>>> Stashed changes
}
