package com.carrefour.kata.discountmvp.infrastructure.persistence.repository;

import com.carrefour.kata.discountmvp.infrastructure.persistence.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCartRepository extends JpaRepository<CartEntity, String> {
} 