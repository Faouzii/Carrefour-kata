package com.carrefour.kata.discountmvp.infrastructure.persistence.repository;

import com.carrefour.kata.discountmvp.infrastructure.persistence.entity.DiscountCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaDiscountCodeRepository extends JpaRepository<DiscountCodeEntity, String> {
    Optional<DiscountCodeEntity> findByCode(String code);
} 