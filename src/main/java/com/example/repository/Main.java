package com.example.repository;



import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
//        @Autowired
        UserRepository userRepository = new UserRepository();

//        // Create a new user
//        User user = new User();
//        user.setId(new UUID(1,1));
//        user.setName("John Doe");
//
//        // Save the user
//        userRepository.save(user);

        // Retrieve all users
        ArrayList<User> users = userRepository.getUsers();
        System.out.println("Users: " + users);
    }
}
