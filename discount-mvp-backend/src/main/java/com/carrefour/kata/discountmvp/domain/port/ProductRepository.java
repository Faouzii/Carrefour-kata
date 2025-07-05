package com.carrefour.kata.discountmvp.domain.port;

import com.carrefour.kata.discountmvp.domain.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(String id);
    List<Product> findAll();
} 