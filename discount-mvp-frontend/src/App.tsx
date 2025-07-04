import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import './App.css';

import Layout from './components/Layout';
import HomePage from './pages/HomePage';
import AboutPage from './pages/AboutPage';
import LoadingSpinner from './components/LoadingSpinner';
import ErrorDisplay from './components/ErrorDisplay';
import { useCart } from './hooks/useCart';
import { useProducts } from './hooks/useProducts';
import { useToast } from './hooks/useToast';
import { useNavigation } from './hooks/useNavigation';

function App() {
  const [isCartOpen, setIsCartOpen] = useState(false);
  
  const { products, isLoading: productsLoading, error: productsError, loadProducts } = useProducts();
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
  } = useCart();
  const { toast, showToast, hideToast } = useToast();
  const { currentPage, navigateTo } = useNavigation();

  useEffect(() => {
    const loadInitialData = async () => {
      await Promise.all([loadProducts(), loadCart()]);
    };
    loadInitialData();
  }, [loadProducts, loadCart]);

  const handleAddToCart = async (productId: string, quantity: number) => {
    const result = await addToCart(productId, quantity);
    if (result.success) {
      setIsCartOpen(true);
    }
    showToast(result.message, result.success ? 'success' : 'danger');
  };

  const handleUpdateQuantity = async (productId: string, quantity: number) => {
    const result = await updateQuantity(productId, quantity);
    showToast(result.message, result.success ? 'success' : 'danger');
  };

  const handleRemoveItem = async (productId: string) => {
    const result = await removeItem(productId);
    showToast(result.message, result.success ? 'success' : 'danger');
  };

  const handleApplyDiscount = async (discountCode: string) => {
    const result = await applyDiscount(discountCode);
    showToast(result.message, result.success ? 'success' : 'danger');
  };

  const handleClearCart = async () => {
    const result = await clearCart();
    if (result.success) {
      setIsCartOpen(false);
    }
    showToast(result.message, result.success ? 'success' : 'danger');
  };

  if (productsLoading || cartLoading) {
    return <LoadingSpinner />;
  }

  if (productsError) {
    return <ErrorDisplay error={productsError} />;
  }

  const renderPage = () => {
    switch (currentPage) {
      case 'home':
        return (
          <HomePage
            products={products}
            cart={cart}
            isCartOpen={isCartOpen}
            onToggleCart={() => setIsCartOpen(!isCartOpen)}
            onAddToCart={handleAddToCart}
            onUpdateQuantity={handleUpdateQuantity}
            onRemoveItem={handleRemoveItem}
            onApplyDiscount={handleApplyDiscount}
            onClearCart={handleClearCart}
            isLoading={cartLoading}
          />
        );
      case 'about':
        return <AboutPage />;
      default:
        return <HomePage
          products={products}
          cart={cart}
          isCartOpen={isCartOpen}
          onToggleCart={() => setIsCartOpen(!isCartOpen)}
          onAddToCart={handleAddToCart}
          onUpdateQuantity={handleUpdateQuantity}
          onRemoveItem={handleRemoveItem}
          onApplyDiscount={handleApplyDiscount}
          onClearCart={handleClearCart}
          isLoading={cartLoading}
        />;
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
