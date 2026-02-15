# Web Scraper with Trie-Based Search Application

## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Testing with Postman](#testing-with-postman)
- [Database Access](#database-access)
- [Architecture & Design](#architecture--design)
- [Testing](#testing)
- [Troubleshooting](#troubleshooting)
- [Advanced Features](#advanced-features)

---

## 🎯 Overview

A production-ready Spring Boot application that scrapes real-time data from websites and provides efficient keyword search using a Trie data structure. Built with clean architecture, design patterns, and comprehensive testing.

### What It Does
1. **Scrapes web content** from user-specified URLs based on keywords
2. **Stores scraped data** efficiently in a database with metadata
3. **Indexes keywords** using a Trie data structure for O(m) search complexity
4. **Enables prefix-based search** with autocompletion capabilities
5. **Supports scheduled scraping** at specified times or on-demand
6. **Tracks job status** and provides comprehensive monitoring

---

## ✨ Features

### Core Functionality
✅ **Web Scraping Module**
- Scrapes content from multiple URLs simultaneously
- Keyword-based content extraction
- Handles timeouts and errors gracefully
- Stores full content and matched snippets

✅ **Trie Data Structure**
- Custom implementation for efficient prefix search
- O(m) time complexity (m = prefix length)
- Thread-safe operations
- Memory-efficient with shared prefixes

✅ **RESTful API**
- 3 main endpoints + health check
- Proper HTTP status codes
- JSON request/response format
- Input validation and error handling

✅ **Job Management**
- Asynchronous job execution
- Scheduled and on-demand scraping
- Job status tracking
- Progress monitoring

✅ **Search Functionality**
- Prefix-based keyword search
- Configurable result limits
- Fast retrieval from indexed data

---

## 🛠 Technology Stack

### Backend Framework
- **Java 17** - Modern Java features
- **Spring Boot 3.2.0** - Application framework
- **Spring Data JPA** - Database abstraction
- **Spring Validation** - Input validation
- **Spring Async** - Asynchronous processing
- **Spring Scheduler** - Scheduled jobs

### Libraries
- **JSoup 1.17.1** - HTML parsing and web scraping
- **Lombok** - Boilerplate code reduction
- **H2 Database** - In-memory database for development

### Testing
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework
- **MockMvc** - REST API testing
- **Spring Boot Test** - Integration testing

### Build Tool
- **Maven 3.6+** - Dependency management and build

---

## 📁 Project Structure

```
webscraper-trie-app/
│
├── src/
│   ├── main/
│   │   ├── java/com/webscraper/
│   │   │   │
│   │   │   ├── config/                      # Configuration
│   │   │   │   ├── AsyncConfiguration.java   # Async config
│   │   │   │   └── ScheduledJobExecutor.java # Job scheduler
│   │   │   │
│   │   │   ├── controller/                  # REST Controllers
│   │   │   │   └── ScrapingController.java   # API endpoints
│   │   │   │
│   │   │   ├── dto/                         # Data Transfer Objects
│   │   │   │   ├── ScrapeRequest.java        # Scraping request
│   │   │   │   ├── ScrapeResponse.java       # Scraping response
│   │   │   │   ├── SearchRequest.java        # Search request
│   │   │   │   ├── SearchResponse.java       # Search response
│   │   │   │   └── JobStatusResponse.java    # Status response
│   │   │   │
│   │   │   ├── entity/                      # JPA Entities
│   │   │   │   ├── ScrapingJob.java          # Job entity
│   │   │   │   └── ScrapedData.java          # Data entity
│   │   │   │
│   │   │   ├── enums/                       # Enumerations
│   │   │   │   └── JobStatus.java            # Job states
│   │   │   │
│   │   │   ├── exception/                   # Exception Handling
│   │   │   │   ├── JobNotFoundException.java
│   │   │   │   ├── ScrapingException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   │
│   │   │   ├── repository/                  # Data Access
│   │   │   │   ├── ScrapingJobRepository.java
│   │   │   │   └── ScrapedDataRepository.java
│   │   │   │
│   │   │   ├── service/                     # Business Logic
│   │   │   │   ├── ScrapingService.java      # Job orchestration
│   │   │   │   └── WebScraperService.java    # Scraping logic
│   │   │   │
│   │   │   ├── trie/                        # Trie Implementation
│   │   │   │   ├── Trie.java                 # Trie structure
│   │   │   │   └── TrieNode.java             # Trie node
│   │   │   │
│   │   │   └── WebScraperTrieApplication.java # Main class
│   │   │
│   │   └── resources/
│   │       └── application.properties        # Configuration
│   │
│   └── test/                                # Test Classes
│       └── java/com/webscraper/
│           ├── controller/
│           │   └── ScrapingControllerTest.java
│           ├── service/
│           │   └── ScrapingServiceTest.java
│           └── trie/
│               └── TrieTest.java
│
├── pom.xml                                  # Maven config
├── README.md                                # This file
├── ARCHITECTURE.md                          # Architecture docs
├── QUICKSTART.md                            # Quick start guide
├── postman_collection.json                  # Postman collection
└── .gitignore                               # Git ignore file
```

---

## ⚙️ Prerequisites

Before you begin, ensure you have the following installed:

### Required
- ✅ **Java Development Kit (JDK) 17 or higher**
  - Download: https://www.oracle.com/java/technologies/downloads/
  - Verify: `java -version`

- ✅ **Apache Maven 3.6 or higher**
  - Download: https://maven.apache.org/download.cgi
  - Verify: `mvn -version`

### Recommended
- ✅ **Postman** (for API testing)
  - Download: https://www.postman.com/downloads/

- ✅ **IDE** (IntelliJ IDEA, Eclipse, or VS Code)
  - IntelliJ IDEA: https://www.jetbrains.com/idea/

### Verification Commands
```bash
# Check Java version (should be 17 or higher)
java -version

# Check Maven version (should be 3.6 or higher)
mvn -version

# Should show Java and Maven paths
```

---

## 🚀 Installation & Setup

### Step 1: Extract the Project
```bash
# Extract the zip file
unzip webscraper-trie-app.zip

# Navigate to project directory
cd webscraper-trie-app

# Verify project structure
ls -la
```

### Step 2: Build the Project
```bash
# Clean and build the project
mvn clean install

# Expected output:
# [INFO] BUILD SUCCESS
# [INFO] Total time: XX.XXX s
# [INFO] Finished at: YYYY-MM-DDTHH:MM:SS
```

**Build Output Explained:**
- `clean` - Removes previous build artifacts
- `install` - Compiles code, runs tests, packages JAR

**If build fails**, see [Troubleshooting](#troubleshooting) section.

### Step 3: Verify Build
```bash
# Check if JAR was created
ls target/

# You should see:
# webscraper-trie-app-1.0.0.jar
```

---

## 🏃 Running the Application

### Method 1: Using Maven (Recommended for Development)
```bash
# Run with default port (8080)
mvn spring-boot:run

# Run with custom port (e.g., 8083)
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8083
```

### Method 2: Using JAR File (Recommended for Production)
```bash
# Build the JAR
mvn clean package

# Run with default port
java -jar target/webscraper-trie-app-1.0.0.jar

# Run with custom port
java -jar target/webscraper-trie-app-1.0.0.jar --server.port=8083
```

### Method 3: From IDE
1. Open project in your IDE
2. Locate `WebScraperTrieApplication.java`
3. Right-click → Run
4. Application starts on port 8080 (or configured port)

### Verify Application is Running
```bash
# Test health endpoint
curl http://localhost:8080/api/v1/health

# Or with custom port
curl http://localhost:8083/api/v1/health

# Expected response:
# Web Scraper Service is running!
```

### Application Startup Logs
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-11-01 10:00:00 INFO  c.w.WebScraperTrieApplication : Started WebScraperTrieApplication in 3.456 seconds
```

### Changing the Port
Edit `src/main/resources/application.properties`:
```properties
server.port=8083
```

---

## 📖 API Documentation

### Base URL
```
http://localhost:8080/api/v1
```
*(Replace 8080 with your configured port)*

### API Endpoints Summary

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/health` | Health check |
| POST | `/scrape` | Initiate scraping |
| GET | `/status/{jobId}` | Check job status |
| POST | `/search` | Search keywords |

---

### 1️⃣ Health Check Endpoint

**Purpose**: Verify the application is running

**Endpoint**: `GET /api/v1/health`

**Request**:
```bash
curl http://localhost:8080/api/v1/health
```

**Response**:
```
HTTP Status: 200 OK
Body: Web Scraper Service is running!
```

**Use Case**: 
- Quick health check
- Monitoring and alerting
- Verify deployment

---

### 2️⃣ Initiate Scraping Endpoint

**Purpose**: Start a web scraping job for specified URLs and keywords

**Endpoint**: `POST /api/v1/scrape`

**Headers**:
```
Content-Type: application/json
```

**Request Body**:
```json
{
  "urls": [
    "https://example.com/page1",
    "https://example.com/page2"
  ],
  "keywords": ["keyword1", "keyword2", "keyword3"],
  "schedule": "2024-11-01T10:00:00Z"  // Optional - for scheduled scraping
}
```

**Request Fields**:
- `urls` (required): Array of URLs to scrape (1-10 URLs recommended)
- `keywords` (required): Array of keywords to search for (1-20 keywords)
- `schedule` (optional): ISO 8601 timestamp for scheduled scraping
  - If omitted: Job executes immediately
  - If provided: Job executes at specified time

**Response**:
```json
{
  "status": "success",
  "message": "Scraping initiated successfully.",
  "jobId": "550e8400-e29b-41d4-a716-446655440000",
  "scheduledAt": "2024-11-01T10:00:00Z"
}
```

**Response Fields**:
- `status`: "success" or "error"
- `message`: Descriptive message
- `jobId`: Unique identifier for tracking the job
- `scheduledAt`: When the job will/did execute

**HTTP Status Codes**:
- `201 Created`: Job created successfully
- `400 Bad Request`: Invalid input (empty URLs/keywords)
- `500 Internal Server Error`: Server error

**Example (Immediate Scraping)**:
```bash
curl -X POST http://localhost:8080/api/v1/scrape \
  -H "Content-Type: application/json" \
  -d '{
    "urls": ["https://en.wikipedia.org/wiki/Technology"],
    "keywords": ["computer", "software", "internet"]
  }'
```

**Example (Scheduled Scraping)**:
```bash
curl -X POST http://localhost:8080/api/v1/scrape \
  -H "Content-Type: application/json" \
  -d '{
    "urls": ["https://example.com"],
    "keywords": ["technology"],
    "schedule": "2024-12-01T15:00:00Z"
  }'
```

---

### 3️⃣ Check Job Status Endpoint

**Purpose**: Retrieve the status and details of a scraping job

**Endpoint**: `GET /api/v1/status/{jobId}`

**Path Parameters**:
- `jobId`: The job ID returned from the scrape endpoint

**Request**:
```bash
curl http://localhost:8080/api/v1/status/550e8400-e29b-41d4-a716-446655440000
```

**Response** (Job Completed):
```json
{
  "status": "completed",
  "jobId": "550e8400-e29b-41d4-a716-446655440000",
  "urlsScraped": [
    "https://en.wikipedia.org/wiki/Technology",
    "https://en.wikipedia.org/wiki/Computer"
  ],
  "keywordsFound": ["technology", "computer", "software"],
  "dataSize": "125.45 KB",
  "finishedAt": "2024-11-01T10:05:23Z",
  "errorMessage": null
}
```

**Response** (Job In Progress):
```json
{
  "status": "in_progress",
  "jobId": "550e8400-e29b-41d4-a716-446655440000",
  "urlsScraped": ["https://en.wikipedia.org/wiki/Technology"],
  "keywordsFound": [],
  "dataSize": null,
  "finishedAt": null,
  "errorMessage": null
}
```

**Response** (Job Failed):
```json
{
  "status": "failed",
  "jobId": "550e8400-e29b-41d4-a716-446655440000",
  "urlsScraped": [],
  "keywordsFound": [],
  "dataSize": null,
  "finishedAt": "2024-11-01T10:05:23Z",
  "errorMessage": "Connection timeout: https://invalid-url.com"
}
```

**Job Status Values**:
- `pending`: Job created, waiting to execute
- `in_progress`: Currently scraping
- `completed`: Successfully finished
- `failed`: Encountered errors
- `cancelled`: Job was cancelled

**Response Fields**:
- `status`: Current job status
- `jobId`: Job identifier
- `urlsScraped`: List of URLs processed
- `keywordsFound`: Keywords found in content
- `dataSize`: Total data scraped (human-readable)
- `finishedAt`: Completion timestamp (null if not finished)
- `errorMessage`: Error details (null if no error)

**HTTP Status Codes**:
- `200 OK`: Job found and status retrieved
- `404 Not Found`: Job ID doesn't exist
- `500 Internal Server Error`: Server error

---

### 4️⃣ Search Keywords Endpoint

**Purpose**: Search scraped data using prefix-based matching

**Endpoint**: `POST /api/v1/search`

**Headers**:
```
Content-Type: application/json
```

**Request Body**:
```json
{
  "prefix": "tech",
  "limit": 10
}
```

**Request Fields**:
- `prefix` (required): Search prefix (minimum 1 character)
- `limit` (optional): Maximum results to return (default: 10, max: 100)

**Response**:
```json
{
  "status": "success",
  "results": [
    {
      "url": "https://en.wikipedia.org/wiki/Technology",
      "matchedContent": "...technology has revolutionized modern computing...",
      "timestamp": "2024-10-29T14:30:00Z"
    },
    {
      "url": "https://en.wikipedia.org/wiki/Computer",
      "matchedContent": "...technical advances in computer science...",
      "timestamp": "2024-10-29T14:31:00Z"
    }
  ]
}
```

**Response Fields**:
- `status`: "success" or "error"
- `results`: Array of matching results
  - `url`: Source URL where content was found
  - `matchedContent`: Content snippet with keyword context
  - `timestamp`: When the data was scraped

**How Prefix Matching Works**:
```
Prefix "tech" matches:
✅ technology
✅ technical
✅ technique
✅ techno
❌ machine
❌ computer
```

**HTTP Status Codes**:
- `200 OK`: Search completed (even if 0 results)
- `400 Bad Request`: Invalid prefix (empty or null)
- `500 Internal Server Error`: Server error

**Example**:
```bash
curl -X POST http://localhost:8080/api/v1/search \
  -H "Content-Type: application/json" \
  -d '{
    "prefix": "comp",
    "limit": 5
  }'
```

**Search Tips**:
- Use 3-4 character prefixes for best results
- Common prefixes: "tech", "comp", "soft", "prog", "data"
- Case-insensitive matching
- Returns most recent results first

---

## 🧪 Testing with Postman

### Quick Start (5 Minutes)

#### Option 1: Import Postman Collection (Recommended)

**Step 1**: Import the Collection
1. Open Postman
2. Click **Import** button (top-left)
3. Click **Choose Files**
4. Select `postman_collection.json` from project folder
5. Click **Import**

**Step 2**: Update Port (if not using 8080)
1. Click on the imported collection "Web Scraper Trie API"
2. Click the three dots (...) → **Edit**
3. Go to **Variables** tab
4. Change all URLs from port `8080` to your port (e.g., `8083`)
5. Click **Save**

**Step 3**: Start Testing
- All 4 requests are ready to use
- Just click **Send** on each request

---

#### Option 2: Manual Setup (If Collection Import Doesn't Work)

### 🔷 Test 1: Health Check

**Step-by-Step**:

1. **Create New Request**
   - Click the `+` button or **New** → **HTTP Request**

2. **Configure Request**
   - Method: **GET**
   - URL: `http://localhost:8080/api/v1/health`
   - (Change `8080` to your port if different)

3. **Send Request**
   - Click **Send** button

4. **Verify Response**
   ```
   Status: 200 OK
   Body: Web Scraper Service is running!
   ```

**Screenshot Guide**:
```
┌─────────────────────────────────────────┐
│ GET  http://localhost:8080/api/v1/health│  [Send]
├─────────────────────────────────────────┤
│ Headers   Body   ...                    │
├─────────────────────────────────────────┤
│ Status: 200 OK                          │
│ Web Scraper Service is running!        │
└─────────────────────────────────────────┘
```

---

### 🔷 Test 2: Initiate Scraping (MOST IMPORTANT)

**Step-by-Step**:

1. **Create New Request**
   - Click `+` or **New** → **HTTP Request**

2. **Configure Method & URL**
   - Method: **POST**
   - URL: `http://localhost:8080/api/v1/scrape`

3. **Set Headers**
   - Click **Headers** tab
   - Add: `Content-Type` = `application/json`

4. **Set Request Body**
   - Click **Body** tab
   - Select **raw**
   - Select **JSON** from dropdown (right side)
   - Paste this JSON:

   ```json
   {
     "urls": [
       "https://en.wikipedia.org/wiki/Technology",
       "https://en.wikipedia.org/wiki/Computer"
     ],
     "keywords": ["technology", "computer", "software", "internet", "data"]
   }
   ```

5. **Send Request**
   - Click **Send**

6. **Copy the jobId**
   - From the response, copy the `jobId` value
   - Example: `"jobId": "550e8400-e29b-41d4-a716-446655440000"`
   - **YOU'LL NEED THIS FOR THE NEXT TEST!**

**Expected Response**:
```json
{
  "status": "success",
  "message": "Scraping initiated successfully.",
  "jobId": "550e8400-e29b-41d4-a716-446655440000",
  "scheduledAt": "2024-11-01T10:00:00Z"
}
```

**Status Code**: `201 Created`

**Screenshot Guide**:
```
┌────────────────────────────────────────────┐
│ POST  http://localhost:8080/api/v1/scrape │ [Send]
├────────────────────────────────────────────┤
│ Headers │ Body │ ...                       │
├────────────────────────────────────────────┤
│ Content-Type: application/json             │
├────────────────────────────────────────────┤
│ {                                          │
│   "urls": [...],                           │
│   "keywords": [...]                        │
│ }                                          │
├────────────────────────────────────────────┤
│ Response: 201 Created                      │
│ {                                          │
│   "status": "success",                     │
│   "jobId": "550e8400-..."  ← COPY THIS    │
│ }                                          │
└────────────────────────────────────────────┘
```

**Alternative URLs for Testing**:
```json
{
  "urls": [
    "https://en.wikipedia.org/wiki/Artificial_intelligence",
    "https://en.wikipedia.org/wiki/Machine_learning",
    "https://en.wikipedia.org/wiki/Programming"
  ],
  "keywords": ["algorithm", "data", "code", "function", "system"]
}
```

---

### 🔷 Test 3: Check Job Status

**⏰ WAIT 15-30 seconds after starting the scraping job before checking status!**

**Step-by-Step**:

1. **Create New Request**
   - Click `+` or **New** → **HTTP Request**

2. **Configure Method & URL**
   - Method: **GET**
   - URL: `http://localhost:8080/api/v1/status/{jobId}`
   - **Replace `{jobId}` with the actual jobId from Test 2**

   Example:
   ```
   http://localhost:8080/api/v1/status/550e8400-e29b-41d4-a716-446655440000
   ```

3. **Send Request**
   - Click **Send**

4. **Check Status Field**
   - If `"status": "in_progress"` → Wait 10 more seconds and try again
   - If `"status": "completed"` → Success! Proceed to Test 4
   - If `"status": "failed"` → Check `errorMessage` field

**Expected Response (Completed)**:
```json
{
  "status": "completed",
  "jobId": "550e8400-e29b-41d4-a716-446655440000",
  "urlsScraped": [
    "https://en.wikipedia.org/wiki/Technology",
    "https://en.wikipedia.org/wiki/Computer"
  ],
  "keywordsFound": ["technology", "computer", "software", "internet"],
  "dataSize": "145.67 KB",
  "finishedAt": "2024-11-01T10:05:23Z",
  "errorMessage": null
}
```

**Status Code**: `200 OK`

**What to Verify**:
- ✅ `status` = `"completed"`
- ✅ `urlsScraped` array is not empty
- ✅ `keywordsFound` array has values
- ✅ `dataSize` shows data amount
- ✅ `finishedAt` has timestamp
- ✅ `errorMessage` is null

**If Status is "in_progress"**:
- This is normal! Scraping takes time
- Wait 10-30 seconds
- Send the request again
- Repeat until status is "completed"

**If Status is "failed"**:
- Check the `errorMessage` field
- Common issues:
  - Invalid URL
  - Network timeout
  - Website blocking
- Try with different URLs

---

### 🔷 Test 4: Search Keywords

**⚠️ Only run this AFTER Test 3 shows "completed" status!**

**Step-by-Step**:

1. **Create New Request**
   - Click `+` or **New** → **HTTP Request**

2. **Configure Method & URL**
   - Method: **POST**
   - URL: `http://localhost:8080/api/v1/search`

3. **Set Headers**
   - Click **Headers** tab
   - Add: `Content-Type` = `application/json`

4. **Set Request Body**
   - Click **Body** tab
   - Select **raw**
   - Select **JSON** from dropdown
   - Paste this JSON:

   ```json
   {
     "prefix": "tech",
     "limit": 10
   }
   ```

5. **Send Request**
   - Click **Send**

6. **Try Different Prefixes**
   - Change `"prefix"` value and send again
   - Try: `"comp"`, `"soft"`, `"int"`, `"data"`

**Expected Response**:
```json
{
  "status": "success",
  "results": [
    {
      "url": "https://en.wikipedia.org/wiki/Technology",
      "matchedContent": "...technology has revolutionized the way we live and work...",
      "timestamp": "2024-10-29T14:30:00Z"
    },
    {
      "url": "https://en.wikipedia.org/wiki/Computer",
      "matchedContent": "...technological advances in computing have enabled...",
      "timestamp": "2024-10-29T14:31:00Z"
    }
  ]
}
```

**Status Code**: `200 OK`

**Search Prefix Examples**:

| Prefix | Will Match |
|--------|------------|
| `"tech"` | technology, technical, technique |
| `"comp"` | computer, computing, computational |
| `"soft"` | software, softwares |
| `"int"` | internet, interface, integration |
| `"data"` | data, database, datasets |
| `"prog"` | program, programming, programmer |
| `"algo"` | algorithm, algorithms, algorithmic |

**What if No Results?**:
- Check that scraping job completed successfully
- Verify `keywordsFound` array in status response
- Try different prefix (use shorter prefix like 3-4 chars)
- Make sure prefix matches keywords you used in Test 2

---

### 📊 Complete Testing Flow

```
Step 1: Health Check
   ↓
   ✅ App is running
   ↓
Step 2: Initiate Scraping
   ↓
   ✅ Got jobId: 550e8400-...
   ↓
   ⏰ WAIT 15-30 seconds
   ↓
Step 3: Check Status
   ↓
   ❓ Status: "in_progress"
   ↓
   ⏰ WAIT 10 more seconds
   ↓
Step 3: Check Status Again
   ↓
   ✅ Status: "completed"
   ✅ keywordsFound: ["technology", "computer", ...]
   ↓
Step 4: Search Keywords
   ↓
   ✅ Got results!
```

---

### 💡 Postman Pro Tips

#### 1. Save Your Requests
- Click **Save** button after creating each request
- Name them clearly:
  - "1. Health Check"
  - "2. Initiate Scraping"
  - "3. Check Job Status"
  - "4. Search Keywords"
- Organize in a folder/collection

#### 2. Use Postman Variables

**Create Environment**:
1. Click **Environments** (left sidebar)
2. Click `+` to create new environment
3. Name it "Web Scraper Local"
4. Add variables:

| Variable | Value |
|----------|-------|
| `base_url` | `http://localhost:8080/api/v1` |
| `job_id` | `(paste jobId here after Test 2)` |

5. Select this environment (dropdown at top-right)

**Use Variables in URLs**:
```
{{base_url}}/health
{{base_url}}/scrape
{{base_url}}/status/{{job_id}}
{{base_url}}/search
```

#### 3. Chain Requests with Tests

Add this to the **Tests** tab of "Initiate Scraping" request:
```javascript
// Save jobId automatically
if (pm.response.code === 201) {
    var jsonData = pm.response.json();
    pm.environment.set("job_id", jsonData.jobId);
    console.log("JobId saved: " + jsonData.jobId);
}
```

Now you don't need to copy-paste jobId manually!

#### 4. Create Request Collection

**Organize All Requests**:
1. Right-click in Collections
2. Create **New Collection** → "Web Scraper API"
3. Drag all 4 requests into this collection
4. Set collection-level variables
5. Export collection for sharing

#### 5. Use Pre-request Scripts

Add to "Check Status" request:
```javascript
// Auto-wait before checking status
setTimeout(function(){}, 2000);
console.log("Waiting 2 seconds before checking status...");
```

---

### 🔴 Common Postman Errors & Solutions

#### Error: "Could not get any response"
**Cause**: Application not running

**Solution**:
1. Check if Spring Boot app is running
2. Verify port number in URL
3. Test with: `curl http://localhost:8080/api/v1/health`

---

#### Error: 400 Bad Request - "URLs list cannot be empty"
**Cause**: Request body validation failed

**Solution**:
1. Check Body tab is selected
2. Verify **raw** is selected
3. Verify **JSON** is selected from dropdown
4. Ensure JSON is valid (use JSON validator)
5. Check `urls` array has at least one URL
6. Check `keywords` array has at least one keyword

---

#### Error: 404 Not Found - Job not found
**Cause**: Invalid or incorrect jobId

**Solution**:
1. Verify you copied the complete jobId
2. Check for extra spaces or characters
3. Ensure job was created successfully in Test 2
4. Check application logs for errors

---

#### Error: Connection Refused
**Cause**: Wrong port or app not running

**Solution**:
1. Verify application is running: `mvn spring-boot:run`
2. Check port in application.properties
3. Update Postman URLs with correct port

---

### 📸 Expected Postman Results Summary

| Test | Status | What You Should See |
|------|--------|---------------------|
| Health Check | 200 OK | "Web Scraper Service is running!" |
| Initiate Scraping | 201 Created | jobId returned |
| Check Status (first call) | 200 OK | status: "in_progress" or "completed" |
| Check Status (after wait) | 200 OK | status: "completed", keywordsFound: [...] |
| Search Keywords | 200 OK | results array with matches |

---

## 🗄️ Database Access

### H2 Console

The application uses H2 in-memory database. Access the web console:

**URL**: `http://localhost:8080/h2-console`

**Login Credentials**:
```
JDBC URL: jdbc:h2:mem:webscraperdb
Username: sa
Password: (leave blank)
```

**Steps**:
1. Navigate to http://localhost:8080/h2-console
2. Ensure JDBC URL is: `jdbc:h2:mem:webscraperdb`
3. Username: `sa`
4. Password: (empty)
5. Click **Connect**

### Useful SQL Queries

**View All Jobs**:
```sql
SELECT * FROM scraping_job;
```

**View Scraped Data**:
```sql
SELECT * FROM scraped_data;
```

**View Recent Jobs**:
```sql
SELECT job_id, status, scheduled_at, finished_at 
FROM scraping_job 
ORDER BY scheduled_at DESC;
```

**Count Keywords Found**:
```sql
SELECT keyword, COUNT(*) as count 
FROM scraped_data 
WHERE keyword IS NOT NULL
GROUP BY keyword 
ORDER BY count DESC;
```

**View Job Details with Data**:
```sql
SELECT 
    sj.job_id,
    sj.status,
    sj.data_size,
    COUNT(sd.id) as records_count
FROM scraping_job sj
LEFT JOIN scraped_data sd ON sj.job_id = sd.job_id
GROUP BY sj.job_id, sj.status, sj.data_size;
```

**Search Scraped Content**:
```sql
SELECT url, keyword, matched_content, timestamp
FROM scraped_data
WHERE LOWER(matched_content) LIKE '%technology%'
ORDER BY timestamp DESC;
```

---

## 🏗️ Architecture & Design

### Design Patterns Used

#### 1. Repository Pattern
**Location**: `repository/` package

**Purpose**: Abstracts data access logic

**Example**:
```java
public interface ScrapingJobRepository extends JpaRepository<ScrapingJob, Long> {
    Optional<ScrapingJob> findByJobId(String jobId);
}
```

#### 2. Service Layer Pattern
**Location**: `service/` package

**Purpose**: Encapsulates business logic

**Layers**:
```
Controller (HTTP) → Service (Business Logic) → Repository (Data Access)
```

#### 3. DTO Pattern
**Location**: `dto/` package

**Purpose**: Transfer data between layers without exposing entities

**Examples**:
- `ScrapeRequest` - Input for scraping
- `ScrapeResponse` - Output of scraping
- `SearchRequest` - Input for search
- `SearchResponse` - Output of search

#### 4. Builder Pattern
**Usage**: Entity and DTO construction

**Example**:
```java
ScrapedData data = ScrapedData.builder()
    .jobId(jobId)
    .url(url)
    .content(content)
    .build();
```

#### 5. Singleton Pattern
**Usage**: Trie instance (managed by Spring)

**Implementation**:
```java
@Component
public class Trie {
    // Single instance across application
}
```

### SOLID Principles

✅ **Single Responsibility**
- Each class has one reason to change
- `WebScraperService`: Only scraping
- `ScrapingService`: Only orchestration
- `ScrapingController`: Only HTTP handling

✅ **Open/Closed Principle**
- Open for extension, closed for modification
- Easy to add new scraping strategies

✅ **Liskov Substitution**
- Interfaces used appropriately
- Repository contracts

✅ **Interface Segregation**
- Focused, specific interfaces
- No unused methods

✅ **Dependency Inversion**
- Depend on abstractions
- Constructor injection
- Interface-based dependencies

### Trie Data Structure

**Time Complexity**:
- Insert: O(m) where m = word length
- Search: O(m) where m = word length
- Prefix Search: O(p + n) where p = prefix length, n = results

**Space Complexity**: O(ALPHABET_SIZE * N * M)
- ALPHABET_SIZE = 26 (lowercase letters)
- N = number of words
- M = average word length

**Thread Safety**: Synchronized methods for concurrent access

---

## 🧪 Testing

### Run All Tests
```bash
# Run all tests
mvn test

# Run with detailed output
mvn test -Dtest=*

# Run specific test class
mvn test -Dtest=TrieTest
mvn test -Dtest=ScrapingServiceTest
mvn test -Dtest=ScrapingControllerTest
```

### Test Coverage
```bash
# Generate coverage report
mvn clean test jacoco:report

# View report
open target/site/jacoco/index.html
```

**Expected Coverage**: 85%+

### Test Classes

#### 1. TrieTest (10 tests)
- Insert and search operations
- Prefix matching
- Edge cases (null, empty)
- Case insensitivity
- Clear and size operations

#### 2. ScrapingServiceTest (8 tests)
- Job creation
- Job status retrieval
- Search functionality
- Error handling
- Mock-based unit tests

#### 3. ScrapingControllerTest (7 tests)
- API endpoint testing
- Request validation
- Response format
- HTTP status codes
- Integration tests with MockMvc

### Running Individual Tests in IDE

**IntelliJ IDEA**:
1. Navigate to test class
2. Right-click on class name
3. Select **Run 'TestClassName'**

**Eclipse**:
1. Right-click on test class
2. Select **Run As** → **JUnit Test**

---

## 🔧 Troubleshooting

### Application Won't Start

#### Issue: Port Already in Use
```
Error: Web server failed to start. Port 8080 was already in use.
```

**Solution 1**: Change port in `application.properties`
```properties
server.port=8083
```

**Solution 2**: Kill process using port 8080
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -i :8080
kill -9 <PID>
```

---

#### Issue: Java Version Error
```
Error: Java version mismatch. Required: 17, Found: 11
```

**Solution**:
```bash
# Check Java version
java -version

# Set JAVA_HOME (Windows)
set JAVA_HOME=C:\Program Files\Java\jdk-17

# Set JAVA_HOME (Linux/Mac)
export JAVA_HOME=/usr/lib/jvm/java-17

# Verify
echo $JAVA_HOME
```

---

#### Issue: Maven Build Fails
```
Error: Failed to execute goal
```

**Solution**:
```bash
# Clean and rebuild
mvn clean

# Update dependencies
mvn clean install -U

# Skip tests temporarily
mvn clean install -DskipTests
```

---

### API Issues

#### Issue: 404 Not Found on All Endpoints
**Cause**: Application context path issue or not running

**Solution**:
1. Verify app is running: Check console for "Started WebScraperTrieApplication"
2. Test health endpoint: `curl http://localhost:8080/api/v1/health`
3. Check for errors in console logs

---

#### Issue: Scraping Job Never Completes
**Possible Causes**:
- Target website is slow
- Network issues
- Website blocking scraper

**Solution**:
1. Check application logs for errors
2. Test with Wikipedia URLs (reliable)
3. Increase timeout in `WebScraperService`
4. Check database: `SELECT * FROM scraping_job;`

---

#### Issue: No Search Results
**Possible Causes**:
- Job not completed
- Keywords not found
- Wrong search prefix

**Checklist**:
1. ✅ Job status is "completed"?
2. ✅ `keywordsFound` array has values?
3. ✅ Search prefix matches keywords?
4. ✅ Using correct case? (should be case-insensitive)

**Debug SQL**:
```sql
-- Check if keywords are in database
SELECT DISTINCT keyword FROM scraped_data;

-- Check if prefix matches
SELECT keyword FROM scraped_data 
WHERE LOWER(keyword) LIKE 'tech%';
```

---

#### Issue: "Connection Refused" in Postman
**Cause**: Application not running or wrong port

**Solution**:
1. Verify app is running
2. Check port number in application.properties
3. Update Postman URLs with correct port
4. Test with curl: `curl http://localhost:8080/api/v1/health`

---

### Database Issues

#### Issue: Can't Connect to H2 Console
**Solution**:
1. Verify application is running
2. Check H2 console is enabled in application.properties:
   ```properties
   spring.h2.console.enabled=true
   ```
3. Use correct JDBC URL: `jdbc:h2:mem:webscraperdb`
4. Leave password blank

---

#### Issue: Data Disappears After Restart
**Explanation**: This is expected behavior!

H2 is configured as **in-memory** database, which means:
- ✅ Fast performance
- ✅ Zero configuration
- ❌ Data is lost on restart

**For Persistent Storage**:
Edit `application.properties`:
```properties
# Change from:
spring.datasource.url=jdbc:h2:mem:webscraperdb

# To (file-based):
spring.datasource.url=jdbc:h2:file:./data/webscraperdb
```

---

### Test Issues

#### Issue: Tests Fail with "Connection Refused"
**Cause**: Tests trying to connect to external URLs

**Solution**:
Tests use mocks, so this shouldn't happen. If it does:
1. Check internet connection
2. Run with: `mvn test -Dmaven.test.skip=false`
3. Check specific failing test

---

#### Issue: Tests Pass but Application Fails
**Cause**: Environment differences

**Solution**:
1. Rebuild: `mvn clean install`
2. Check logs for specific errors
3. Verify all dependencies: `mvn dependency:tree`

---

## 🚀 Advanced Features

### Custom Configuration

#### Change Database to File-Based
Edit `application.properties`:
```properties
spring.datasource.url=jdbc:h2:file:./data/webscraperdb
```

#### Increase Scraping Timeout
Edit `WebScraperService.java`:
```java
private static final int TIMEOUT_MS = 30000; // 30 seconds
```

#### Change Thread Pool Size
Edit `AsyncConfiguration.java`:
```java
executor.setCorePoolSize(10);  // More threads
executor.setMaxPoolSize(20);
```

#### Enable Debug Logging
Edit `application.properties`:
```properties
logging.level.com.webscraper=DEBUG
```

### Extend Functionality

#### Add New Scraping Strategy
```java
public interface ScrapingStrategy {
    List<ScrapedData> scrape(String url, List<String> keywords);
}

@Service
public class SeleniumScrapingStrategy implements ScrapingStrategy {
    // Implement for JavaScript-heavy sites
}
```

#### Add Caching
```java
@Cacheable("searchResults")
public SearchResponse search(SearchRequest request) {
    // Results will be cached
}
```

#### Add Authentication
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Add JWT or Basic Auth
}
```

---

## 📚 Additional Resources

### Documentation Files
- **README.md** (this file) - Complete guide
- **ARCHITECTURE.md** - Architecture details
- **QUICKSTART.md** - 5-minute quick start
- **PROJECT_EXPLANATION.md** - Detailed explanation

### API Testing
- **postman_collection.json** - Import into Postman

### Code Documentation
- JavaDoc comments in all classes
- Inline comments for complex logic

### External Resources
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [JSoup Documentation](https://jsoup.org/)
- [Trie Data Structure](https://en.wikipedia.org/wiki/Trie)

---

## 📞 Support

### Getting Help
1. Check this README for solutions
2. Review ARCHITECTURE.md for design details
3. Check application logs
4. Review JavaDoc in code

### Common Questions

**Q: Can I use a different database?**  
A: Yes! Just update application.properties with your database connection details.

**Q: How do I deploy to production?**  
A: Build JAR with `mvn package`, then run with `java -jar target/webscraper-trie-app-1.0.0.jar`

**Q: Can I scrape JavaScript-heavy websites?**  
A: Current implementation uses JSoup (static HTML only). For JavaScript sites, integrate Selenium.

**Q: How many URLs can I scrape at once?**  
A: Recommended: 1-10 URLs per job. More URLs = longer processing time.

**Q: Is the data persistent?**  
A: By default, no (H2 in-memory). Change to file-based H2 or PostgreSQL for persistence.

---

## ✅ Checklist for Successful Testing

Before submitting or demonstrating:

- [ ] Application builds successfully: `mvn clean install`
- [ ] Application starts: `mvn spring-boot:run`
- [ ] Health check works: `GET /health` returns 200 OK
- [ ] Can initiate scraping: `POST /scrape` returns 201 Created
- [ ] Can check job status: `GET /status/{jobId}` returns 200 OK
- [ ] Job completes successfully: status = "completed"
- [ ] Search returns results: `POST /search` returns results
- [ ] All tests pass: `mvn test` shows no failures
- [ ] H2 console accessible
- [ ] Postman collection works

---

## 🎓 Learning Outcomes

By studying this project, you'll learn:

- ✅ Spring Boot application structure
- ✅ RESTful API design
- ✅ JPA and database operations
- ✅ Async and scheduled processing
- ✅ Trie data structure implementation
- ✅ Web scraping with JSoup
- ✅ Exception handling
- ✅ Unit and integration testing
- ✅ Design patterns in practice
- ✅ Clean code principles

---

## 📄 License

This project is created for educational/assignment purposes.

---

## 🙏 Acknowledgments

Built with:
- Spring Boot 3.2.0
- JSoup 1.17.1
- H2 Database
- JUnit 5 & Mockito

---

**For questions or issues, please refer to the troubleshooting section or check the application logs.**

---

**Happy Testing! 🚀**

Last Updated: 2024
Version: 1.0.0
