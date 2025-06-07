# QuickShelf

[![Java](https://img.shields.io/badge/Java-23-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Version](https://img.shields.io/badge/Version-1.0.0-success.svg)](https://github.com/yourusername/quickshelf/releases)

> 📦 A modern, lightweight inventory management API for your product catalog

QuickShelf is a Java Spring Boot RESTful API server that allows users to perform CRUD (Create, Read, Update, Delete) operations on product entries. It's designed to be simple, efficient, and easy to integrate with any frontend application.

## ✨ Features

- **Complete CRUD Operations** - Create, read, update, and delete product entries
- **Robust Validation** - Input validation for all product attributes
- **Error Handling** - Comprehensive error handling with appropriate HTTP status codes
- **API Documentation** - Interactive API documentation with Swagger/OpenAPI
- **In-memory Database** - H2 database for easy testing and development
- **Comprehensive Testing** - Unit and integration tests with high coverage

## 🛠️ Technologies

- Java 23
- Spring Boot 3.2.3
- Spring Data JPA
- H2 Database (in-memory)
- Gradle
- SpringDoc OpenAPI (Swagger)
- JUnit 5

## 📋 Prerequisites

- Java 23 or higher
- Gradle 8.x (or use the included Gradle wrapper)

## 🚀 Getting Started

### Clone the Repository

```bash
git clone https://github.com/yourusername/quickshelf.git
cd quickshelf
```

### Run the Application

You can run the application using the provided start script:

```bash
./start.sh
```

Alternatively, you can use Gradle directly:

```bash
./gradlew bootRun
```

The application will start on port 8080 with context path `/api`.

## 🔌 API Endpoints

- `POST /api/products` - Create a new product
- `GET /api/products` - Retrieve a list of all products
- `GET /api/products/{product_id}` - Retrieve a specific product by its ID
- `PUT /api/products/{product_id}` - Update an existing product
- `DELETE /api/products/{product_id}` - Delete a product

## 📚 API Documentation

Once the application is running, you can access the Swagger UI at:

```
http://localhost:8080/api/swagger-ui.html
```

The OpenAPI specification is available at:

```
http://localhost:8080/api/api-docs
```

## 💾 Database Console

The H2 in-memory database console is available at:

```
http://localhost:8080/api/h2-console
```

Connection details:
- JDBC URL: `jdbc:h2:mem:quickshelfdb`
- Username: `sa`
- Password: `password`

## 📊 Sample Data

The application is pre-loaded with sample product data for testing purposes. You can view these products by accessing the `/api/products` endpoint.

## 🧪 Running Tests

You can run the tests using the provided test script:

```bash
./run-tests.sh
```

Alternatively, you can use Gradle directly:

```bash
./gradlew test
```

## 📁 Project Structure

```
quickshelf/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── quickshelf/
│   │   │           ├── config/       # Configuration classes
│   │   │           ├── controller/   # REST controllers
│   │   │           ├── dto/          # Data Transfer Objects
│   │   │           ├── exception/    # Custom exceptions
│   │   │           ├── model/        # Entity classes
│   │   │           ├── repository/   # Data access layer
│   │   │           ├── service/      # Business logic layer
│   │   │           └── QuickShelfApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── data.sql              # Sample data
│   │       └── schema.sql            # Database schema
│   └── test/
│       └── java/
│           └── com/
│               └── quickshelf/
│                   ├── controller/   # Controller tests
│                   ├── integration/  # Integration tests
│                   ├── model/        # Model tests
│                   ├── repository/   # Repository tests
│                   ├── service/      # Service tests
│                   └── QuickShelfApplicationTests.java
├── build.gradle
├── settings.gradle
├── start.sh                          # Script to start the application
├── run-tests.sh                      # Script to run tests
└── README.md
```

## 📖 Documentation

- API documentation is available through Swagger UI
- Javadoc documentation is available for all classes and methods
- A Postman collection is provided in the `postman` directory for manual testing

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.