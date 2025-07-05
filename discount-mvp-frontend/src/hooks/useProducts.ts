import { useState, useCallback } from 'react';
import { Product } from '../types';
import { productService } from '../services/api';
import { useAsyncOperation } from './useAsyncOperation';

export const useProducts = () => {
  const [products, setProducts] = useState<Product[]>([]);
  
  const { executeOperation, isLoading, error, clearError } = useAsyncOperation({
    onSuccess: (data: Product[]) => setProducts(data)
  });

  const loadProducts = useCallback(async () => {
    await executeOperation(
      () => productService.getAllProducts(),
      undefined,
      'Failed to load products. Please check if the backend is running.'
    );
  }, [executeOperation]);

  return {
    products,
    isLoading,
    error,
    loadProducts,
    clearError
  };
}; 