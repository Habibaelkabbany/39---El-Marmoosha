package com.example.MiniProject1;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.controller.UserController;
import com.example.model.User;
import com.example.service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired private MockMvc mockMvc;

    @Mock private UserService userService;
    @InjectMocks private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("John Doe", UUID.randomUUID(), new ArrayList<>());
    }

    // TEST: addUser()
    @Test void testAddUser_Positive() throws Exception {
        mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\"}"))
                .andExpect(status().isOk());
    }

    @Test void testGetUsers_Positive() throws Exception {
        mockMvc.perform(get("/user/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }



}
