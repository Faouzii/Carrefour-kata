import React from 'react';
import { Container, Alert } from 'react-bootstrap';

interface ErrorDisplayProps {
  error: string;
}

const ErrorDisplay: React.FC<ErrorDisplayProps> = ({ error }) => {
  return (
    <Container className="mt-4">
      <Alert variant="danger">
        <Alert.Heading>Error</Alert.Heading>
        <p>{error}</p>
        <hr />
        <p className="mb-0">
          Make sure the backend server is running on <code>http://localhost:8080</code>
        </p>
      </Alert>
    </Container>
  );
};

export default ErrorDisplay; 