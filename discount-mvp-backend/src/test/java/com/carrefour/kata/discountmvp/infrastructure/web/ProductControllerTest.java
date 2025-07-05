package com.carrefour.kata.discountmvp.infrastructure.web;

import com.carrefour.kata.discountmvp.domain.Product;
import com.carrefour.kata.discountmvp.domain.port.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    private final Product testProduct1 = new Product("1", "Fresh Apples", BigDecimal.valueOf(2.50), "Fresh Carrefour product");
    private final Product testProduct2 = new Product("2", "Organic Bananas", BigDecimal.valueOf(1.80), "Organic Carrefour Bio product");

    @Test
    void getAllProducts_shouldReturnProductsList() throws Exception {
        when(productRepository.findAll()).thenReturn(List.of(testProduct1, testProduct2));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Fresh Apples"))
                .andExpect(jsonPath("$[0].price").value(2.50))
                .andExpect(jsonPath("$[0].description").value("Fresh Carrefour product"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Organic Bananas"))
                .andExpect(jsonPath("$[1].price").value(1.80))
                .andExpect(jsonPath("$[1].description").value("Organic Carrefour Bio product"));
    }

    @Test
    void getAllProducts_shouldReturnEmptyList() throws Exception {
        when(productRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
} 