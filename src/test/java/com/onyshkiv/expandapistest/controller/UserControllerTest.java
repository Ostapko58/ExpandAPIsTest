package com.onyshkiv.expandapistest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onyshkiv.expandapistest.entity.User;
import com.onyshkiv.expandapistest.service.UserService;
import com.onyshkiv.expandapistest.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtUtil jwtUtil;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void saveUserTest() throws Exception {
        User user = new User(0, "user", "password");
        String jsonUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).saveUser(any());
    }

    @Test
    public void authenticateUserTest() throws Exception {
        User user = new User(0, "user", "password");
        String jsonUser = objectMapper.writeValueAsString(user);
        when(userService.authenticate(any())).thenReturn("jwt Token");
        mockMvc.perform(post("/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("jwt Token"));

        verify(userService, times(1)).authenticate(any());
    }

}
