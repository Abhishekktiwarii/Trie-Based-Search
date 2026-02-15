package com.webscraper.controller;

import com.webscraper.dto.*;
import com.webscraper.service.ScrapingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for web scraping and search operations.
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class ScrapingController {

    private final ScrapingService scrapingService;

    /**
     * Endpoint to initiate web scraping.
     * POST /api/v1/scrape
     *
     * @param request the scrape request containing URLs and keywords
     * @return scrape response with job details
     */
    @PostMapping("/scrape")
    public ResponseEntity<ScrapeResponse> initiateScraping(
            @Valid @RequestBody ScrapeRequest request) {
        
        log.info("Received scraping request for URLs: {}", request.getUrls());
        ScrapeResponse response = scrapingService.initiateScraping(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint to search scraped data using Trie-based prefix search.
     * POST /api/v1/search
     *
     * @param request the search request containing prefix and limit
     * @return search response with matching results
     */
    @PostMapping("/search")
    public ResponseEntity<SearchResponse> search(
            @Valid @RequestBody SearchRequest request) {
        
        log.info("Received search request with prefix: '{}'", request.getPrefix());
        SearchResponse response = scrapingService.search(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to check the status of a scraping job.
     * GET /api/v1/status/{jobId}
     *
     * @param jobId the job ID to check
     * @return job status response
     */
    @GetMapping("/status/{jobId}")
    public ResponseEntity<JobStatusResponse> getJobStatus(
            @PathVariable String jobId) {
        
        log.info("Received status check request for job: {}", jobId);
        JobStatusResponse response = scrapingService.getJobStatus(jobId);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint.
     * GET /api/v1/health
     *
     * @return simple health status
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Web Scraper Service is running!");
    }
}
