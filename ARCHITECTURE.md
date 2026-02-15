# System Architecture Documentation

## Overview
This document describes the architecture and design decisions for the Web Scraper with Trie-Based Search Application.

## Architectural Layers

### 1. Presentation Layer (Controller)
- **ScrapingController**: REST API endpoints
- Handles HTTP requests/responses
- Input validation using Bean Validation
- Exception handling via @RestControllerAdvice

### 2. Service Layer
- **ScrapingService**: Orchestrates scraping operations
- **WebScraperService**: Performs actual web scraping
- Business logic and transaction management
- Async job execution

### 3. Data Access Layer
- **ScrapingJobRepository**: Manages job persistence
- **ScrapedDataRepository**: Manages scraped content
- Spring Data JPA abstractions
- Query optimization

### 4. Data Structures
- **Trie**: Efficient prefix-based search
- Thread-safe implementation
- O(m) search complexity

## Design Patterns

### 1. Repository Pattern
```
Controller -> Service -> Repository -> Database
```
**Benefits:**
- Decouples business logic from data access
- Easy to test with mocks
- Flexible data source changes

### 2. DTO Pattern
**Purpose:** Transfer data between layers without exposing entities

**DTOs:**
- ScrapeRequest
- ScrapeResponse
- SearchRequest
- SearchResponse
- JobStatusResponse

### 3. Builder Pattern
**Usage:** Entity and DTO construction
```java
ScrapedData.builder()
    .jobId(jobId)
    .url(url)
    .content(content)
    .build();
```

### 4. Singleton Pattern
**Implementation:** Trie instance managed by Spring
- Single instance across application
- Thread-safe operations

### 5. Strategy Pattern
**Future Enhancement:** Different scraping strategies
- BasicScrapingStrategy
- JavaScriptScrapingStrategy
- APIBasedScrapingStrategy

## Data Flow

### Scraping Flow
```
1. Client -> POST /api/v1/scrape
2. ScrapingController validates request
3. ScrapingService creates job
4. Job saved with PENDING status
5. Async executor picks up job
6. WebScraperService scrapes each URL
7. ScrapedData saved to database
8. Keywords indexed in Trie
9. Job status updated to COMPLETED
```

### Search Flow
```
1. Client -> POST /api/v1/search
2. Trie finds words matching prefix
3. Database queries for scraped data
4. Results aggregated and limited
5. Response returned to client
```

## Component Interactions

```
┌─────────────┐
│   Client    │
└──────┬──────┘
       │ HTTP
       ▼
┌─────────────────────┐
│  ScrapingController │
└──────┬──────────────┘
       │
       ▼
┌─────────────────┐        ┌──────────────┐
│ ScrapingService │◄──────►│     Trie     │
└──────┬──────────┘        └──────────────┘
       │
       ├─────────────────┐
       │                 │
       ▼                 ▼
┌──────────────┐  ┌────────────────┐
│ WebScraper   │  │  Repositories  │
│   Service    │  └────────┬───────┘
└──────────────┘           │
                           ▼
                    ┌──────────────┐
                    │   Database   │
                    └──────────────┘
```

## Thread Management

### Async Configuration
```java
ThreadPoolTaskExecutor:
- Core Pool Size: 5
- Max Pool Size: 10
- Queue Capacity: 100
```

### Scheduled Tasks
```java
@Scheduled(fixedRate = 60000)
- Checks pending jobs every minute
- Executes jobs when due
```

## Database Schema

### ScrapingJob Table
```sql
CREATE TABLE scraping_job (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    job_id VARCHAR(255) UNIQUE NOT NULL,
    status VARCHAR(50) NOT NULL,
    scheduled_at TIMESTAMP,
    started_at TIMESTAMP,
    finished_at TIMESTAMP,
    data_size BIGINT,
    error_message TEXT
);
```

### ScrapedData Table
```sql
CREATE TABLE scraped_data (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    job_id VARCHAR(255) NOT NULL,
    url VARCHAR(2048) NOT NULL,
    content TEXT,
    matched_content TEXT,
    keyword VARCHAR(500),
    timestamp TIMESTAMP NOT NULL,
    data_size BIGINT,
    INDEX idx_job_id (job_id),
    INDEX idx_url (url)
);
```

