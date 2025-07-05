import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import './App.css';

import Layout from './components/Layout';
import HomePage from './pages/HomePage';
import AboutPage from './pages/AboutPage';
import LoadingSpinner from './components/LoadingSpinner';
import ErrorDisplay from './components/ErrorDisplay';
import { useProducts } from './hooks/useProducts';
import { useCartWithToast } from './hooks/useCartWithToast';
import { useToast } from './hooks/useToast';
import { useNavigation } from './hooks/useNavigation';

function App() {
  const [isCartOpen, setIsCartOpen] = useState(false);
  
  const { products, isLoading: productsLoading, error: productsError, loadProducts } = useProducts();
  const { toast, hideToast, showToast } = useToast();
  const { 
    cart, 
    isLoading: cartLoading, 
    addToCart, 
    updateQuantity, 
    removeItem, 
    applyDiscount, 
    clearCart, 
    loadCart, 
    getCartItemCount 
  } = useCartWithToast({ showToast });
  const { currentPage, navigateTo } = useNavigation();

  useEffect(() => {
    const loadInitialData = async () => {
      await Promise.all([loadProducts(), loadCart()]);
    };
    loadInitialData();
  }, []); // Only run once on mount

  const handleAddToCart = async (productId: string, quantity: number) => {
    const result = await addToCart(productId, quantity);
    if (result.success) {
      setIsCartOpen(true);
    }
  };

  const handleClearCart = async () => {
    const result = await clearCart();
    if (result.success) {
      setIsCartOpen(false);
    }
  };

  if (productsLoading) {
    return <LoadingSpinner />;
  }

  if (productsError) {
    return <ErrorDisplay error={productsError} />;
  }

  const renderPage = () => {
    switch (currentPage) {
      case 'about':
        return <AboutPage />;
      case 'home':
      default:
        return (
          <HomePage
            products={products}
            cart={cart}
            isCartOpen={isCartOpen}
            onToggleCart={() => setIsCartOpen(!isCartOpen)}
            onAddToCart={handleAddToCart}
            onUpdateQuantity={updateQuantity}
            onRemoveItem={removeItem}
            onApplyDiscount={applyDiscount}
            onClearCart={handleClearCart}
            isLoading={cartLoading}
          />
        );
    }
  };

  return (
    <Layout
      cartItemCount={getCartItemCount()}
      onToggleCart={() => setIsCartOpen(!isCartOpen)}
      currentPage={currentPage}
      onNavigate={navigateTo}
      toast={toast}
      onCloseToast={hideToast}
    >
      {renderPage()}
    </Layout>
  );
}

export default App;
