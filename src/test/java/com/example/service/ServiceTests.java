package com.example.service;

import com.example.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@AutoConfigureMockMvc
public class ServiceTests {
    @Autowired
    private MockMvc mockMvc;

//    @Autowired
    private UserService userService;




    @Test
    void contextLoads() {
    }

    // User Service Tests
    @Test
    void testAddUser (){
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Habiba");
        User result = userService.addUser(user);
        assertNotNull(result, "User should not be null");
        assertEquals(user.getName(), result.getName(), "User name should be equal");
        assertEquals(user.getId(), result.getId(), "User id should be equal");
    }


}
