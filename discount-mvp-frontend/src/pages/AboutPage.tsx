import React from 'react';
import { Container, Row, Col, Card } from 'react-bootstrap';

const AboutPage: React.FC = () => {
  return (
    <Container className="py-5">
      <Row>
        <Col>
          <h1 className="text-center mb-5">
            <i className="bi bi-info-circle me-3"></i>
            About Carrefour Shopping Experience
          </h1>
        </Col>
      </Row>
      
      <Row className="g-4">
        <Col md={6}>
          <Card className="h-100 shadow-sm">
            <Card.Body className="p-4">
              <Card.Title className="h4 mb-3">
                <i className="bi bi-award text-primary me-2"></i>
                Our Mission
              </Card.Title>
              <Card.Text>
                At Carrefour, we are committed to providing quality products with sustainable sourcing 
                and excellent value. Our shopping experience is designed to be seamless, modern, and 
                user-friendly, reflecting our commitment to customer satisfaction.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
        
        <Col md={6}>
          <Card className="h-100 shadow-sm">
            <Card.Body className="p-4">
              <Card.Title className="h4 mb-3">
                <i className="bi bi-gear text-primary me-2"></i>
                Technology Stack
              </Card.Title>
              <Card.Text>
                This application is built with modern technologies including React, TypeScript, 
                and Spring Boot. It demonstrates clean architecture principles, proper separation 
                of concerns, and scalable design patterns.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
        
        <Col md={6}>
          <Card className="h-100 shadow-sm">
            <Card.Body className="p-4">
              <Card.Title className="h4 mb-3">
                <i className="bi bi-lightning text-primary me-2"></i>
                Features
              </Card.Title>
              <ul className="list-unstyled">
                <li><i className="bi bi-check-circle text-success me-2"></i>Product browsing</li>
                <li><i className="bi bi-check-circle text-success me-2"></i>Shopping cart management</li>
                <li><i className="bi bi-check-circle text-success me-2"></i>Discount code application</li>
                <li><i className="bi bi-check-circle text-success me-2"></i>Real-time updates</li>
                <li><i className="bi bi-check-circle text-success me-2"></i>Responsive design</li>
              </ul>
            </Card.Body>
          </Card>
        </Col>
        
        <Col md={6}>
          <Card className="h-100 shadow-sm">
            <Card.Body className="p-4">
              <Card.Title className="h4 mb-3">
                <i className="bi bi-code-slash text-primary me-2"></i>
                Architecture
              </Card.Title>
              <Card.Text>
                Built with hexagonal architecture principles, featuring clean separation between 
                domain logic, application services, and infrastructure. The frontend follows 
                modern React patterns with custom hooks and component composition.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default AboutPage; 