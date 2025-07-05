import { useState, useCallback } from 'react';
import { Cart as CartType } from '../types';
import { cartService } from '../services/api';
import { useAsyncOperation } from './useAsyncOperation';

interface CartOperationResult {
  success: boolean;
  message: string;
}

export const useCart = () => {
  const [cart, setCart] = useState<CartType>({
    id: '',
    items: [],
    totalAmount: 0,
    discountAmount: 0,
    finalAmount: 0
  });
  
  const { executeOperation, isLoading } = useAsyncOperation({
    onSuccess: (data: CartType) => setCart(data)
  });

  const addToCart = useCallback(async (productId: string, quantity: number): Promise<CartOperationResult> => {
    const result = await executeOperation(
      () => cartService.addToCart({ productId, quantity }),
      `Added ${quantity} item(s) to cart!`,
      'Failed to add item to cart'
    );
    return { success: result.success, message: result.message };
  }, [executeOperation]);

  const updateQuantity = useCallback(async (productId: string, quantity: number): Promise<CartOperationResult> => {
    const result = await executeOperation(
      () => cartService.updateCartItem(productId, quantity),
      'Cart updated!',
      'Failed to update cart'
    );
    return { success: result.success, message: result.message };
  }, [executeOperation]);

  const removeItem = useCallback(async (productId: string): Promise<CartOperationResult> => {
    const result = await executeOperation(
      () => cartService.removeFromCart(productId),
      'Item removed from cart!',
      'Failed to remove item'
    );
    return { success: result.success, message: result.message };
  }, [executeOperation]);

  const applyDiscount = useCallback(async (discountCode: string): Promise<CartOperationResult> => {
    const result = await executeOperation(
      () => cartService.applyDiscount({ discountCode }),
      `Discount code "${discountCode}" applied successfully!`,
      'Failed to apply discount code'
    );
    return { success: result.success, message: result.message };
  }, [executeOperation]);

  const clearCart = useCallback(async (): Promise<CartOperationResult> => {
    const result = await executeOperation(
      () => cartService.clearCart(),
      'Cart cleared!',
      'Failed to clear cart'
    );
    return { success: result.success, message: result.message };
  }, [executeOperation]);

  const loadCart = useCallback(async () => {
    await executeOperation(
      () => cartService.getCart(),
      undefined,
      'Failed to load cart'
    );
  }, [executeOperation]);

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