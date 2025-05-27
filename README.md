# Challenge Domus

## Description
This project is a REST API implementation that allows retrieving movie directors based on a movie count threshold. The API fetches movie data from an external source, processes it to count movies per director, and returns directors who have directed more movies than a specified threshold.

## Technologies Used
- Java 21
- Spring Boot 3.4.5
- Spring WebFlux (Reactive Programming)
- In-memory caching with AtomicReference
- Lombok
- Swagger/OpenAPI
- JUnit 5 & Mockito

## Requirements
- Java 21 or higher
- Maven 3.8.x or higher

## Configuration
1. Clone the repository
2. Configure the external API endpoint in `application.yml`:
   ```yaml
   external:
     api:
       endpoint: https://challenge.iugolabs.com/api/movies/search?page=<pageNumber>
   ```

## Execution
```bash
mvn spring-boot:run
```

## Endpoints
- `GET /api/directors?threshold=X`: Get directors with more than X movies
- `GET /actuator/health`: Application health status
- `GET /swagger-ui.html`: API documentation

## Features
- Reactive programming with WebFlux
- In-memory caching with daily refresh at 3 AM
- Global error handling
- Swagger documentation
- Unit and integration tests
- Environment-based configuration
- Actuator monitoring

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── domus/
│   │           └── challenge/
│   │               ├── controller/
│   │               │   └── DirectorController
│   │               ├── service/
│   │               │   ├── DirectorService
│   │               │   └── MovieCacheLoader
│   │               ├── client/
│   │               │   └── MovieClient
│   │               ├── model/
│   │               │   ├── Movie
│   │               │   └── MoviePageResponse
│   │               └── exception/
│   │                   ├── GlobalExceptionHandler
│   │                   ├── ErrorResponse
│   │                   └── ErrorCode
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

## API Response Format
```json
{
  "directors": [
    "Director Name 1",
    "Director Name 2"
  ]
}
```

## Error Response Format
```json
{
  "timestamp": "2024-03-19T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "code": "INVALID_INPUT",
  "message": "Invalid threshold value"
}
```

## Testing
The project includes comprehensive test coverage:
- Unit tests for service and controller layers
- Integration tests for application context
- Test configuration with dedicated profile
- Mock external API for testing

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
