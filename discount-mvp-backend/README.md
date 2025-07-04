# Carrefour Discount MVP Backend

This is the backend for the Carrefour Discount MVP Kata. It is a Spring Boot application following hexagonal architecture, with extensible discount logic using the Strategy design pattern.

## 🏗️ Architecture
- **Hexagonal (Ports & Adapters)**: Clean separation between domain, application, and infrastructure.
- **Domain-Driven**: All business logic is in the domain layer.
- **Strategy Pattern**: Discount calculation uses the Strategy pattern, making it easy to add new discount types.

## 🛠️ Tech Stack
- Java 17
- Spring Boot 3
- In-memory repositories (easy to swap for a real DB)
- JUnit 5 for testing

## 📁 Key Folders
- `domain/` — Core business logic, entities, and discount strategies
- `application/` — Orchestrates business use cases
- `infrastructure/` — REST controllers, in-memory repositories

## 🚀 Running the Backend
```bash
cd discount-mvp-backend
./mvnw spring-boot:run
```
The backend will be available at [http://localhost:8080](http://localhost:8080)

## 🧪 Running Tests
```bash
./mvnw test
```

## 📚 API Endpoints
- `GET /api/v1/products` — List all products
- `GET /api/v1/cart` — Get the current cart
- `POST /api/v1/cart/items` — Add item to cart
- `PUT /api/v1/cart/items/{productId}` — Update item quantity
- `DELETE /api/v1/cart/items/{productId}` — Remove item
- `DELETE /api/v1/cart` — Clear cart
- `POST /api/v1/cart/discount` — Apply a discount code

## 🧩 Extending Discounts
Discounts are implemented using the **Strategy pattern**. To add a new discount type:
1. Implement the `DiscountCalculator` interface in `domain/discount/`.
2. Register your new calculator in the application config.

## 🏷️ Example Discount Strategies
- **Percentage** (e.g., 10% off)
- **Fixed Amount** (e.g., €0.50 off per item)

## 📝 Notes
- All business rules are enforced in the service/domain layer.
- Only one discount code can be applied to a cart at a time.
- Discount codes are product-specific and can have expiration dates.

See the root README for business rules and available discount codes. 