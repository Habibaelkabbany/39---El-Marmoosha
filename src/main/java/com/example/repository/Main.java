package com.example.repository;



import com.example.model.Order;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
//        @Autowired
        UserRepository userRepository = new UserRepository();

        // Create a new user
        User user = new User();
        user.setId(new UUID(1,1));
        user.setName("John Doe");

        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserId(UUID.fromString("00000000-0000-0001-0000-000000000001"));
        order.setProducts(new ArrayList<>());
        order.setTotalPrice(160.0);

//        userRepository.addUser(user);
//        userRepository.addOrderToUser(UUID.fromString("00000000-0000-0001-0000-000000000001"), order);
        // Save the user
//        System.out.println(userRepository.getUserById(UUID.fromString("00000000-0000-0001-0000-000000000001")));
//        System.out.println(userRepository.getOrdersByUserId(UUID.fromString("00000000-0000-0001-0000-000000000001")));
//            userRepository.removeOrderFromUser(UUID.fromString("00000000-0000-0001-0000-000000000001"), UUID.fromString("d0bb4396-1d97-4fc1-b8de-ba2cd06ca1da"));
//        userRepository.deleteUserById(UUID.fromString("00000000-0000-0001-0000-000000000001"));
        // Retrieve all users
        ArrayList<User> users = userRepository.getUsers();
        System.out.println("Users: " + users);
    }
}
