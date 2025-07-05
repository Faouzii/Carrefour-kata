import React, { useState, useCallback, memo } from 'react';
import { Card, Button, Badge, Form } from 'react-bootstrap';
import { Product } from '../types';
import './ProductCard.css';

interface ProductCardProps {
  product: Product;
  onAddToCart: (productId: string, quantity: number) => void;
  isLoading?: boolean;
}

const ProductCard: React.FC<ProductCardProps> = memo(({ product, onAddToCart, isLoading = false }) => {
  const [quantity, setQuantity] = useState(1);

  const handleAddToCart = useCallback(() => {
    if (quantity > 0) {
      onAddToCart(product.id, quantity);
      setQuantity(1);
    }
  }, [quantity, product.id, onAddToCart]);

  return (
    <Card className="product-card">
      <Card.Body className="product-card-body">
        <div className="text-center mb-3">
          <div className="product-icon-container">
            <i className="bi bi-box-seam fs-4 text-primary"></i>
          </div>
          <Badge bg="success" className="product-badge">In Stock</Badge>
        </div>
        
        <Card.Title className="product-title">{product.name}</Card.Title>
        <Card.Text className="product-description">
          {product.description || "Quality Carrefour product with sustainable sourcing and excellent value."}
        </Card.Text>
        
        <div className="mb-3">
          <h4 className="product-price">€{product.price.toFixed(2)}</h4>
        </div>
        
        <div className="product-quantity-container">
          <Form.Label className="product-quantity-label">Qty:</Form.Label>
          <Form.Control
            type="number"
            min="1"
            max="99"
            value={quantity}
            onChange={(e) => setQuantity(Math.max(1, parseInt(e.target.value) || 1))}
            className="product-quantity-input border-2"
          />
        </div>
        
        <Button
          variant="primary"
          onClick={handleAddToCart}
          disabled={isLoading}
          className="product-add-button"
        >
          {isLoading ? (
            <>
              <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
              Adding...
            </>
          ) : (
            <>
              <i className="bi bi-cart-plus me-2 fs-5"></i>
              Add to Cart
            </>
          )}
        </Button>
      </Card.Body>
    </Card>
  );
});

ProductCard.displayName = 'ProductCard';

export default ProductCard; 