package com.carrefour.kata.discountmvp.infrastructure.persistence;

import com.carrefour.kata.discountmvp.domain.DiscountCode;
import com.carrefour.kata.discountmvp.domain.port.DiscountCodeRepository;
import com.carrefour.kata.discountmvp.infrastructure.persistence.entity.DiscountCodeEntity;
import com.carrefour.kata.discountmvp.infrastructure.persistence.repository.JpaDiscountCodeRepository;
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
public class JpaDiscountCodeRepositoryImpl implements DiscountCodeRepository {
    
    private final JpaDiscountCodeRepository jpaDiscountCodeRepository;
    
    @Override
    public Optional<DiscountCode> findByCode(String code) {
        log.debug("Finding discount code by code: {}", code);
        return jpaDiscountCodeRepository.findByCode(code)
                .map(this::toDomain);
    }
    
    @Override
    public List<DiscountCode> findAll() {
        log.debug("Finding all discount codes");
        return jpaDiscountCodeRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }
    
    private DiscountCode toDomain(DiscountCodeEntity entity) {
        return new DiscountCode(
                entity.getCode(),
                entity.getType(),
                entity.getValue(),
                entity.getEligibleProductIds(),
                entity.getExpirationDate()
        );
    }
} 