import { useState, useCallback } from 'react';

interface AsyncOperationResult<T> {
  success: boolean;
  data?: T;
  message: string;
}

interface UseAsyncOperationOptions {
  onSuccess?: (data: any) => void;
  onError?: (error: any) => void;
}

export const useAsyncOperation = <T = any>(options: UseAsyncOperationOptions = {}) => {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const executeOperation = useCallback(async (
    operation: () => Promise<T>,
    successMessage?: string,
    errorMessage?: string
  ): Promise<AsyncOperationResult<T>> => {
    try {
      setIsLoading(true);
      setError(null);
      
      const result = await operation();
      
      options.onSuccess?.(result);
      return {
        success: true,
        data: result,
        message: successMessage || 'Operation completed successfully'
      };
    } catch (err: any) {
      const errorMsg = errorMessage || err.message || 'Operation failed';
      setError(errorMsg);
      console.error('Operation error:', err);
      
      options.onError?.(err);
      return {
        success: false,
        message: errorMsg
      };
    } finally {
      setIsLoading(false);
    }
  }, [options]);

  const clearError = useCallback(() => {
    setError(null);
  }, []);

  return {
    isLoading,
    error,
    executeOperation,
    clearError
  };
}; 