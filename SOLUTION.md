# Domus Challenge Solution

## Architecture Overview

### Technology Stack
- Java 21
- Spring Boot 3.4.5
- Spring WebFlux (Reactive Programming)
- In-memory caching with AtomicReference
- Lombok
- Swagger/OpenAPI
- JUnit 5 & Mockito

### Component Structure
```
com.domus.challenge
├── controller
│   └── DirectorController
├── service
│   ├── DirectorService
│   └── MovieCacheLoader
├── client
│   └── MovieClient
├── model
│   ├── Movie
│   └── MoviePageResponse
└── exception
    ├── GlobalExceptionHandler
    ├── ErrorResponse
    └── ErrorCode
```

## Design Decisions

### 1. Caching Strategy
The most significant design decision was implementing a caching strategy using an in-memory cache with scheduled refresh. This decision was based on several observations:

1. **Data Characteristics**:
   - Movie data is relatively static
   - Directors and their movie counts don't change frequently
   - The external API might have rate limits

2. **Performance Considerations**:
   - Multiple requests for the same threshold would require reprocessing all pages
   - Pagination handling is resource-intensive
   - Network calls to external API add latency

3. **Cache Implementation**:
   ```java
   @Service
   @RequiredArgsConstructor
   public class MovieCacheLoader {
       private final MovieClient movieClient;
       private final AtomicReference<List<Movie>> cachedMovies = new AtomicReference<>(Collections.emptyList());

       @PostConstruct
       public void loadOnStartup() {
           log.info("Loading movies at startup...");
           refreshMovieCache();
       }

       @Scheduled(cron = "0 0 3 * * ?")
       public void refreshMovieCache() {
           try {
               List<Movie> allMovies = movieClient.fetchAllMovies();
               cachedMovies.set(allMovies);
               log.info("Movie cache refreshed: {} movies loaded", allMovies.size());
           } catch (Exception e) {
               log.error("Failed to refresh movie cache", e);
           }
       }

       public List<Movie> getCachedMovies() {
           return cachedMovies.get();
       }
   }
   ```

4. **Cache Benefits**:
   - Reduced external API calls
   - Improved response times
   - Lower resource consumption
   - Better scalability
   - Automatic refresh at 3 AM daily
   - Thread-safe operations using AtomicReference

### 2. Reactive Programming
Chose WebFlux and reactive programming for:
- Non-blocking I/O operations
- Better resource utilization
- Scalability
- Modern approach to handling concurrent requests

### 3. Error Handling Strategy
Implemented a comprehensive error handling approach:

1. **Global Exception Handler**:
   - Centralized error handling
   - Consistent error responses
   - Proper HTTP status codes

2. **Error Categories**:
   - Invalid input (400)
   - External API errors (502)
   - Internal server errors (500)

3. **Error Response Format**:
   ```json
   {
     "timestamp": "2024-03-19T10:30:00",
     "status": 400,
     "error": "Bad Request",
     "code": "INVALID_INPUT",
     "message": "Invalid threshold value"
   }
   ```

### 4. Pagination Handling
Implemented a sequential pagination strategy:

1. **Page Processing**:
   - Sequential fetching of pages
   - Efficient resource utilization
   - Error handling per page

2. **Data Aggregation**:
   - In-memory collection of results
   - Efficient director counting
   - Memory-optimized processing

### 5. Testing Strategy
Implemented a comprehensive testing approach focusing on key components:

1. **Unit Tests**:
   - **DirectorService Tests**:
     - Testing threshold filtering
     - Handling empty/null movie lists
     - Sorting directors alphabetically
     - Edge cases with negative thresholds
     - Null director handling
   
   - **DirectorController Tests**:
     - Valid threshold parameter handling
     - Invalid threshold parameter validation
     - Missing threshold parameter handling
     - Response format validation
     - HTTP status code verification

2. **Integration Tests**:
   - **Application Context Tests**:
     - Spring context loading verification
     - Actuator health endpoint availability
     - Configuration property loading
     - WebFlux environment setup

3. **Test Coverage**:
   - **Happy Path Scenarios**:
     - Valid threshold returns correct directors
     - Proper sorting of results
     - Correct response format
   
   - **Error Scenarios**:
     - Invalid threshold format
     - Missing threshold parameter
     - Negative threshold values
     - Empty movie lists
     - Null directors in movies

4. **Test Configuration**:
   - Dedicated test profile (`application-test.yml`)
   - Mock external API endpoint
   - Configured logging levels
   - Random port allocation
   - Actuator endpoints configuration

## Performance Considerations

### 1. Cache Configuration
- Daily cache refresh at 3:00 AM
- In-memory cache using AtomicReference
- Thread-safe operations
- Automatic refresh on application startup

### 2. Memory Management
- Efficient data structures
- Proper resource cleanup
- Memory leak prevention
- Atomic operations for thread safety

### 3. Response Time Optimization
- Cached responses
- Parallel processing
- Efficient data structures
- Non-blocking I/O operations

## Security Considerations

1. **Input Validation**:
   - Threshold parameter validation
   - Type checking
   - Range validation

2. **Error Handling**:
   - Secure error messages
   - No sensitive data exposure
   - Proper logging

## Monitoring and Maintenance

1. **Actuator Endpoints**:
   - Health checks
   - Application metrics
   - Basic application status

2. **Logging**:
   - Structured logging
   - Error tracking
   - Performance monitoring
   - Cache refresh logging