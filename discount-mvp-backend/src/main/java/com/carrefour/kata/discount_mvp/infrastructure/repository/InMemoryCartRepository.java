package com.carrefour.kata.discount_mvp.infrastructure.repository;

import com.carrefour.kata.discount_mvp.domain.Cart;
import com.carrefour.kata.discount_mvp.domain.port.CartRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
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