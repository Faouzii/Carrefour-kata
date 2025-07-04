import axios, { AxiosError, AxiosResponse } from 'axios';
import { Product, Cart, AddToCartRequest, ApplyDiscountRequest } from '../types';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000, // 10 seconds timeout
});

api.interceptors.request.use(
  (config) => {
    console.log(`API Request: ${config.method?.toUpperCase()} ${config.url}`);
    return config;
  },
  (error) => {
    console.error('API Request Error:', error);
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (response: AxiosResponse) => {
    console.log(`API Response: ${response.status} ${response.config.url}`);
    return response;
  },
  (error: AxiosError) => {
    console.error('API Response Error:', {
      status: error.response?.status,
      message: error.message,
      url: error.config?.url
    });
    return Promise.reject(error);
  }
);

export const productService = {
  getAllProducts: async (): Promise<Product[]> => {
    const response = await api.get('/v1/products');
    return response.data;
  },
};

export const cartService = {
  getCart: async (): Promise<Cart> => {
    const response = await api.get('/v1/cart');
    return response.data;
  },

  addToCart: async (request: AddToCartRequest): Promise<Cart> => {
    const response = await api.post('/v1/cart/items', request);
    return response.data;
  },

  updateCartItem: async (productId: string, quantity: number): Promise<Cart> => {
    const response = await api.put(`/v1/cart/items/${productId}`, { quantity });
    return response.data;
  },

  removeFromCart: async (productId: string): Promise<Cart> => {
    const response = await api.delete(`/v1/cart/items/${productId}`);
    return response.data;
  },

  applyDiscount: async (request: ApplyDiscountRequest): Promise<Cart> => {
    const response = await api.post('/v1/cart/discount', request);
    return response.data;
  },

  clearCart: async (): Promise<Cart> => {
    const response = await api.delete('/v1/cart');
    return response.data;
  },
}; 