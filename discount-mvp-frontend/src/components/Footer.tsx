import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';

const Footer: React.FC = () => {
  return (
    <div className="carrefour-footer">
      <Container>
        <Row>
          <Col md={4}>
            <h5>About Carrefour</h5>
            <p className="small">Leading retailer committed to quality, sustainability, and customer satisfaction.</p>
          </Col>
          <Col md={4}>
            <h5>Customer Service</h5>
            <p className="small">24/7 support available for all your shopping needs.</p>
          </Col>
          <Col md={4}>
            <h5>Follow Us</h5>
            <div className="d-flex gap-2">
              <a href="#" className="me-2"><i className="bi bi-facebook"></i></a>
              <a href="#" className="me-2"><i className="bi bi-twitter"></i></a>
              <a href="#" className="me-2"><i className="bi bi-instagram"></i></a>
              <a href="#" className="me-2"><i className="bi bi-linkedin"></i></a>
            </div>
          </Col>
        </Row>
        <Row className="mt-3">
          <Col>
            <hr className="border-light" />
            <p className="text-center small mb-0">
              © 2015 Carrefour. All rights reserved. | 
              <a href="#" className="ms-2">Privacy Policy</a> | 
              <a href="#" className="ms-2">Terms of Service</a>
            </p>
            <p className="text-center small mt-2 mb-0">
              Made with <span style={{color: 'var(--carrefour-red)'}}>♥</span> by Faouzi
            </p>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default Footer; 