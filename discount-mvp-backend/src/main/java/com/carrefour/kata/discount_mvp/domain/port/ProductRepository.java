package com.carrefour.kata.discount_mvp.domain.port;

import com.carrefour.kata.discount_mvp.domain.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(String id);
    List<Product> findAll();
} 