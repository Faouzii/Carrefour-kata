import React from 'react';
import Header from './Header';
import Footer from './Footer';
import ToastNotification from './ToastNotification';
import { ToastVariant } from '../hooks/useToast';
import { Page } from '../hooks/useNavigation';

interface LayoutProps {
  children: React.ReactNode;
  cartItemCount: number;
  onToggleCart: () => void;
  currentPage: Page;
  onNavigate: (page: Page) => void;
  toast: {
    show: boolean;
    message: string;
    variant: ToastVariant;
  };
  onCloseToast: () => void;
}

const Layout: React.FC<LayoutProps> = ({
  children,
  cartItemCount,
  onToggleCart,
  currentPage,
  onNavigate,
  toast,
  onCloseToast
}) => {
  return (
    <div className="App">
      <Header 
        cartItemCount={cartItemCount} 
        onToggleCart={onToggleCart}
        currentPage={currentPage}
        onNavigate={onNavigate}
      />
      
      {children}
      
      <Footer />
      
      <ToastNotification toast={toast} onClose={onCloseToast} />
    </div>
  );
};

export default Layout; 