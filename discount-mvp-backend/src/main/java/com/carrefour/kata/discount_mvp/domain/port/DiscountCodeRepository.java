package com.carrefour.kata.discount_mvp.domain.port;

import com.carrefour.kata.discount_mvp.domain.DiscountCode;
import java.util.List;
import java.util.Optional;

public interface DiscountCodeRepository {
    Optional<DiscountCode> findByCode(String code);
    List<DiscountCode> findAll();
} 