package com.carrefour.kata.discountmvp.infrastructure.repository;

import com.carrefour.kata.discountmvp.domain.Cart;
import com.carrefour.kata.discountmvp.domain.port.CartRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Repository
@ConditionalOnProperty(name = "app.database.type", havingValue = "memory", matchIfMissing = true)
public class InMemoryCartRepository implements CartRepository {
    private final Map<String, Cart> carts = new HashMap<>();

    @Override
    public Optional<Cart> findById(String id) {
        return Optional.ofNullable(carts.get(id));
    }

    @Override
    public Cart save(Cart cart) {
        carts.put(cart.id(), cart);
        return cart;
    }

    @Override
    public List<Cart> findAll() {
        return new ArrayList<>(carts.values());
    }
} 