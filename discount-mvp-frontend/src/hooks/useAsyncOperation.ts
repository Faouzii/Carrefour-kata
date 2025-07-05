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
      // Try to extract error message from backend response first
      let errorMsg = errorMessage;
      if (!errorMsg) {
        if (err.response?.data?.error) {
          // Backend returns ApiError with 'error' field
          errorMsg = err.response.data.error;
        } else if (err.message) {
          // Fallback to axios error message
          errorMsg = err.message;
        } else {
          errorMsg = 'Operation failed';
        }
      }
      
      // Ensure errorMsg is always a string
      const finalErrorMsg = errorMsg || 'Operation failed';
      
      setError(finalErrorMsg);
      console.error('Operation error:', err);
      
      options.onError?.(err);
      return {
        success: false,
        message: finalErrorMsg
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