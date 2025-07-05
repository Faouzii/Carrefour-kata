package com.carrefour.kata.discountmvp.infrastructure.web;

import com.carrefour.kata.discountmvp.application.CartService;
import com.carrefour.kata.discountmvp.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
@ContextConfiguration(classes = CartController.class)
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private CartService cartService;
    
    private final Product testProduct = new Product("1", "Test Product", BigDecimal.valueOf(10.00), "Test description");
    private final Cart testCart = new Cart("default-cart", List.of(new CartItem(testProduct, 2)), null);
    private final CartDto testCartDto = new CartDto("default-cart", List.of(), null, BigDecimal.valueOf(20.00), BigDecimal.ZERO, BigDecimal.valueOf(20.00));

    @Test
    void getCart_shouldReturnCart() throws Exception {
        when(cartService.getCart("default-cart")).thenReturn(testCart);
        
        mockMvc.perform(get("/api/v1/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("default-cart"));
    }

    @Test
    void addItem_shouldReturnUpdatedCart() throws Exception {
        when(cartService.addItem(anyString(), anyString(), any(Integer.class))).thenReturn(testCart);
        
        mockMvc.perform(post("/api/v1/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":\"1\",\"quantity\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("default-cart"));
    }

    @Test
    void applyDiscount_shouldReturnUpdatedCart() throws Exception {
        when(cartService.applyDiscountCode(anyString(), anyString())).thenReturn(testCart);
        
        mockMvc.perform(post("/api/v1/cart/discount")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"discountCode\":\"PERC10\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateItem_shouldReturnUpdatedCart() throws Exception {
        when(cartService.updateItem(anyString(), anyString(), any(Integer.class))).thenReturn(testCart);
        
        mockMvc.perform(put("/api/v1/cart/items/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"quantity\":3}"))
                .andExpect(status().isOk());
    }

    @Test
    void removeItem_shouldReturnUpdatedCart() throws Exception {
        when(cartService.removeItem(anyString(), anyString())).thenReturn(testCart);
        
        mockMvc.perform(delete("/api/v1/cart/items/1"))
                .andExpect(status().isOk());
    }

    @Test
    void clearCart_shouldReturnEmptyCart() throws Exception {
        when(cartService.clearCart(anyString())).thenReturn(testCart);
        
        mockMvc.perform(delete("/api/v1/cart"))
                .andExpect(status().isOk());
    }
} 