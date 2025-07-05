package com.carrefour.kata.discountmvp.infrastructure.web;

import com.carrefour.kata.discountmvp.domain.Product;
import com.carrefour.kata.discountmvp.domain.port.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    private ProductDto toDto(Product product) {
        String description = getProductDescription(product.name());
        return new ProductDto(
                product.id(),
                product.name(),
                product.price(),
                description
        );
    }

    private String getProductDescription(String productName) {
        
        String lowerName = productName.toLowerCase();
        if (lowerName.contains("organic") || lowerName.contains("bio")) {
            return "Organic Carrefour Bio product, sustainably sourced and certified organic.";
        } else if (lowerName.contains("fresh") || lowerName.contains("fruit") || lowerName.contains("vegetable")) {
            return "Fresh Carrefour product, carefully selected for quality and taste.";
        } else if (lowerName.contains("premium") || lowerName.contains("gourmet")) {
            return "Premium Carrefour selection, offering exceptional quality and flavor.";
        } else if (lowerName.contains("local") || lowerName.contains("regional")) {
            return "Local Carrefour product, supporting regional producers and sustainability.";
        } else {
            return "Quality Carrefour product with sustainable sourcing and excellent value.";
        }
    }

    public record ProductDto(String id, String name, java.math.BigDecimal price, String description) {}
} 