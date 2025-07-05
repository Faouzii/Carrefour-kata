package com.carrefour.kata.discountmvp.infrastructure.repository;

import com.carrefour.kata.discountmvp.domain.Product;
import com.carrefour.kata.discountmvp.domain.ProductDescriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InMemoryProductRepositoryTest {

    @Mock
    private ProductDescriptionService descriptionService;

    private InMemoryProductRepository repository;

    @BeforeEach
    void setUp() {
        when(descriptionService.generateDescription(anyString())).thenReturn("Test description");
        repository = new InMemoryProductRepository(descriptionService);
    }

    @Test
    void findById_shouldReturnProduct_whenProductExists() {
        Optional<Product> result = repository.findById("1");
        
        assertTrue(result.isPresent());
        assertEquals("1", result.get().id());
        assertEquals("Fresh Apples", result.get().name());
        assertEquals(BigDecimal.valueOf(2.50), result.get().price());
    }

    @Test
    void findById_shouldReturnEmpty_whenProductDoesNotExist() {
        Optional<Product> result = repository.findById("999");
        
        assertFalse(result.isPresent());
    }

    @Test
    void findAll_shouldReturnAllProducts() {
        List<Product> products = repository.findAll();
        
        assertEquals(8, products.size());
        assertTrue(products.stream().anyMatch(p -> p.id().equals("1")));
        assertTrue(products.stream().anyMatch(p -> p.id().equals("2")));
        assertTrue(products.stream().anyMatch(p -> p.id().equals("6")));
    }

    @Test
    void findAll_shouldReturnProductsWithDescriptions() {
        List<Product> products = repository.findAll();
        
        assertTrue(products.stream().allMatch(p -> p.description() != null));
        assertTrue(products.stream().allMatch(p -> !p.description().isEmpty()));
    }

    @Test
    void findById_shouldReturnProductWithCorrectPrice() {
        Optional<Product> result = repository.findById("6");
        
        assertTrue(result.isPresent());
        assertEquals(BigDecimal.valueOf(299.99), result.get().price());
    }
} 