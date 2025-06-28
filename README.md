# Online Tea Shop Microservices

A microservices-based e-commerce platform for tea, coffee, and food products built with Spring Boot.

## Project Structure

The project consists of the following microservices:

1. **Product Service** - Manages product catalog (tea, coffee, food)
2. **Order Service** - Handles order processing and management
3. **User Service** - Manages user accounts and authentication
4. **Cart Service** - Handles shopping cart functionality
5. **Payment Service** - Processes payments
6. **API Gateway** - Routes requests to appropriate services
7. **Service Registry** - Service discovery and registration
8. **Config Server** - Centralized configuration management

## Technology Stack

- Java 17
- Spring Boot 3.2.3
- Spring Cloud 2023.0.0
- Spring Data JPA
- PostgreSQL
- Maven
- Docker (for containerization)

## Prerequisites

- JDK 17 or higher
- Maven 3.6 or higher
- Docker and Docker Compose
- PostgreSQL

## Getting Started

1. Clone the repository
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Start the services using Docker Compose:
   ```bash
   docker-compose up -d
   ```

## Service Ports

- Config Server: 8888
- Service Registry: 8761
- API Gateway: 8080
- Product Service: 8081
- Order Service: 8082
- User Service: 8083
- Cart Service: 8084
- Payment Service: 8085

## API Documentation

API documentation will be available at:
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Gateway: http://localhost:8080

## Development

Each microservice is a separate Maven module with its own configuration and dependencies. The parent `pom.xml` manages common dependencies and configurations.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request 