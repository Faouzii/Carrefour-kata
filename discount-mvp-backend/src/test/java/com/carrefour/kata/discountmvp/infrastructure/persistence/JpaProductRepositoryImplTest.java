package com.carrefour.kata.discountmvp.infrastructure.persistence;

import com.carrefour.kata.discountmvp.domain.Product;
import com.carrefour.kata.discountmvp.infrastructure.persistence.entity.ProductEntity;
import com.carrefour.kata.discountmvp.infrastructure.persistence.repository.JpaProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaProductRepositoryImplTest {

    @Mock
    private JpaProductRepository jpaProductRepository;

    private JpaProductRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new JpaProductRepositoryImpl(jpaProductRepository);
    }

    @Test
    void findById_shouldReturnProduct_whenProductExists() {
        // Given
        var productEntity = new ProductEntity("1", "Test Product", BigDecimal.valueOf(10.00), "Test description");
        when(jpaProductRepository.findById("1")).thenReturn(Optional.of(productEntity));

        // When
        Optional<Product> result = repository.findById("1");

        // Then
        assertTrue(result.isPresent());
        assertEquals("1", result.get().id());
        assertEquals("Test Product", result.get().name());
        assertEquals(BigDecimal.valueOf(10.00), result.get().price());
        assertEquals("Test description", result.get().description());
    }

    @Test
    void findById_shouldReturnEmpty_whenProductDoesNotExist() {
        // Given
        when(jpaProductRepository.findById("999")).thenReturn(Optional.empty());

        // When
        Optional<Product> result = repository.findById("999");

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void findAll_shouldReturnAllProducts() {
        // Given
        var productEntity1 = new ProductEntity("1", "Product 1", BigDecimal.valueOf(10.00), "Description 1");
        var productEntity2 = new ProductEntity("2", "Product 2", BigDecimal.valueOf(20.00), "Description 2");
        when(jpaProductRepository.findAll()).thenReturn(List.of(productEntity1, productEntity2));

        // When
        List<Product> products = repository.findAll();

        // Then
        assertEquals(2, products.size());
        assertEquals("1", products.get(0).id());
        assertEquals("Product 1", products.get(0).name());
        assertEquals("2", products.get(1).id());
        assertEquals("Product 2", products.get(1).name());
    }
} 