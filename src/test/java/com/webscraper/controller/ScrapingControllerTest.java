package com.webscraper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webscraper.dto.*;
import com.webscraper.service.ScrapingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for ScrapingController.
 */
@WebMvcTest(ScrapingController.class)
class ScrapingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ScrapingService scrapingService;

    @Test
    void testInitiateScraping_Success() throws Exception {
        // Given
        ScrapeRequest request = ScrapeRequest.builder()
                .urls(Arrays.asList("https://example.com"))
                .keywords(Arrays.asList("technology"))
                .build();

        ScrapeResponse response = ScrapeResponse.builder()
                .status("success")
                .message("Scraping initiated successfully.")
                .jobId("test-job-123")
                .scheduledAt(LocalDateTime.now())
                .build();

        when(scrapingService.initiateScraping(any(ScrapeRequest.class)))
                .thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/scrape")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.jobId").value("test-job-123"));
    }

    @Test
    void testInitiateScraping_ValidationError() throws Exception {
        // Given - empty URLs list
        ScrapeRequest request = ScrapeRequest.builder()
                .urls(Arrays.asList())
                .keywords(Arrays.asList("technology"))
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/scrape")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSearch_Success() throws Exception {
        // Given
        SearchRequest request = SearchRequest.builder()
                .prefix("tech")
                .limit(5)
                .build();

        List<SearchResponse.SearchResult> results = Arrays.asList(
                SearchResponse.SearchResult.builder()
                        .url("https://example.com")
                        .matchedContent("Technology trends...")
                        .timestamp(LocalDateTime.now())
                        .build()
        );

        SearchResponse response = SearchResponse.builder()
                .status("success")
                .results(results)
                .build();

        when(scrapingService.search(any(SearchRequest.class)))
                .thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.results").isArray());
    }

    @Test
    void testGetJobStatus_Success() throws Exception {
        // Given
        JobStatusResponse response = JobStatusResponse.builder()
                .status("completed")
                .jobId("test-job-123")
                .urlsScraped(Arrays.asList("https://example.com"))
                .keywordsFound(Arrays.asList("technology"))
                .dataSize("1.5 MB")
                .finishedAt(LocalDateTime.now())
                .build();

        when(scrapingService.getJobStatus("test-job-123"))
                .thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/v1/status/test-job-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("completed"))
                .andExpect(jsonPath("$.jobId").value("test-job-123"));
    }

    @Test
    void testHealthCheck() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Web Scraper Service is running!"));
    }
}
