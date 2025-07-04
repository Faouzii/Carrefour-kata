import React, { useState } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import ProductCard from '../components/ProductCard';
import Cart from '../components/Cart';
import { Product } from '../types';

import { Cart as CartType } from '../types';

interface HomePageProps {
  products: Product[];
  cart: CartType;
  isCartOpen: boolean;
  onToggleCart: () => void;
  onAddToCart: (productId: string, quantity: number) => void;
  onUpdateQuantity: (productId: string, quantity: number) => void;
  onRemoveItem: (productId: string) => void;
  onApplyDiscount: (discountCode: string) => void;
  onClearCart: () => void;
  isLoading?: boolean;
}

const HomePage: React.FC<HomePageProps> = ({
  products,
  cart,
  isCartOpen,
  onToggleCart,
  onAddToCart,
  onUpdateQuantity,
  onRemoveItem,
  onApplyDiscount,
  onClearCart,
  isLoading = false
}) => {
  const handleAddToCart = async (productId: string, quantity: number) => {
    await onAddToCart(productId, quantity);
  };

  const handleClearCart = async () => {
    await onClearCart();
  };

  return (
    <Container fluid className="main-container">
      <div className="content-wrapper">
        {/* Products Section */}
        <div className={`products-section ${isCartOpen ? 'with-cart' : ''}`}>
          <div className="d-flex justify-content-between align-items-center mb-3">
            <h2>
              <i className="bi bi-grid me-2"></i>
              Products
            </h2>
            <span className="text-muted">{products.length} products available</span>
          </div>
          
          <Row xs={1} md={2} lg={3} className="g-3">
            {products.map((product) => (
              <Col key={product.id}>
                <ProductCard
                  product={product}
                  onAddToCart={handleAddToCart}
                  isLoading={isLoading}
                />
              </Col>
            ))}
          </Row>
        </div>

        {/* Sliding Cart Panel */}
        <div className={`cart-panel ${isCartOpen ? 'open' : ''}`}>
          <div className="cart-panel-header">
            <div className="d-flex justify-content-between align-items-center">
              <h5 className="mb-0">
                <i className="bi bi-cart3 me-2"></i>
                Shopping Cart
              </h5>
                              <button 
                  className="btn btn-sm btn-outline-light"
                  onClick={onToggleCart}
                >
                  <i className="bi bi-x-lg"></i>
                </button>
            </div>
          </div>
          
                      <Cart
              cart={cart}
              onUpdateQuantity={onUpdateQuantity}
              onRemoveItem={onRemoveItem}
              onApplyDiscount={onApplyDiscount}
              onClearCart={handleClearCart}
            />
        </div>
      </div>
    </Container>
  );
};

export default HomePage; 