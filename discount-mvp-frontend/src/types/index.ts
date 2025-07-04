export interface Product {
  id: string;
  name: string;
  price: number;
  description: string;
}

export interface DiscountCode {
  code: string;
  discountType: 'PERCENTAGE' | 'FIXED_AMOUNT';
  value: number;
  isActive: boolean;
}

export interface CartItem {
  productId: string;
  quantity: number;
  product: Product;
}

export interface Cart {
  id: string;
  items: CartItem[];
  totalAmount: number;
  discountAmount: number;
  finalAmount: number;
  appliedDiscountCode?: string;
}

export interface AddToCartRequest {
  productId: string;
  quantity: number;
}

export interface ApplyDiscountRequest {
  discountCode: string;
}

// API Response types for better type safety
export interface ApiResponse<T> {
  data: T;
  message?: string;
  success: boolean;
}

export interface ApiError {
  message: string;
  status: number;
  timestamp: string;
}

// Utility types
export type LoadingState = 'idle' | 'loading' | 'success' | 'error';

export interface AsyncState<T> {
  data: T | null;
  loading: LoadingState;
  error: string | null;
} 