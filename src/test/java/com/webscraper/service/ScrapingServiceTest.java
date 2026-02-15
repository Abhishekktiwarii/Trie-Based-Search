package com.webscraper.service;

import com.webscraper.dto.*;
import com.webscraper.entity.ScrapedData;
import com.webscraper.entity.ScrapingJob;
import com.webscraper.enums.JobStatus;
import com.webscraper.exception.JobNotFoundException;
import com.webscraper.repository.ScrapedDataRepository;
import com.webscraper.repository.ScrapingJobRepository;
import com.webscraper.trie.Trie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ScrapingService.
 */
@ExtendWith(MockitoExtension.class)
class ScrapingServiceTest {

    @Mock
    private ScrapingJobRepository jobRepository;

    @Mock
    private ScrapedDataRepository dataRepository;

    @Mock
    private WebScraperService webScraperService;

    @Mock
    private Trie trie;

    @InjectMocks
    private ScrapingService scrapingService;

    private ScrapeRequest scrapeRequest;
    private ScrapingJob scrapingJob;

    @BeforeEach
    void setUp() {
        scrapeRequest = ScrapeRequest.builder()
                .urls(Arrays.asList("https://example.com", "https://example2.com"))
                .keywords(Arrays.asList("technology", "innovation"))
                .build();

        scrapingJob = ScrapingJob.builder()
                .id(1L)
                .jobId("test-job-123")
                .urls(scrapeRequest.getUrls())
                .keywords(scrapeRequest.getKeywords())
                .status(JobStatus.PENDING)
                .scheduledAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testInitiateScraping_Success() {
        // Given
        when(jobRepository.save(any(ScrapingJob.class))).thenReturn(scrapingJob);

        // When
        ScrapeResponse response = scrapingService.initiateScraping(scrapeRequest);

        // Then
        assertNotNull(response);
        assertEquals("success", response.getStatus());
        assertEquals("Scraping initiated successfully.", response.getMessage());
        assertNotNull(response.getJobId());
        verify(jobRepository, times(1)).save(any(ScrapingJob.class));
    }

    @Test
    void testGetJobStatus_Success() {
        // Given
        when(jobRepository.findByJobId("test-job-123")).thenReturn(Optional.of(scrapingJob));
        when(dataRepository.findByJobId("test-job-123")).thenReturn(Arrays.asList());

        // When
        JobStatusResponse response = scrapingService.getJobStatus("test-job-123");

        // Then
        assertNotNull(response);
        assertEquals("pending", response.getStatus());
        assertEquals("test-job-123", response.getJobId());
        verify(jobRepository, times(1)).findByJobId("test-job-123");
    }

    @Test
    void testGetJobStatus_JobNotFound() {
        // Given
        when(jobRepository.findByJobId("non-existent")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(JobNotFoundException.class, 
            () -> scrapingService.getJobStatus("non-existent"));
    }

    @Test
    void testSearch_WithResults() {
        // Given
        SearchRequest searchRequest = SearchRequest.builder()
                .prefix("tech")
                .limit(5)
                .build();

        List<String> matchingKeywords = Arrays.asList("technology", "technical");
        when(trie.findWordsWithPrefix("tech", 10)).thenReturn(matchingKeywords);

        ScrapedData data1 = ScrapedData.builder()
                .url("https://example.com")
                .matchedContent("Latest technology trends...")
                .timestamp(LocalDateTime.now())
                .build();

        when(dataRepository.findByKeywordContainingIgnoreCase("technology"))
                .thenReturn(Arrays.asList(data1));
        when(dataRepository.findByKeywordContainingIgnoreCase("technical"))
                .thenReturn(Arrays.asList());

        // When
        SearchResponse response = scrapingService.search(searchRequest);

        // Then
        assertNotNull(response);
        assertEquals("success", response.getStatus());
        assertFalse(response.getResults().isEmpty());
        verify(trie, times(1)).findWordsWithPrefix("tech", 10);
    }

    @Test
    void testSearch_NoResults() {
        // Given
        SearchRequest searchRequest = SearchRequest.builder()
                .prefix("xyz")
                .limit(5)
                .build();

        when(trie.findWordsWithPrefix("xyz", 10)).thenReturn(Arrays.asList());

        // When
        SearchResponse response = scrapingService.search(searchRequest);

        // Then
        assertNotNull(response);
        assertEquals("success", response.getStatus());
        assertTrue(response.getResults().isEmpty());
    }
}
