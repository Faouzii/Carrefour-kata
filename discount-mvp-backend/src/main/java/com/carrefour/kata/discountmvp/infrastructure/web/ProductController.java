package com.carrefour.kata.discountmvp.infrastructure.web;

import com.carrefour.kata.discountmvp.domain.Product;
import com.carrefour.kata.discountmvp.domain.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        var products = productRepository.findAll();
        var productDtos = products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    private ProductDto toDto(Product product) {
        return new ProductDto(
                product.id(),
                product.name(),
                product.price(),
                product.description()
        );
    }

    public record ProductDto(String id, String name, java.math.BigDecimal price, String description) {}
} 