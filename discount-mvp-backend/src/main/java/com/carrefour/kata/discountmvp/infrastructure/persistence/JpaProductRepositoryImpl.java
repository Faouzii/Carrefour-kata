package com.carrefour.kata.discountmvp.infrastructure.persistence;

import com.carrefour.kata.discountmvp.domain.Product;
import com.carrefour.kata.discountmvp.domain.port.ProductRepository;
import com.carrefour.kata.discountmvp.infrastructure.persistence.entity.ProductEntity;
import com.carrefour.kata.discountmvp.infrastructure.persistence.repository.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "app.database.type", havingValue = "jpa", matchIfMissing = false)
@RequiredArgsConstructor
@Slf4j
public class JpaProductRepositoryImpl implements ProductRepository {
    
    private final JpaProductRepository jpaProductRepository;
    
    @Override
    public Optional<Product> findById(String id) {
        log.debug("Finding product by id: {}", id);
        return jpaProductRepository.findById(id)
                .map(this::toDomain);
    }
    
    @Override
    public List<Product> findAll() {
        log.debug("Finding all products");
        return jpaProductRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }
    
    private Product toDomain(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getDescription()
        );
    }
} 