import { useState, useCallback } from 'react';

export type Page = 'home' | 'about';

export const useNavigation = () => {
  const [currentPage, setCurrentPage] = useState<Page>('home');

  const navigateTo = useCallback((page: Page) => {
    setCurrentPage(page);
  }, []);

  const isCurrentPage = useCallback((page: Page) => {
    return currentPage === page;
  }, [currentPage]);

  return {
    currentPage,
    navigateTo,
    isCurrentPage
  };
}; 