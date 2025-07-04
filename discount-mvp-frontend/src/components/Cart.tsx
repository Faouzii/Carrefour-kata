import React, { useState, useEffect } from 'react';
import { Card, Button, Form, Badge, Alert } from 'react-bootstrap';
import { Cart as CartType, CartItem } from '../types';

interface CartProps {
  cart: CartType;
  onUpdateQuantity: (productId: string, quantity: number) => void;
  onRemoveItem: (productId: string) => void;
  onApplyDiscount: (discountCode: string) => void;
  onClearCart: () => void;
  isLoading?: boolean;
}

const Cart: React.FC<CartProps> = ({
  cart,
  onUpdateQuantity,
  onRemoveItem,
  onApplyDiscount,
  onClearCart,
  isLoading = false
}) => {
  const [discountCode, setDiscountCode] = useState('');
  const [showDiscountForm, setShowDiscountForm] = useState(false);



  const handleApplyDiscount = (e: React.FormEvent) => {
    e.preventDefault();
    if (discountCode.trim()) {
      onApplyDiscount(discountCode.trim());
      setDiscountCode('');
      setShowDiscountForm(false);
    }
  };

  const handleQuantityChange = (item: CartItem, newQuantity: number) => {
    if (newQuantity > 0) {
      onUpdateQuantity(item.productId, newQuantity);
    } else {
      onRemoveItem(item.productId);
    }
  };

    return (
    <div className="cart-container" style={{ height: '100%', display: 'flex', flexDirection: 'column' }}>
      {cart.items.length === 0 ? (
        <div className="text-center py-5 flex-grow-1 d-flex align-items-center justify-content-center">
          <div>
            <div className="bg-light rounded-circle p-4 mb-3" style={{ width: '80px', height: '80px', margin: '0 auto', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
              <i className="bi bi-cart-x fs-2 text-muted"></i>
            </div>
            <h6 className="text-muted mb-2">Your cart is empty</h6>
            <p className="text-muted small">Add some products to get started!</p>
          </div>
        </div>
      ) : (
        <>
          {/* Scrollable Cart Items */}
          <div className="cart-items-scrollable" style={{ flex: 1, overflowY: 'auto', padding: '1rem', paddingBottom: '0', maxHeight: 'calc(100vh - 400px)' }}>
            {cart.items.map((item) => (
              <div key={item.productId} className="cart-item p-3 border-bottom mb-3" style={{ backgroundColor: 'rgba(255, 255, 255, 0.15)', borderRadius: '12px', border: '1px solid rgba(255, 255, 255, 0.3)', boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)' }}>
                <div className="d-flex justify-content-between align-items-start">
                  <div className="flex-grow-1 me-3">
                    <h6 className="mb-2 fw-semibold text-white">{item.product.name}</h6>
                    <p className="text-white-50 small mb-3" style={{ lineHeight: '1.4' }}>{item.product.description}</p>
                    <div className="d-flex align-items-center justify-content-between">
                      <div className="d-flex align-items-center gap-3">
                        <Form.Label className="mb-0 fw-semibold small text-white">Qty:</Form.Label>
                        <Form.Control
                          type="number"
                          min="1"
                          max="99"
                          value={item.quantity}
                          onChange={(e) => handleQuantityChange(item, parseInt(e.target.value) || 0)}
                          style={{ 
                            width: '80px', 
                            height: '40px',
                            fontSize: '1rem',
                            fontWeight: '600',
                            textAlign: 'center',
                            backgroundColor: 'rgba(255, 255, 255, 0.9)',
                            border: '2px solid rgba(255, 255, 255, 0.3)'
                          }}
                          className="border-2"
                        />
                      </div>
                      <div className="text-end">
                        <div className="text-white fw-bold fs-5">
                          €{(item.product.price * item.quantity).toFixed(2)}
                        </div>
                        <div className="text-white-50 small">
                          €{item.product.price.toFixed(2)} each
                        </div>
                      </div>
                    </div>
                  </div>
                  <Button
                    variant="outline-light"
                    size="sm"
                    onClick={() => onRemoveItem(item.productId)}
                    disabled={isLoading}
                    className="border-2"
                    style={{ borderRadius: '8px', padding: '0.5rem 0.75rem' }}
                  >
                    <i className="bi bi-trash"></i>
                  </Button>
                </div>
              </div>
            ))}
          </div>

          {/* Fixed Bottom Section */}
          <div className="cart-bottom-fixed" style={{ padding: '1rem', borderTop: '1px solid rgba(255, 255, 255, 0.2)', backgroundColor: 'rgba(255, 255, 255, 0.05)', height: '350px', display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
            {/* Discount Section */}
            <div>
              {cart.appliedDiscountCode && (
                <Alert className="py-3 border-0 mb-3" style={{ borderRadius: '12px', backgroundColor: 'rgba(255, 255, 255, 0.15)', color: 'white', border: '1px solid rgba(255, 255, 255, 0.3)' }}>
                  <div className="d-flex align-items-center">
                    <i className="bi bi-check-circle-fill me-2 text-white fs-5"></i>
                    <div>
                      <strong>Discount Applied!</strong>
                      <div className="small">Code: {cart.appliedDiscountCode}</div>
                    </div>
                  </div>
                </Alert>
              )}
              
              <Button
                variant="outline-light"
                size="lg"
                onClick={() => setShowDiscountForm(!showDiscountForm)}
                className="w-100 py-3 fw-semibold mb-3"
                style={{ borderRadius: '12px', borderWidth: '2px' }}
              >
                <i className="bi bi-tag me-2"></i>
                Apply Discount Code
              </Button>
              
              {showDiscountForm && (
                <Form onSubmit={handleApplyDiscount} className="mb-3">
                  <div className="d-flex gap-2">
                    <Form.Control
                      type="text"
                      placeholder="Enter discount code"
                      value={discountCode}
                      onChange={(e) => setDiscountCode(e.target.value)}
                      className="py-3 border-2"
                      style={{ borderRadius: '12px', fontSize: '1rem', backgroundColor: 'rgba(255, 255, 255, 0.9)' }}
                    />
                    <Button 
                      type="submit" 
                      variant="light" 
                      size="lg" 
                      disabled={isLoading}
                      className="py-3 px-4 fw-semibold"
                      style={{ borderRadius: '12px' }}
                    >
                      Apply
                    </Button>
                  </div>
                </Form>
              )}
            </div>

            {/* Summary */}
            <div className="p-3" style={{ backgroundColor: 'rgba(255, 255, 255, 0.15)', borderRadius: '12px', border: '1px solid rgba(255, 255, 255, 0.3)', boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)' }}>
              <h6 className="fw-semibold mb-3 text-white">Order Summary</h6>
              <div className="d-flex justify-content-between mb-2">
                <span className="text-white-50">Subtotal:</span>
                <span className="fw-semibold text-white">€{cart.totalAmount.toFixed(2)}</span>
              </div>
              {cart.discountAmount > 0 && (
                <div className="d-flex justify-content-between mb-2">
                  <span className="text-white-50">Discount:</span>
                  <span className="fw-semibold text-white">-€{cart.discountAmount.toFixed(2)}</span>
                </div>
              )}
              <div className="d-flex justify-content-between fw-bold fs-4 pt-2 border-top border-white-25">
                <span className="text-white">Total:</span>
                <span className="text-warning">€{cart.finalAmount.toFixed(2)}</span>
              </div>
            </div>

            {/* Actions */}
            <div className="d-flex gap-3">
              <Button
                variant="outline-light"
                onClick={() => {
                  onClearCart();
                  setShowDiscountForm(false);
                }}
                disabled={isLoading}
                className="flex-grow-1 py-3 fw-semibold"
                style={{ borderRadius: '12px', borderWidth: '2px' }}
              >
                <i className="bi bi-trash me-2"></i>
                Clear Cart
              </Button>
              <Button
                variant="warning"
                disabled={isLoading || cart.items.length === 0}
                className="flex-grow-1 py-3 fw-semibold"
                style={{ borderRadius: '12px' }}
              >
                <i className="bi bi-credit-card me-2"></i>
                Checkout
              </Button>
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default Cart; 