import React from 'react';
import { Toast, ToastContainer } from 'react-bootstrap';
import { ToastVariant } from '../hooks/useToast';

interface ToastNotificationProps {
  toast: {
    show: boolean;
    message: string;
    variant: ToastVariant;
  };
  onClose: () => void;
}

const ToastNotification: React.FC<ToastNotificationProps> = ({ toast, onClose }) => {
  return (
    <ToastContainer
      position="bottom-start"
      className="p-3"
      style={{ position: 'fixed', zIndex: 9999, left: 0, right: 'unset', bottom: 0 }}
    >
      <Toast
        show={toast.show}
        onClose={onClose}
        delay={3000}
        autohide
        bg={toast.variant}
      >
        <Toast.Header closeButton>
          <strong className="me-auto">
            {toast.variant === 'success' ? 'Success' : 'Error'}
          </strong>
        </Toast.Header>
        <Toast.Body className={toast.variant === 'success' ? 'text-white' : ''}>
          {toast.message}
        </Toast.Body>
      </Toast>
    </ToastContainer>
  );
};

export default ToastNotification; 