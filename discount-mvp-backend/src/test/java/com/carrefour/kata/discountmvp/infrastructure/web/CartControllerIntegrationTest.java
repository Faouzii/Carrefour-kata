package com.carrefour.kata.discountmvp.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addItemAndGetCart() throws Exception {
        mockMvc.perform(delete("/api/v1/cart"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/v1/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":\"1\",\"quantity\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].productId").value("1"))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
        mockMvc.perform(get("/api/v1/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].productId").value("1"));
    }

    @Test
    void applyDiscountAndGetCart() throws Exception {
        mockMvc.perform(delete("/api/v1/cart"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/v1/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":\"1\",\"quantity\":2}"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/v1/cart/discount")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"discountCode\":\"PERC10\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appliedDiscountCode").value("PERC10"));
        mockMvc.perform(get("/api/v1/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appliedDiscountCode").value("PERC10"));
    }

    @Test
    void applyInvalidDiscountShouldReturnNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/cart"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/v1/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":\"1\",\"quantity\":2}"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/v1/cart/discount")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"discountCode\":\"INVALID\"}"))
                .andExpect(status().isNotFound());
    }
} 