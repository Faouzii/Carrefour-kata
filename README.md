# Carrefour Discount MVP — Business Rules & Demo Notes

This project demonstrates a shopping cart with discount code functionality for Carrefour, focusing on clear business rules and extensible discount logic.

## 🛒 Business Rules
- **One discount per cart:** Only one discount code can be applied at a time. Applying a new code replaces the previous one.
- **Product eligibility:**
  - Most discounts apply only to food products (see below).
  - Electronics are excluded from most discounts.
  - Some codes are product-specific (see table).
- **Discount code validation:**
  - Invalid codes show an error 
  - Discounts may have expiration dates.
- **Discount calculation:**
  - The backend uses the Strategy design pattern, making it easy to add new discount types.

## 🏷️ Discount Codes
| Code         | Type         | Value         | Applies To         | Description                       |
|--------------|--------------|--------------|--------------------|-----------------------------------|
| `PERC10`     | Percentage   | 10% off      | Food products      | 10% off all food items            |
| `FIXED5`     | Fixed Amount | €0.50/item   | Food products      | €0.50 off per food item           |
| `PERC20`     | Percentage   | 20% off      | Bananas, Oranges   | 20% off bananas and oranges       |
| `FIXED10`    | Fixed Amount | €1.00/item   | Bread, Milk        | €1.00 off bread and milk          |
| `WELCOME`    | Percentage   | 5% off       | All products       | 5% off your first order           |

## 🥗 Eligible Products
**Food Products (eligible for most discounts):**
- Fresh Apples (€2.50)
- Organic Bananas (€1.80)
- Sweet Oranges (€3.20)
- Fresh Milk (€1.50)
- Whole Grain Bread (€2.00)

**Electronics (not eligible for most discounts):**
- Smartphone (€299.99)
- Laptop (€899.99)
- Wireless Headphones (€89.99)

## 📝 Demo Notes
- **Static product data:** For simplicity, products are hardcoded in the backend for this demo. In a real-world scenario, products would be managed by a dedicated microservice.
- **Extensible discounts:** The discount logic is designed for easy extension (see backend README for details).
- **See backend and frontend READMEs** for setup, API, and technical details.

## 💡 Example: Applying a Discount
Suppose your cart contains:
- 2 × Fresh Apples (€2.50 each)
- 1 × Organic Bananas (€1.80)

Apply `PERC10` (10% off food):
- Subtotal: €6.80
- Discount: €0.68
- **Total after discount:** €6.12

Apply `FIXED5` (€0.50 off per food item):
- Discount: €1.50
- **Total after discount:** €5.30 