import React, { useState, useCallback, memo } from 'react';
import { Card, Button, Badge, Form } from 'react-bootstrap';
import { Product } from '../types';

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
    <Card className="h-100 shadow-sm carrefour-product-card">
      <Card.Body className="d-flex flex-column p-4">
        <div className="text-center mb-4">
          <div className="bg-light rounded-circle p-4 mb-3" style={{ width: '80px', height: '80px', margin: '0 auto', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <i className="bi bi-box-seam fs-2 text-primary"></i>
          </div>
          <Badge bg="success" className="mb-2 px-3 py-2">In Stock</Badge>
        </div>
        
        <Card.Title className="h5 mb-3 fw-bold">{product.name}</Card.Title>
        <Card.Text className="text-muted mb-4 flex-grow-1" style={{ lineHeight: '1.6' }}>
          {product.description || "Quality Carrefour product with sustainable sourcing and excellent value."}
        </Card.Text>
        
        <div className="mb-4">
          <h3 className="text-primary mb-0 fw-bold">€{product.price.toFixed(2)}</h3>
        </div>
        
        <div className="d-flex align-items-center gap-3 mb-4">
          <Form.Label className="mb-0 fw-semibold">Quantity:</Form.Label>
          <Form.Control
            type="number"
            min="1"
            max="99"
            value={quantity}
            onChange={(e) => setQuantity(Math.max(1, parseInt(e.target.value) || 1))}
            style={{ 
              width: '100px', 
              height: '45px',
              fontSize: '1.1rem',
              fontWeight: '600',
              textAlign: 'center'
            }}
            className="border-2"
          />
        </div>
        
        <Button
          variant="primary"
          onClick={handleAddToCart}
          disabled={isLoading}
          className="w-100 py-3 fw-semibold"
          style={{ fontSize: '1.1rem', borderRadius: '12px' }}
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