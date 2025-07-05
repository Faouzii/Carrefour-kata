package com.carrefour.kata.discountmvp.application;

import com.carrefour.kata.discountmvp.domain.*;
import com.carrefour.kata.discountmvp.domain.discount.DiscountCalculator;
import com.carrefour.kata.discountmvp.domain.discount.PercentageDiscountCalculator;
import com.carrefour.kata.discountmvp.domain.discount.FixedAmountDiscountCalculator;
import com.carrefour.kata.discountmvp.domain.exception.*;
import com.carrefour.kata.discountmvp.domain.port.CartRepository;
import com.carrefour.kata.discountmvp.domain.port.DiscountCodeRepository;
import com.carrefour.kata.discountmvp.domain.port.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private DiscountCodeRepository discountCodeRepository;
    private List<DiscountCalculator> discountCalculators;
    private CartService cartService;

    private final Product apple = new Product("1", "Apple", BigDecimal.valueOf(1.00), "Fresh Apple product");
    private final Product banana = new Product("2", "Banana", BigDecimal.valueOf(0.80), "Fresh Banana product");
    private final DiscountCode perc10 = new DiscountCode("PERC10", DiscountType.PERCENTAGE, BigDecimal.valueOf(10), Set.of("1", "2"), LocalDate.now().plusDays(10));
    private final DiscountCode expired = new DiscountCode("EXPIRED", DiscountType.PERCENTAGE, BigDecimal.valueOf(10), Set.of("1"), LocalDate.now().minusDays(1));
    private final DiscountCode notApplicable = new DiscountCode("NA", DiscountType.PERCENTAGE, BigDecimal.valueOf(10), Set.of("3"), LocalDate.now().plusDays(10));

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        productRepository = mock(ProductRepository.class);
        discountCodeRepository = mock(DiscountCodeRepository.class);
        discountCalculators = List.of(new PercentageDiscountCalculator(), new FixedAmountDiscountCalculator());
        cartService = new CartService(cartRepository, productRepository, discountCodeRepository, discountCalculators);
    }

    @Test
    void addItem_shouldAddNewItemToCart() {
        when(cartRepository.findById("cart1")).thenReturn(Optional.empty());
        when(productRepository.findById("1")).thenReturn(Optional.of(apple));
        when(cartRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Cart cart = cartService.addItem("cart1", "1", 2);
        assertEquals(1, cart.items().size());
        assertEquals("Apple", cart.items().get(0).product().name());
        assertEquals(2, cart.items().get(0).quantity());
    }

    @Test
    void applyDiscountCode_shouldApplyValidDiscount() {
        Cart cart = new Cart("cart1", List.of(new CartItem(apple, 2)), null);
        when(cartRepository.findById("cart1")).thenReturn(Optional.of(cart));
        when(discountCodeRepository.findByCode("PERC10")).thenReturn(Optional.of(perc10));
        when(cartRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Cart updated = cartService.applyDiscountCode("cart1", "PERC10");
        assertTrue(updated.getAppliedDiscountCode().isPresent());
        assertEquals("PERC10", updated.getAppliedDiscountCode().get().getCode());
    }

    @Test
    void applyDiscountCode_shouldThrowIfExpired() {
        Cart cart = new Cart("cart1", List.of(new CartItem(apple, 2)), null);
        when(cartRepository.findById("cart1")).thenReturn(Optional.of(cart));
        when(discountCodeRepository.findByCode("EXPIRED")).thenReturn(Optional.of(expired));
        assertThrows(DiscountCodeExpiredException.class, () -> cartService.applyDiscountCode("cart1", "EXPIRED"));
    }

    @Test
    void applyDiscountCode_shouldThrowIfNotApplicable() {
        Cart cart = new Cart("cart1", List.of(new CartItem(apple, 2)), null);
        when(cartRepository.findById("cart1")).thenReturn(Optional.of(cart));
        when(discountCodeRepository.findByCode("NA")).thenReturn(Optional.of(notApplicable));
        assertThrows(DiscountCodeNotApplicableException.class, () -> cartService.applyDiscountCode("cart1", "NA"));
    }

    @Test
    void calculateTotal_shouldApplyPercentageDiscount() {
        Cart cart = new Cart("cart1", List.of(new CartItem(apple, 2)), perc10);
        when(cartRepository.findById("cart1")).thenReturn(Optional.of(cart));
        BigDecimal total = cartService.calculateTotal("cart1");

        assertEquals(BigDecimal.valueOf(1.80).setScale(2), total.setScale(2));
    }

    @Test
    void calculateTotal_shouldReturnFullPriceIfNoDiscount() {
        Cart cart = new Cart("cart1", List.of(new CartItem(apple, 2)), null);
        when(cartRepository.findById("cart1")).thenReturn(Optional.of(cart));
        BigDecimal total = cartService.calculateTotal("cart1");
        assertEquals(BigDecimal.valueOf(2.00).setScale(2), total.setScale(2));
    }

    @Test
    void addItem_shouldThrowIfInvalidQuantity() {
        assertThrows(InvalidQuantityException.class, () -> cartService.addItem("cart1", "1", 0));
        assertThrows(InvalidQuantityException.class, () -> cartService.addItem("cart1", "1", -1));
    }

    @Test
    void addItem_shouldThrowIfProductNotFound() {
        when(cartRepository.findById("cart1")).thenReturn(Optional.empty());
        when(productRepository.findById("999")).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> cartService.addItem("cart1", "999", 1));
    }

    @Test
    void applyDiscountCode_shouldThrowIfCartNotFound() {
        when(cartRepository.findById("nonexistent")).thenReturn(Optional.empty());
        assertThrows(CartNotFoundException.class, () -> cartService.applyDiscountCode("nonexistent", "PERC10"));
    }

    @Test
    void applyDiscountCode_shouldThrowIfDiscountCodeNotFound() {
        Cart cart = new Cart("cart1", List.of(new CartItem(apple, 2)), null);
        when(cartRepository.findById("cart1")).thenReturn(Optional.of(cart));
        when(discountCodeRepository.findByCode("INVALID")).thenReturn(Optional.empty());
        assertThrows(DiscountCodeNotFoundException.class, () -> cartService.applyDiscountCode("cart1", "INVALID"));
    }

    @Test
    void updateItem_shouldRemoveItemWhenQuantityIsZero() {
        Cart cart = new Cart("cart1", List.of(new CartItem(apple, 2)), null);
        when(cartRepository.findById("cart1")).thenReturn(Optional.of(cart));
        when(cartRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        
        Cart result = cartService.updateItem("cart1", "1", 0);
        
        assertTrue(result.items().isEmpty());
    }

    @Test
    void updateItem_shouldRemoveItemWhenQuantityIsNegative() {
        Cart cart = new Cart("cart1", List.of(new CartItem(apple, 2)), null);
        when(cartRepository.findById("cart1")).thenReturn(Optional.of(cart));
        when(cartRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        
        Cart result = cartService.updateItem("cart1", "1", -1);
        
        assertTrue(result.items().isEmpty());
    }

    @Test
    void calculateTotal_shouldHandleEmptyCart() {
        Cart emptyCart = new Cart("cart1", List.of(), null);
        when(cartRepository.findById("cart1")).thenReturn(Optional.of(emptyCart));
        
        BigDecimal total = cartService.calculateTotal("cart1");
        
        assertEquals(BigDecimal.ZERO, total);
    }

    @Test
    void calculateTotal_shouldHandleCartWithNoDiscount() {
        Cart cart = new Cart("cart1", List.of(new CartItem(apple, 3)), null);
        when(cartRepository.findById("cart1")).thenReturn(Optional.of(cart));
        
        BigDecimal total = cartService.calculateTotal("cart1");
        
        assertEquals(BigDecimal.valueOf(3.00), total);
    }

    @Test
    void addItem_shouldUpdateExistingItemQuantity() {
        Cart cart = new Cart("cart1", List.of(new CartItem(apple, 2)), null);
        when(cartRepository.findById("cart1")).thenReturn(Optional.of(cart));
        when(productRepository.findById("1")).thenReturn(Optional.of(apple));
        when(cartRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        
        Cart result = cartService.addItem("cart1", "1", 3);
        
        assertEquals(1, result.items().size());
        assertEquals(5, result.items().get(0).quantity()); // 2 + 3
    }
} 