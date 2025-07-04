import { useState, useCallback } from 'react';

export type ToastVariant = 'success' | 'danger';

interface ToastState {
  show: boolean;
  message: string;
  variant: ToastVariant;
}

export const useToast = () => {
  const [toast, setToast] = useState<ToastState>({
    show: false,
    message: '',
    variant: 'success'
  });

  const showToast = useCallback((message: string, variant: ToastVariant = 'success') => {
    setToast({ show: true, message, variant });
  }, []);

  const hideToast = useCallback(() => {
    setToast(prev => ({ ...prev, show: false }));
  }, []);

  return {
    toast,
    showToast,
    hideToast
  };
}; 