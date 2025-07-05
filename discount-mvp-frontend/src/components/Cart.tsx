import React, { useState } from 'react';
import { Button, Form, Alert } from 'react-bootstrap';
import { Cart as CartType, CartItem } from '../types';
import './Cart.css';

interface CartProps {
  cart: CartType;
  onUpdateQuantity: (productId: string, quantity: number) => void;
  onRemoveItem: (productId: string) => void;
  onApplyDiscount: (discountCode: string) => Promise<{ success: boolean; message: string; }>;
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
  const [discountError, setDiscountError] = useState<string | null>(null);

  const handleApplyDiscount = async (e: React.FormEvent) => {
    e.preventDefault();
    if (discountCode.trim()) {
      const result = await onApplyDiscount(discountCode.trim());
      if (result.success) {
        setDiscountCode('');
        setShowDiscountForm(false);
        setDiscountError(null);
      } else {
        setDiscountError(result.message);
      }
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
    <div className="cart-container">
      {cart.items.length === 0 ? (
        <div className="cart-empty-container">
          <div>
            <div className="cart-empty-icon">
              <i className="bi bi-cart-x fs-2 text-muted"></i>
            </div>
            <h6 className="cart-empty-title">Your cart is empty</h6>
            <p className="cart-empty-text">Add some products to get started!</p>
          </div>
        </div>
      ) : (
        <>
          {/* Scrollable Cart Items */}
          <div className="cart-items-scrollable">
            {cart.items.map((item) => (
              <div key={item.productId} className="cart-item">
                <div className="d-flex justify-content-between align-items-start">
                  <div className="flex-grow-1 me-3">
                    <h6 className="cart-item-title">{item.product.name}</h6>
                    <p className="cart-item-description">{item.product.description}</p>
                    <div className="d-flex align-items-center justify-content-between">
                      <div className="d-flex align-items-center gap-3">
                        <Form.Label className="mb-0 fw-semibold small text-white">Qty:</Form.Label>
                        <Form.Control
                          type="number"
                          min="1"
                          max="99"
                          value={item.quantity}
                          onChange={(e) => handleQuantityChange(item, parseInt(e.target.value) || 0)}
                          className="cart-item-quantity-input border-2"
                        />
                      </div>
                      <div className="text-end">
                        <div className="cart-item-price">
                          €{(item.product.price * item.quantity).toFixed(2)}
                        </div>
                        <div className="cart-item-price-per-unit">
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
                    className="cart-item-remove-btn border-2"
                  >
                    <i className="bi bi-trash"></i>
                  </Button>
                </div>
              </div>
            ))}
          </div>

          {/* Fixed Bottom Section */}
          <div className="cart-bottom-fixed">
            {/* Discount Section */}
            <div>
              {cart.appliedDiscountCode && (
                <Alert className="cart-discount-alert">
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
                className="cart-discount-button"
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
                      onChange={(e) => { setDiscountCode(e.target.value); setDiscountError(null); }}
                      className="cart-discount-input border-2"
                    />
                    <Button 
                      type="submit" 
                      variant="light" 
                      size="lg" 
                      disabled={isLoading}
                      className="cart-discount-apply-btn"
                    >
                      Apply
                    </Button>
                  </div>
                  {discountError && (
                    <Alert variant="danger" className="cart-error-alert">
                      <i className="bi bi-exclamation-triangle me-2"></i>
                      {discountError}
                    </Alert>
                  )}
                </Form>
              )}
            </div>

            {/* Summary */}
            <div className="cart-summary">
              <h6 className="cart-summary-title">Order Summary</h6>
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
              <div className="d-flex justify-content-between fw-bold cart-summary-total">
                <span className="text-white">Total:</span>
                <span className="text-warning">€{cart.finalAmount.toFixed(2)}</span>
              </div>
            </div>

            {/* Actions */}
            <div className="cart-actions">
              <Button
                variant="outline-light"
                onClick={() => {
                  onClearCart();
                  setShowDiscountForm(false);
                }}
                disabled={isLoading}
                className="cart-action-btn cart-action-btn-outline"
              >
                <i className="bi bi-trash me-2"></i>
                Clear Cart
              </Button>
              <Button
                variant="warning"
                disabled={isLoading || cart.items.length === 0}
                className="cart-action-btn"
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