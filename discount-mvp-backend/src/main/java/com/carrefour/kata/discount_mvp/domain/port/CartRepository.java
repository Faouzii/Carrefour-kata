package com.carrefour.kata.discount_mvp.domain.port;

import com.carrefour.kata.discount_mvp.domain.Cart;
import java.util.List;
import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findById(String id);
    Cart save(Cart cart);
    List<Cart> findAll();
} 