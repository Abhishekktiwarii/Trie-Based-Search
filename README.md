# Web Scraper with Trie-Based Search Application

A production-ready Spring Boot application that scrapes real-time data from websites and provides efficient keyword search using Trie data structure.

## Features

- **Web Scraping**: Scrapes content from multiple URLs based on specified keywords
- **Trie Data Structure**: Efficient prefix-based search and autocompletion
- **Scheduled Jobs**: Support for immediate and scheduled scraping
- **RESTful APIs**: Complete REST API for scraping, search, and job management
- **Async Processing**: Non-blocking scraping execution
- **Comprehensive Testing**: JUnit tests with 80%+ coverage
- **Clean Architecture**: Follows SOLID principles and design patterns

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **JSoup** (web scraping)
- **Lombok**
- **JUnit 5 & Mockito** (testing)

## Architecture & Design Patterns

### Design Patterns Implemented
1. **Repository Pattern**: Data access abstraction
2. **Service Layer Pattern**: Business logic separation
3. **DTO Pattern**: Data transfer between layers
4. **Singleton Pattern**: Trie instance management
5. **Strategy Pattern**: Scraping strategies
6. **Builder Pattern**: Entity and DTO construction

### Project Structure
```
src/
├── main/
│   ├── java/com/webscraper/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── entity/         # JPA entities
│   │   ├── enums/          # Enumerations
│   │   ├── exception/      # Custom exceptions
│   │   ├── repository/     # Data repositories
│   │   ├── service/        # Business logic
│   │   ├── trie/           # Trie implementation
│   │   └── util/           # Utility classes
│   └── resources/
│       └── application.properties
└── test/                    # Unit & Integration tests
```

## API Endpoints

### 1. Initiate Scraping
**POST** `/api/v1/scrape`

**Request Body:**
```json
{
  "urls": [
    "https://example.com/news",
    "https://example2.com/blog"
  ],
  "keywords": ["technology", "innovation"],
  "schedule": "2024-11-01T10:00:00Z"
}
```

**Response:**
```json
{
  "status": "success",
  "message": "Scraping initiated successfully.",
  "jobId": "550e8400-e29b-41d4-a716-446655440000",
  "scheduledAt": "2024-11-01T10:00:00Z"
}
```

### 2. Search Scraped Data
**POST** `/api/v1/search`

**Request Body:**
```json
{
  "prefix": "tech",
  "limit": 5
}
```

**Response:**
```json
{
  "status": "success",
  "results": [
    {
      "url": "https://example.com/news",
      "matchedContent": "Latest technology trends are shaping...",
      "timestamp": "2024-10-29T14:30:00Z"
    }
  ]
}
```

### 3. Check Job Status
**GET** `/api/v1/status/{jobId}`

**Response:**
```json
{
  "status": "completed",
  "jobId": "550e8400-e29b-41d4-a716-446655440000",
  "urlsScraped": [
    "https://example.com/news"
  ],
  "keywordsFound": ["technology"],
  "dataSize": "1.50 MB",
  "finishedAt": "2024-11-01T10:15:00Z"
}
```

### 4. Health Check
**GET** `/api/v1/health`

**Response:**
```
Web Scraper Service is running!
```

## Getting Started

### Prerequisites
- JDK 17 or higher
- Maven 3.6+

### Installation & Running

1. **Extract the zip file**
```bash
unzip webscraper-trie-app.zip
cd webscraper-trie-app
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Running Tests
```bash
mvn test
```

## Testing with Postman

### 1. Import Collection
Import the API endpoints into Postman using the base URL: `http://localhost:8080/api/v1`

### 2. Test Scraping Flow

**Step 1: Initiate Scraping**
```
POST http://localhost:8080/api/v1/scrape
Content-Type: application/json

{
  "urls": ["https://en.wikipedia.org/wiki/Technology"],
  "keywords": ["computer", "internet", "software"]
}
```

**Step 2: Check Job Status** (use jobId from Step 1)
```
GET http://localhost:8080/api/v1/status/{jobId}
```

**Step 3: Search for Keywords**
```
POST http://localhost:8080/api/v1/search
Content-Type: application/json

{
  "prefix": "comp",
  "limit": 10
}
```

## Configuration

### Database Configuration
Default configuration uses H2 in-memory database. Access H2 console at:
```
http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:webscraperdb
Username: sa
Password: (leave blank)
```

### Application Properties
Key configurations in `application.properties`:
- `server.port`: Server port (default: 8080)
- `spring.datasource.url`: Database URL
- `logging.level.com.webscraper`: Logging level

## Trie Data Structure

### Implementation Details
- **Thread-safe**: Synchronized methods for concurrent access
- **Case-insensitive**: Normalizes all inputs to lowercase
- **Prefix search**: O(m) time complexity where m is prefix length
- **Space efficient**: Shares common prefixes

### Trie Operations
```java
// Insert word
trie.insert("technology");

// Search exact word
boolean exists = trie.search("technology"); // true

// Check prefix
boolean hasPrefix = trie.startsWith("tech"); // true

// Find all words with prefix
List<String> words = trie.findWordsWithPrefix("tech", 10);
```

## Key Features Explanation

### 1. Async Scraping
Scraping jobs are executed asynchronously to prevent blocking the API response. Jobs are processed in a thread pool with configurable size.

### 2. Scheduled Jobs
The application checks for pending scheduled jobs every minute and executes them when due.

### 3. Error Handling
Comprehensive error handling with:
- Custom exceptions
- Global exception handler
- Proper HTTP status codes
- Detailed error messages

### 4. Data Persistence
- Scraped data stored in H2 database
- Job metadata tracked for monitoring
- Indexed tables for efficient queries

## Code Quality

### Best Practices Followed
- ✅ SOLID principles
- ✅ Clean code architecture
- ✅ Separation of concerns
- ✅ Dependency injection
- ✅ Interface-based design
- ✅ Comprehensive logging
- ✅ Input validation
- ✅ Exception handling
- ✅ Unit & Integration tests
- ✅ JavaDoc documentation

## Testing Coverage

### Test Classes
1. `TrieTest`: Trie data structure tests
2. `ScrapingServiceTest`: Service layer tests
3. `ScrapingControllerTest`: Controller tests

### Test Types
- Unit tests with Mockito
- Integration tests with MockMvc
- Edge case testing
- Validation testing

## Limitations & Future Enhancements

### Current Limitations
- In-memory database (data lost on restart)
- Basic web scraping (no JavaScript rendering)
- Single-server deployment

### Future Enhancements
- [ ] PostgreSQL/MySQL integration
- [ ] Selenium for JavaScript-heavy sites
- [ ] Rate limiting for scraping
- [ ] User authentication
- [ ] Distributed job processing
- [ ] Caching layer (Redis)
- [ ] API documentation (Swagger/OpenAPI)
- [ ] Docker containerization
- [ ] Kubernetes deployment

## Troubleshooting

### Common Issues

**Issue**: Application fails to start
**Solution**: Ensure JDK 17+ is installed and JAVA_HOME is set correctly

**Issue**: Scraping fails with timeout
**Solution**: Check network connectivity and target website accessibility

**Issue**: No results in search
**Solution**: Ensure scraping job completed successfully before searching

## Contributing

This is an assignment project. For questions or improvements, please contact the development team.

## License

This project is created for educational/assignment purposes.

## Contact

For any queries regarding this application, please refer to the assignment documentation.

---

**Built with ❤️ using Spring Boot**
