# Shopping App

Shopping App is a Spring Boot-based online shopping application that provides a REST API for managing products. It uses Java 21, Spring Boot, Spring Data JPA, and PostgreSQL, and includes Docker support for easy deployment.

## Features

- Product CRUD operations
- REST API built with Spring MVC
- JPA-based persistence with PostgreSQL
- Bean validation for request payloads
- Actuator support for monitoring
- Docker-ready setup
- Maven-based project build
- Pagination support for product listings
- Partial updates with PATCH for product modifications
- Global exception handling for consistent API errors

## Tech Stack

- Java 21
- Spring Boot 4.1.1
- Maven
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Docker

## Project Structure

```text
shopping-app/
├── .mvn/
│   └── wrapper/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── online/
│   │   │           └── shopping_app/
│   │   │               ├── controller/           # REST controllers
│   │   │               ├── dto/                  # Request/response DTOs
│   │   │               ├── exception/            # Global and custom exceptions & handlers
│   │   │               ├── model/                # JPA entities
│   │   │               ├── repository/           # Spring Data JPA repositories
│   │   │               ├── service/              # Business logic
│   │   │               └── ShoppingAppApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── online/
│                   └── shopping_app/
│                      └── ShoppingAppApplicationTests.java
├── .dockerignore
├── .env.sample
├── .gitattributes
├── .gitignore
├── Dockerfile
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Prerequisites

Before running this project, make sure the following are installed:

- Java 21
- Maven
- PostgreSQL
- Docker (optional, for containerized deployment)
  

## Database Setup and configuration

### Install PostgreSQL
1. Download PostgreSQL from the official website and start the database service.
2. Create the database:

   ```sql
   CREATE DATABASE myapp_db;
   ```

3. Configure the database connection in `src/main/resources/application.properties`:

   ```properties
   server.port=8081
   spring.application.name=shopping-app
   
   spring.datasource.url=jdbc:postgresql://localhost:5432/myapp_db
   spring.datasource.username=${DB_USERNAME}
   spring.datasource.password=${DB_PASSWORD}

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=false
   ```

### Credentials
#### Option 1 : For Docker

Create a `.env` file using the sample file in the project folder `.env.sample`

Then update your database credentials in the below format in your `.env` to match your database setup:

```dotenv
DB_URL=jdbc:postgresql://localhost:5432/your_database
DB_USERNAME=username  #database username
DB_PASSWORD=password  #database password
```

Thus the application will pick the values from `.env`.

#### Option 2 : For Eclipse
1. Go to eclipse
2. Run -> Run as configurations -> Under 'Environment' -> Add the values
<img width="897" height="391" alt="image" src="https://github.com/user-attachments/assets/ed16c04a-7546-4761-b784-05009acde36a" />

## Running the Application

### Option 1: Run with Maven

On Windows:

```bash
cd shopping-app
mvn clean install
mvn spring-boot:run
```

The application will start on:

```text
http://localhost:8081
```

---

### Option 2: Run with Docker

Build the Docker image:

```bash
cd shopping-app
docker build -t shopping-app .
```
Run the container:

```bash
docker run -p 8081:8081 shopping-app
```

### Option 3: Run in Eclipse IDE

1. Import project in eclipse
2. Expand the folder, you will see ShoppingApplication.java
3. Right click on ShoppingApplication.java -> Run as -> Java Application
   
## API Endpoints

Base URL:

```text
http://localhost:8081
```

### Product API

```text
GET    ->  /api/products/getAllProducts
GET    ->  /api/products/getProductById/{id}
POST   ->  /api/products/addProduct
PUT    ->  /api/products/updateProduct/{id}
PATCH  ->  /api/products/updateProduct/{id}
DELETE ->  /api/products/deleteProduct/{id}
```

## Sample Requests

### Get all products

```http
GET /api/products/getAllProducts?page=0&size=10
```

### Get product by ID

```http
GET /api/products/getProductById/1
```

### Add a product

```http
POST /api/products/addProduct
Content-Type: application/json
```

Example body:

```json
{
  "name": "Laptop",
  "description": "Gaming laptop",
  "price": 1200.50,
  "stock": 10
}
```

### Update a product

```http
PUT /api/products/updateProduct/1
Content-Type: application/json
```

Example body:

```json
{
  "name": "Updated Laptop",
  "description": "Updated gaming laptop",
  "price": 1350.00,
  "stock": 15
}
```

### Partial update a product

```http
PATCH /api/products/updateProduct/1
Content-Type: application/json
```

Example body:

```json
{
  "price": 1299.99,
  "stock": 12
}
```

### Delete a product

```http
DELETE /api/products/deleteProduct/1
```

## Project Architecture

The project follows a standard Spring Boot layered architecture:

- `controller` — exposes REST endpoints
- `service` — contains business logic
- `repository` — handles data access using Spring Data JPA
- `model` — defines entity classes
- `dto` — request/response transfer objects to decouple API models from persistence
- `exception` — custom exceptions and global exception handlers
- `resources` — contains application configuration

## Recent changes (since 2026-10-10)

- 2026-10-12 — Added a global exception handler to return consistent error responses and HTTP status codes.
- 2026-10-13 — Introduced request and response DTOs for Product to decouple API models from persistence.
- 2026-10-15 — Added pagination for product listing endpoints and updated the project README to match the latest structure.
- 2026-10-18 — Added support for HTTP PATCH on Product to allow partial updates of product fields.

## Notes

- The application uses JPA automatic schema updates via:
  ```properties
  spring.jpa.hibernate.ddl-auto=update
  ```
- Validation is enabled for incoming request payloads.
- The project is configured for PostgreSQL, so a database must be available before running the app.
- Pagination and partial update support are included in the latest API updates.

## License

This project does not currently declare a specific license.
