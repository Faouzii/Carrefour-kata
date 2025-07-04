# Carrefour Discount MVP Frontend

This is the frontend for the Carrefour Discount MVP Kata. It is a modern React application styled with Bootstrap and custom Carrefour branding.

## 🛠️ Tech Stack
- React (with hooks)
- TypeScript
- Bootstrap 5
- Custom CSS for glass morphism and responsive design

## 📁 Folder Structure
- `src/components/` — UI components (Cart, ProductCard, Header, etc.)
- `src/hooks/` — Custom hooks for cart, products, navigation, and toasts
- `src/pages/` — Page-level components (Home, About)
- `src/services/` — API service for backend communication
- `src/types/` — TypeScript types

## 🚀 Running the Frontend
```bash
cd discount-mvp-frontend
npm install
npm start
```
The app will be available at [http://localhost:3000](http://localhost:3000)

## 🧪 Running Tests
```bash
npm test
```

## ✨ UI/UX Features
- Responsive, mobile-friendly layout
- Animated sliding cart panel with glass morphism
- Toast notifications for errors and actions
- Always-visible cart actions (clear, checkout)
- Product cards with add-to-cart and discount badge

## 🧩 Extensibility
- Easily add new UI components or hooks
- API service is centralized for easy backend changes
- Cart and product logic is encapsulated in hooks for reuse

## 📝 Notes
- The frontend enforces business rules (e.g., only one discount code at a time) in sync with the backend
- All discount/product logic is driven by backend data

See the root README for business rules, discount codes, and product eligibility.