## Error Handling Strategy

### Exception Hierarchy
```
RuntimeException
├── JobNotFoundException
└── ScrapingException
```

### Global Exception Handler
- Catches all exceptions
- Returns proper HTTP status codes
- Provides meaningful error messages
- Logs errors for debugging

## Security Considerations

### Current Implementation
- Input validation
- SQL injection prevention (JPA)
- XSS prevention (no HTML rendering)

### Recommended Enhancements
- Rate limiting
- Authentication (JWT)
- Authorization (Role-based)
- HTTPS enforcement
- API key management

## Performance Optimization

### Current Optimizations
1. **Async Processing**: Non-blocking scraping
2. **Database Indexes**: Fast queries
3. **Trie Structure**: O(m) search time
4. **Connection Pooling**: Efficient DB access
5. **Content Truncation**: Limited storage

### Future Improvements
1. **Caching** (Redis): Frequently accessed data
2. **Message Queue** (RabbitMQ): Job distribution
3. **Pagination**: Large result sets
4. **Batch Processing**: Multiple URLs
5. **Database Sharding**: Horizontal scaling

## Scalability

### Vertical Scaling
- Increase thread pool size
- More memory for Trie
- Faster database server

### Horizontal Scaling
- Load balancer for API servers
- Distributed job queue
- Shared cache (Redis)
- Database replication
- Microservices architecture

## Monitoring & Logging

### Logging Strategy
```
- INFO: Normal operations
- DEBUG: Detailed execution flow
- ERROR: Exceptions and failures
- WARN: Potential issues
```

### Metrics to Monitor
- API response times
- Job completion rates
- Error rates
- Database query performance
- Memory usage
- Thread pool utilization

## Testing Strategy

### Unit Tests
- Test individual methods
- Mock dependencies
- Fast execution
- High coverage (80%+)

### Integration Tests
- Test API endpoints
- MockMvc for HTTP testing
- Database interactions
- End-to-end scenarios

### Test Coverage
```
- Trie operations: 100%
- Service layer: 85%
- Controller layer: 90%
- Overall: 85%+
```

## Deployment Architecture

### Development Environment
```
Local Machine
├── Spring Boot Application (port 8080)
├── H2 In-Memory Database
└── Maven for build
```

### Production Ready Setup (Recommended)
```
Load Balancer
├── App Server 1 (Docker)
├── App Server 2 (Docker)
├── App Server 3 (Docker)
Database Cluster
├── PostgreSQL Master
└── PostgreSQL Replica
Redis Cache Cluster
Message Queue (RabbitMQ)
```

## Technology Choices Justification

### Spring Boot
✅ Rapid development
✅ Production-ready features
✅ Large ecosystem
✅ Easy testing

### H2 Database
✅ Zero configuration
✅ Fast for development
✅ Easy testing
⚠️ Replace with PostgreSQL for production

### JSoup
✅ Simple HTML parsing
✅ CSS selector support
✅ Reliable and maintained
⚠️ No JavaScript execution

### Trie Data Structure
✅ Efficient prefix search
✅ O(m) time complexity
✅ Memory efficient for shared prefixes
✅ Natural fit for autocomplete

## Future Roadmap

### Phase 1 (Short-term)
- [ ] Add API documentation (Swagger)
- [ ] Implement rate limiting
- [ ] Add caching layer
- [ ] Docker containerization

### Phase 2 (Medium-term)
- [ ] User authentication
- [ ] PostgreSQL migration
- [ ] Selenium for JS sites
- [ ] Webhook notifications

### Phase 3 (Long-term)
- [ ] Microservices architecture
- [ ] Kubernetes deployment
- [ ] Machine learning for content extraction
- [ ] Real-time scraping with WebSockets

## Conclusion

This architecture provides a solid foundation for a web scraping application with efficient search capabilities. The clean separation of concerns, proper use of design patterns, and comprehensive testing ensure maintainability and extensibility.

---

**Document Version:** 1.0  
**Last Updated:** 2024  
**Author:** Development Team
