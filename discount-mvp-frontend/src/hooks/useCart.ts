import { useState, useCallback } from 'react';
import { Cart as CartType } from '../types';
import { cartService } from '../services/api';

export const useCart = () => {
  const [cart, setCart] = useState<CartType>({
    id: '',
    items: [],
    totalAmount: 0,
    discountAmount: 0,
    finalAmount: 0
  });
  const [isLoading, setIsLoading] = useState(false);

  const addToCart = useCallback(async (productId: string, quantity: number) => {
    try {
      setIsLoading(true);
      const updatedCart = await cartService.addToCart({ productId, quantity });
      setCart(updatedCart);
      return { success: true, message: `Added ${quantity} item(s) to cart!` };
    } catch (err) {
      console.error('Error adding to cart:', err);
      return { success: false, message: 'Failed to add item to cart' };
    } finally {
      setIsLoading(false);
    }
  }, []);

  const updateQuantity = useCallback(async (productId: string, quantity: number) => {
    try {
      setIsLoading(true);
      const updatedCart = await cartService.updateCartItem(productId, quantity);
      setCart(updatedCart);
      return { success: true, message: 'Cart updated!' };
    } catch (err) {
      console.error('Error updating cart:', err);
      return { success: false, message: 'Failed to update cart' };
    } finally {
      setIsLoading(false);
    }
  }, []);

  const removeItem = useCallback(async (productId: string) => {
    try {
      setIsLoading(true);
      const updatedCart = await cartService.removeFromCart(productId);
      setCart(updatedCart);
      return { success: true, message: 'Item removed from cart!' };
    } catch (err) {
      console.error('Error removing item:', err);
      return { success: false, message: 'Failed to remove item' };
    } finally {
      setIsLoading(false);
    }
  }, []);

  const applyDiscount = useCallback(async (discountCode: string) => {
    try {
      setIsLoading(true);
      const updatedCart = await cartService.applyDiscount({ discountCode });
      setCart(updatedCart);
      return { success: true, message: `Discount code "${discountCode}" applied successfully!` };
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || 'Failed to apply discount code';
      console.error('Error applying discount:', err);
      return { success: false, message: errorMessage };
    } finally {
      setIsLoading(false);
    }
  }, []);

  const clearCart = useCallback(async () => {
    try {
      setIsLoading(true);
      const updatedCart = await cartService.clearCart();
      setCart(updatedCart);
      return { success: true, message: 'Cart cleared!' };
    } catch (err) {
      console.error('Error clearing cart:', err);
      return { success: false, message: 'Failed to clear cart' };
    } finally {
      setIsLoading(false);
    }
  }, []);

  const loadCart = useCallback(async () => {
    try {
      setIsLoading(true);
      const cartData = await cartService.getCart();
      setCart(cartData);
    } catch (err) {
      console.error('Error loading cart:', err);
    } finally {
      setIsLoading(false);
    }
  }, []);

  const getCartItemCount = useCallback(() => {
    return cart.items.reduce((total, item) => total + item.quantity, 0);
  }, [cart.items]);

  return {
    cart,
    isLoading,
    addToCart,
    updateQuantity,
    removeItem,
    applyDiscount,
    clearCart,
    loadCart,
    getCartItemCount
  };
}; 