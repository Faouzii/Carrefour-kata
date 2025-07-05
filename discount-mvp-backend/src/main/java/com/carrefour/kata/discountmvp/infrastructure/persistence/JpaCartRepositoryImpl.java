package com.carrefour.kata.discountmvp.infrastructure.persistence;

import com.carrefour.kata.discountmvp.domain.Cart;
import com.carrefour.kata.discountmvp.domain.CartItem;
import com.carrefour.kata.discountmvp.domain.DiscountCode;
import com.carrefour.kata.discountmvp.domain.Product;
import com.carrefour.kata.discountmvp.domain.port.CartRepository;
import com.carrefour.kata.discountmvp.infrastructure.persistence.entity.CartEntity;
import com.carrefour.kata.discountmvp.infrastructure.persistence.entity.CartItemEntity;
import com.carrefour.kata.discountmvp.infrastructure.persistence.entity.DiscountCodeEntity;
import com.carrefour.kata.discountmvp.infrastructure.persistence.entity.ProductEntity;
import com.carrefour.kata.discountmvp.infrastructure.persistence.repository.JpaCartRepository;
import com.carrefour.kata.discountmvp.infrastructure.persistence.repository.JpaDiscountCodeRepository;
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
public class JpaCartRepositoryImpl implements CartRepository {
    
    private final JpaCartRepository jpaCartRepository;
    private final JpaProductRepository jpaProductRepository;
    private final JpaDiscountCodeRepository jpaDiscountCodeRepository;
    
    @Override
    public Optional<Cart> findById(String id) {
        log.debug("Finding cart by id: {}", id);
        return jpaCartRepository.findById(id)
                .map(this::toDomain);
    }
    
    @Override
    public Cart save(Cart cart) {
        log.debug("Saving cart: {}", cart.id());
        CartEntity entity = toEntity(cart);
        CartEntity savedEntity = jpaCartRepository.save(entity);
        return toDomain(savedEntity);
    }
    
    @Override
    public List<Cart> findAll() {
        log.debug("Finding all carts");
        return jpaCartRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }
    
    private Cart toDomain(CartEntity entity) {
        List<CartItem> cartItems = entity.getItems().stream()
                .map(this::toCartItemDomain)
                .toList();
        
        DiscountCode appliedDiscountCode = null;
        if (entity.getAppliedDiscountCode() != null) {
            appliedDiscountCode = toDiscountCodeDomain(entity.getAppliedDiscountCode());
        }
        
        return new Cart(entity.getId(), cartItems, appliedDiscountCode);
    }
    
    private CartItem toCartItemDomain(CartItemEntity entity) {
        Product product = toProductDomain(entity.getProduct());
        return new CartItem(product, entity.getQuantity());
    }
    
    private Product toProductDomain(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getDescription()
        );
    }
    
    private DiscountCode toDiscountCodeDomain(DiscountCodeEntity entity) {
        return new DiscountCode(
                entity.getCode(),
                entity.getType(),
                entity.getValue(),
                entity.getEligibleProductIds(),
                entity.getExpirationDate()
        );
    }
    
    private CartEntity toEntity(Cart cart) {
        List<CartItemEntity> cartItemEntities = cart.items().stream()
                .map(this::toCartItemEntity)
                .toList();
        
        DiscountCodeEntity appliedDiscountCodeEntity = null;
        if (cart.getAppliedDiscountCode().isPresent()) {
            appliedDiscountCodeEntity = jpaDiscountCodeRepository.findById(
                    cart.getAppliedDiscountCode().get().getCode()
            ).orElse(null);
        }
        
        CartEntity entity = new CartEntity(cart.id(), cartItemEntities, appliedDiscountCodeEntity);
        
        // Set the cart reference in cart items
        cartItemEntities.forEach(item -> item.setCart(entity));
        
        return entity;
    }
    
    private CartItemEntity toCartItemEntity(CartItem cartItem) {
        ProductEntity productEntity = jpaProductRepository.findById(cartItem.product().id()).orElse(null);
        return new CartItemEntity(null, null, productEntity, cartItem.quantity());
    }
} 