import React from 'react';
import { Container, Row, Col, Nav } from 'react-bootstrap';
import { Page } from '../hooks/useNavigation';
import './Header.css';

interface HeaderProps {
  cartItemCount: number;
  onToggleCart: () => void;
  currentPage: Page;
  onNavigate: (page: Page) => void;
}

const Header: React.FC<HeaderProps> = ({ 
  cartItemCount, 
  onToggleCart, 
  currentPage, 
  onNavigate 
}) => {
  return (
    <div className="carrefour-header">
      <Container>
        <Row className="align-items-center">
          <Col md={6}>
            <div className="text-start">
              <div className="carrefour-logo">
                <img 
                  src="/carrefour_logo.png" 
                  alt="Carrefour Logo" 
                  className="me-3"
                />
                CARREFOUR
              </div>
              <div className="carrefour-slogan">
                 The best in choice and quality, at the best price
              </div>
            </div>
          </Col>
          <Col md={3}>
            <Nav className="justify-content-center">
              <Nav.Item>
                <Nav.Link 
                  className={`text-white ${currentPage === 'home' ? 'fw-bold' : ''}`}
                  onClick={() => onNavigate('home')}
                  style={{ cursor: 'pointer' }}
                >
                  <i className="bi bi-house me-1"></i>
                  Home
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link 
                  className={`text-white ${currentPage === 'about' ? 'fw-bold' : ''}`}
                  onClick={() => onNavigate('about')}
                  style={{ cursor: 'pointer' }}
                >
                  <i className="bi bi-info-circle me-1"></i>
                  About
                </Nav.Link>
              </Nav.Item>
            </Nav>
          </Col>
          <Col md={3} className="text-center text-md-end">
            {currentPage === 'home' && cartItemCount > 0 && (
              <button 
                className="cart-nav-btn"
                onClick={onToggleCart}
                title="Toggle Cart"
              >
                <i className="bi bi-cart3 me-2"></i>
                Cart
                <span className="cart-nav-badge">
                  {cartItemCount}
                </span>
              </button>
            )}
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default Header; 