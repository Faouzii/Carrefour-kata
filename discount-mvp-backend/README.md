# Discount MVP Backend

A Spring Boot application implementing a discount system with support for both in-memory and JPA database implementations.

## Features

- **Dual Database Support**: In-memory (default with sample data) or JPA with H2 database
- **Clean Architecture**: Domain-driven design with clear separation of concerns
- **Strategy Pattern**: Extensible discount calculation system supporting multiple discount types
- **RESTful API**: Complete CRUD operations for carts, products, and discount codes
- **Business Logic**: Discount calculation with percentage and fixed amount support
- **Comprehensive Testing**: Unit tests, repository tests, and controller tests

## Architecture

The application follows a clean architecture pattern with the following layers:

```
┌─────────────────────────────────────────────────────────────┐
│                    Web Layer (Controllers)                  │
├─────────────────────────────────────────────────────────────┤
│                  Application Layer (Services)               │
├─────────────────────────────────────────────────────────────┤
│                    Domain Layer (Business Logic)            │
├─────────────────────────────────────────────────────────────┤
│                Infrastructure Layer (Repositories)          │
└─────────────────────────────────────────────────────────────┘
```

### Domain Layer
- **Entities**: `Cart`, `CartItem`, `Product`, `DiscountCode`
- **Services**: `CartService`, `ProductDescriptionService`
- **Ports**: `CartRepository`, `ProductRepository`, `DiscountCodeRepository`

### Infrastructure Layer
- **In-Memory Implementation**: Fast, no persistence, perfect for development/testing
- **JPA Implementation**: Full database persistence with H2

## Database Configuration

### Default: In-Memory Database (with sample data)
The application uses in-memory implementations by default, which includes:
- **Sample Products**: 8 products (fruits, dairy, electronics)
- **Sample Discount Codes**: 4 discount codes with different types
- **Perfect for**: Development, testing, and quick prototyping

```properties
app.database.type=memory
```

### JPA with H2 Database
To use the JPA implementation with H2 database:

```properties
app.database.type=jpa
```

#### Running with JPA Profile
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=jpa
```

#### H2 Console
When using JPA, the H2 console is available at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## API Documentation

Interactive API documentation is available via Swagger UI:

- [Swagger UI](http://localhost:8080/swagger-ui.html)
- [OpenAPI JSON](http://localhost:8080/api-docs)

You can use Swagger UI to explore and test all endpoints directly from your browser.

## API Endpoints

### Products
- `GET /api/v1/products` - Get all products
- `GET /api/v1/products/{id}` - Get product by ID

### Carts
- `GET /api/v1/cart` - Get current cart
- `POST /api/v1/cart/items` - Add item to cart
- `PUT /api/v1/cart/items/{productId}` - Update item quantity
- `DELETE /api/v1/cart/items/{productId}` - Remove item from cart
- `POST /api/v1/cart/discount` - Apply discount code to cart
- `DELETE /api/v1/cart` - Clear cart

## Business Rules

### Discount Codes
- **Percentage Discount**: Reduces total by a percentage (e.g., 10% off)
- **Fixed Amount Discount**: Reduces total by a fixed amount (e.g., €5 off)
- **Product Eligibility**: Discounts can be limited to specific products
- **Expiration**: Discount codes have expiration dates
- **Extensible Design**: Uses Strategy pattern to easily add new discount types without modifying existing code

### Cart Operations
- Items can be added, updated, or removed
- Only one discount code can be applied per cart
- Discount calculations respect product eligibility rules

## Development

### Prerequisites
- Java 21+
- Maven 3.6+

### Running the Application

#### Default (In-Memory with sample data)
```bash
./mvnw spring-boot:run
```

#### With JPA Database
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=jpa
```

### Running Tests
```bash
./mvnw test
```

### Building the Application
```bash
./mvnw clean package
```

## Configuration Files

### Main Configuration
- `application.properties` - Default configuration (in-memory)
- `application-memory.properties` - In-memory profile configuration
- `application-jpa.properties` - JPA profile configuration

### Key Properties

#### Database Selection
```properties
# In-Memory (default)
app.database.type=memory

# JPA with H2
app.database.type=jpa
```

#### JPA Configuration
```properties
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
```

#### H2 Configuration
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## Testing Strategy

### Test Types
- **Unit Tests**: Business logic testing with mocked dependencies
- **Repository Tests**: Data access layer testing
- **Controller Tests**: Web layer testing with `@WebMvcTest`

### Test Coverage
- Service layer business logic
- Repository implementations
- Controller endpoints
- Exception handling
- Data validation

## Future Enhancements

### Possible Database Implementations
- **PostgreSQL**: Production-ready relational database
- **MongoDB**: Document-based storage
- **Redis**: Caching layer
- **MySQL**: Alternative relational database

### Additional Features
- **Authentication & Authorization**: User authentication and role-based access control
- **Audit Logging**: Track changes to entities
- **Caching**: Improve performance with Redis
- **Pagination**: Handle large datasets
- **Search**: Full-text search capabilities