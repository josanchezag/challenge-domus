# Challenge Domus

## Description
This project is a REST API implementation that allows retrieving movie directors based on a movie count threshold.

## Technologies Used
- Java 21
- Spring Boot 3.4.5
- Spring WebFlux
- Caffeine Cache
- Lombok
- Swagger/OpenAPI
- JUnit 5
- Mockito

## Requirements
- Java 21 or higher
- Maven 3.8.x or higher

## Configuration
1. Clone the repository
2. Configure environment variables:
   ```bash
   EXTERNAL_API_URL=https://api.example.com/movies
   EXTERNAL_API_TIMEOUT=5000
   EXTERNAL_API_RETRY_ATTEMPTS=3
   EXTERNAL_API_RETRY_DELAY=1000
   ```

## Execution
```bash
mvn spring-boot:run
```

## Endpoints
- `GET /api/directors?threshold=X`: Get directors with more than X movies
- `GET /actuator/health`: Application status
- `GET /swagger-ui.html`: API documentation

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── domus/
│   │           └── challenge/
│   │               ├── controller/
│   │               ├── service/
│   │               ├── client/
│   │               ├── model/
│   │               └── exception/
│   └── resources/
│       └── application.yml
└── test/
    └── java/
        └── com/
            └── domus/
                └── challenge/
                    ├── controller/
                    ├── service/
                    └── client/
```

## Features
- Reactive programming with WebFlux
- Caching with Caffeine
- Global error handling
- Swagger documentation
- Unit and integration tests
- Environment-based configuration
- Actuator monitoring

## License
This project is under the MIT License.

# Domus Directors API

A Spring WebFlux-based REST API that returns directors who have directed more movies than a specified threshold.

## 🧠 Problem Statement

Given a paginated public API of movies released after 2010, implement an endpoint that:

- Calls the external movie API,
- Counts how many movies each director has directed,
- Filters the directors whose movie count is greater than a given threshold,
- Returns the result sorted alphabetically.

## 📦 API Endpoint

**GET** `/api/directors?threshold=X`

**Example:**
