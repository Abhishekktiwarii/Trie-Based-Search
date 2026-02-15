# Quick Start Guide

## Prerequisites Check

Before starting, ensure you have:
- ✅ JDK 17 or higher installed
- ✅ Maven 3.6+ installed
- ✅ Postman (for API testing)

**Check Java version:**
```bash
java -version
```

**Check Maven version:**
```bash
mvn -version
```

## Installation Steps

### Step 1: Extract Project
```bash
unzip webscraper-trie-app.zip
cd webscraper-trie-app
```

### Step 2: Build Project
```bash
mvn clean install
```

Expected output:
```
[INFO] BUILD SUCCESS
[INFO] Total time: XX.XXX s
```

### Step 3: Run Application
```bash
mvn spring-boot:run
```

Expected output:
```
Started WebScraperTrieApplication in X.XXX seconds
```

Application is now running at: `http://localhost:8080`

## Quick Test Workflow

### Test 1: Health Check
```bash
curl http://localhost:8080/api/v1/health
```

Expected response:
```
Web Scraper Service is running!
```

### Test 2: Initiate Scraping

**Using curl:**
```bash
curl -X POST http://localhost:8080/api/v1/scrape \
  -H "Content-Type: application/json" \
  -d '{
    "urls": ["https://en.wikipedia.org/wiki/Technology"],
    "keywords": ["computer", "software", "internet"]
  }'
```

**Using Postman:**
1. Open Postman
2. Create new POST request
3. URL: `http://localhost:8080/api/v1/scrape`
4. Headers: `Content-Type: application/json`
5. Body (raw JSON):
```json
{
  "urls": ["https://en.wikipedia.org/wiki/Technology"],
  "keywords": ["computer", "software", "internet"]
}
```
6. Click Send

**Expected Response:**
```json
{
  "status": "success",
  "message": "Scraping initiated successfully.",
  "jobId": "550e8400-e29b-41d4-a716-446655440000",
  "scheduledAt": "2024-11-01T10:00:00Z"
}
```

**📝 Save the `jobId` for next steps!**

### Test 3: Check Job Status

Replace `{jobId}` with the ID from previous step:

```bash
curl http://localhost:8080/api/v1/status/{jobId}
```

**Expected Response:**
```json
{
  "status": "completed",
  "jobId": "550e8400-e29b-41d4-a716-446655440000",
  "urlsScraped": ["https://en.wikipedia.org/wiki/Technology"],
  "keywordsFound": ["computer", "software"],
  "dataSize": "45.23 KB",
  "finishedAt": "2024-11-01T10:05:23Z"
}
```

### Test 4: Search Keywords

```bash
curl -X POST http://localhost:8080/api/v1/search \
  -H "Content-Type: application/json" \
  -d '{
    "prefix": "comp",
    "limit": 5
  }'
```

**Expected Response:**
```json
{
  "status": "success",
  "results": [
    {
      "url": "https://en.wikipedia.org/wiki/Technology",
      "matchedContent": "...computer science and technology...",
      "timestamp": "2024-11-01T10:05:20Z"
    }
  ]
}
```

## Run Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=TrieTest
mvn test -Dtest=ScrapingServiceTest
mvn test -Dtest=ScrapingControllerTest
```

### View Test Coverage
```bash
mvn clean test jacoco:report
# Report will be in: target/site/jacoco/index.html
```

## Access H2 Database Console

1. Navigate to: `http://localhost:8080/h2-console`
2. Use these credentials:
   - JDBC URL: `jdbc:h2:mem:webscraperdb`
   - Username: `sa`
   - Password: (leave blank)
3. Click "Connect"

### Useful SQL Queries

**View all jobs:**
```sql
SELECT * FROM scraping_job;
```

**View scraped data:**
```sql
SELECT * FROM scraped_data;
```

**Count keywords:**
```sql
SELECT keyword, COUNT(*) as count 
FROM scraped_data 
GROUP BY keyword;
```

## Import Postman Collection

1. Open Postman
2. Click "Import"
3. Select `postman_collection.json` from project root
4. Collection "Web Scraper Trie API" will be imported
5. Update `{{jobId}}` variable with actual job ID

## Common Workflows

### Workflow 1: Immediate Scraping
```
1. POST /api/v1/scrape (without schedule)
   → Job executes immediately
2. Wait 10-30 seconds
3. GET /api/v1/status/{jobId}
   → Check completion
4. POST /api/v1/search
   → Search scraped data
```

### Workflow 2: Scheduled Scraping
```
1. POST /api/v1/scrape (with future schedule)
   → Job saved as PENDING
2. Wait until scheduled time
3. Automatic execution by scheduler
4. GET /api/v1/status/{jobId}
   → Verify completion
```

### Workflow 3: Multiple URL Scraping
```json
{
  "urls": [
    "https://en.wikipedia.org/wiki/Technology",
    "https://en.wikipedia.org/wiki/Computer",
    "https://en.wikipedia.org/wiki/Software"
  ],
  "keywords": ["technology", "computer", "programming"]
}
```

## Troubleshooting

### Issue: Application won't start

**Error:** `Error: JAVA_HOME is not defined`
**Solution:**
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17

# Linux/Mac
export JAVA_HOME=/usr/lib/jvm/java-17
```

### Issue: Build fails

**Error:** `Failed to execute goal`
**Solution:**
```bash
# Clean and rebuild
mvn clean
mvn install -U
```

### Issue: Port 8080 already in use

**Solution 1:** Kill the process using port 8080
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -i :8080
kill -9 <PID>
```

**Solution 2:** Change port in `application.properties`
```properties
server.port=8081
```

### Issue: Scraping takes too long

**Possible Causes:**
- Slow network connection
- Target website is slow
- Multiple URLs being scraped

**Solution:** Wait for job completion or check logs
```bash
# View logs
tail -f logs/application.log
```

### Issue: No search results

**Checklist:**
1. ✅ Did scraping job complete? (`status: "completed"`)
2. ✅ Were keywords found? (check `keywordsFound` array)
3. ✅ Is search prefix correct? (case-insensitive)
4. ✅ Is limit high enough?

## Performance Tips

### 1. Optimal Keywords
- Use common, relevant keywords
- 3-5 keywords per scrape
- Avoid very rare terms

### 2. URL Selection
- Choose content-rich pages
- Avoid heavy media sites
- Wikipedia is great for testing

### 3. Search Prefix
- Start with 3-4 characters
- Use common word beginnings
- "tech", "comp", "prog" work well

## Next Steps

After successfully running the quick start:

1. **Explore the Code**
   - Read through service classes
   - Understand Trie implementation
   - Review test cases

2. **Experiment**
   - Try different URLs
   - Test various keywords
   - Experiment with search prefixes

3. **Read Documentation**
   - README.md: Full documentation
   - ARCHITECTURE.md: Design details
   - JavaDoc: Code documentation

4. **Extend Functionality**
   - Add new endpoints
   - Implement new features
   - Write more tests

## Support

For issues or questions:
1. Check README.md
2. Review ARCHITECTURE.md
3. Read JavaDoc comments
4. Check application logs

---

**Happy Coding! 🚀**
