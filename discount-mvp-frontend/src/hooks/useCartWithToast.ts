import { useCallback } from 'react';
import { useCart } from './useCart';
import { ToastVariant } from './useToast';

interface UseCartWithToastOptions {
  showToast: (message: string, variant: ToastVariant) => void;
}

export const useCartWithToast = (options: UseCartWithToastOptions) => {
  const cart = useCart();
  const { showToast } = options;

  const addToCartWithToast = useCallback(async (productId: string, quantity: number) => {
    const result = await cart.addToCart(productId, quantity);
    showToast(result.message, result.success ? 'success' : 'danger');
    return result;
  }, [cart, showToast]);

  const updateQuantityWithToast = useCallback(async (productId: string, quantity: number) => {
    const result = await cart.updateQuantity(productId, quantity);
    showToast(result.message, result.success ? 'success' : 'danger');
    return result;
  }, [cart, showToast]);

  const removeItemWithToast = useCallback(async (productId: string) => {
    const result = await cart.removeItem(productId);
    showToast(result.message, result.success ? 'success' : 'danger');
    return result;
  }, [cart, showToast]);

  const applyDiscountWithToast = useCallback(async (discountCode: string) => {
    const result = await cart.applyDiscount(discountCode);
    // Only show toast for success, not for errors (errors will be displayed inline)
    if (result.success) {
      showToast(result.message, 'success');
    }
    return result;
  }, [cart, showToast]);

  const clearCartWithToast = useCallback(async () => {
    const result = await cart.clearCart();
    showToast(result.message, result.success ? 'success' : 'danger');
    return result;
  }, [cart, showToast]);

  return {
    ...cart,
    addToCart: addToCartWithToast,
    updateQuantity: updateQuantityWithToast,
    removeItem: removeItemWithToast,
    applyDiscount: applyDiscountWithToast,
    clearCart: clearCartWithToast,
  };
}; 