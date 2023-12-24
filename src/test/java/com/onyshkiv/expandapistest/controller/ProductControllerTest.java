package com.onyshkiv.expandapistest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onyshkiv.expandapistest.entity.Product;
import com.onyshkiv.expandapistest.entity.ProductJsonPayload;
import com.onyshkiv.expandapistest.service.ProductService;
import com.onyshkiv.expandapistest.service.UserService;
import com.onyshkiv.expandapistest.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private UserService userService;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void createTableAndAddProductsTest() throws Exception {
        ProductJsonPayload payload = new ProductJsonPayload();
        String jsonPayload = objectMapper.writeValueAsString(payload);

        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(productService, times(1)).save(any());
        verify(productService, times(1)).createTable(any());
    }

    @Test
    public void getAllProductsTest() throws Exception {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Product product = new Product(1L, LocalDate.now(), "111", "item", 1, "Paid");
        when(productService.getAllProducts()).thenReturn(List.of(product));
        mockMvc.perform(get("/products/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].entryDate").value(LocalDate.now().format(pattern)))
                .andExpect(jsonPath("$[0].itemCode").value("111"))
                .andExpect(jsonPath("$[0].itemName").value("item"))
                .andExpect(jsonPath("$[0].itemQuantity").value(1))
                .andExpect(jsonPath("$[0].status").value("Paid"));
        verify(productService, times(1)).getAllProducts();
    }

}
