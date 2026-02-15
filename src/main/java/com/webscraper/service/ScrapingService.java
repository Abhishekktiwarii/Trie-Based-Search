package com.webscraper.service;

import com.webscraper.dto.*;
import com.webscraper.entity.ScrapedData;
import com.webscraper.entity.ScrapingJob;
import com.webscraper.enums.JobStatus;
import com.webscraper.exception.JobNotFoundException;
import com.webscraper.repository.ScrapedDataRepository;
import com.webscraper.repository.ScrapingJobRepository;
import com.webscraper.trie.Trie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for managing scraping jobs and executing scraping operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapingService {

    private final ScrapingJobRepository jobRepository;
    private final ScrapedDataRepository dataRepository;
    private final WebScraperService webScraperService;
    private final Trie trie;

    /**
     * Initialize a new scraping job.
     *
     * @param request the scrape request
     * @return scrape response with job details
     */
    @Transactional
    public ScrapeResponse initiateScraping(ScrapeRequest request) {
        String jobId = UUID.randomUUID().toString();
        
        LocalDateTime scheduledTime = request.getSchedule() != null 
                ? request.getSchedule() 
                : LocalDateTime.now();

        ScrapingJob job = ScrapingJob.builder()
                .jobId(jobId)
                .urls(request.getUrls())
                .keywords(request.getKeywords())
                .status(JobStatus.PENDING)
                .scheduledAt(scheduledTime)
                .build();

        jobRepository.save(job);

        // Execute immediately if no schedule specified or schedule is in the past
        if (scheduledTime.isBefore(LocalDateTime.now().plusMinutes(1))) {
            executeScrapingAsync(jobId);
        }

        log.info("Scraping job initiated: {}", jobId);

        return ScrapeResponse.builder()
                .status("success")
                .message("Scraping initiated successfully.")
                .jobId(jobId)
                .scheduledAt(scheduledTime)
                .build();
    }

    /**
     * Execute scraping job asynchronously.
     *
     * @param jobId the job ID
     */
    @Async
    @Transactional
    public void executeScrapingAsync(String jobId) {
        ScrapingJob job = jobRepository.findByJobId(jobId)
                .orElseThrow(() -> new JobNotFoundException("Job not found: " + jobId));

        try {
            log.info("Starting scraping job: {}", jobId);
            
            job.setStatus(JobStatus.IN_PROGRESS);
            job.setStartedAt(LocalDateTime.now());
            jobRepository.save(job);

            List<ScrapedData> allScrapedData = new ArrayList<>();
            long totalDataSize = 0;

            for (String url : job.getUrls()) {
                try {
                    List<ScrapedData> scrapedData = webScraperService.scrapeUrl(
                            url, job.getKeywords(), jobId);
                    
                    allScrapedData.addAll(scrapedData);
                    
                    for (ScrapedData data : scrapedData) {
                        totalDataSize += data.getDataSize() != null ? data.getDataSize() : 0;
                    }
                    
                } catch (Exception e) {
                    log.error("Error scraping URL: {} for job: {}", url, jobId, e);
                }
            }

            // Save all scraped data
            dataRepository.saveAll(allScrapedData);

            // Index keywords in Trie
            indexKeywordsInTrie(allScrapedData);

            // Update job status
            job.setStatus(JobStatus.COMPLETED);
            job.setFinishedAt(LocalDateTime.now());
            job.setDataSize(totalDataSize);
            jobRepository.save(job);

            log.info("Scraping job completed: {} - Total data size: {} bytes", jobId, totalDataSize);

        } catch (Exception e) {
            log.error("Scraping job failed: {}", jobId, e);
            job.setStatus(JobStatus.FAILED);
            job.setErrorMessage(e.getMessage());
            job.setFinishedAt(LocalDateTime.now());
            jobRepository.save(job);
        }
    }

    /**
     * Index keywords from scraped data into the Trie.
     *
     * @param scrapedDataList list of scraped data
     */
    private void indexKeywordsInTrie(List<ScrapedData> scrapedDataList) {
        for (ScrapedData data : scrapedDataList) {
            if (data.getKeyword() != null && !data.getKeyword().isEmpty()) {
                trie.insert(data.getKeyword());
            }
            
            // Also index words from matched content
            if (data.getMatchedContent() != null) {
                String[] words = data.getMatchedContent().toLowerCase()
                        .split("[\\s\\p{Punct}]+");
                for (String word : words) {
                    if (word.length() > 2) { // Only index words longer than 2 characters
                        trie.insert(word);
                    }
                }
            }
        }
        log.info("Indexed keywords in Trie. Total words: {}", trie.size());
    }

    /**
     * Get the status of a scraping job.
     *
     * @param jobId the job ID
     * @return job status response
     */
    @Transactional(readOnly = true)
    public JobStatusResponse getJobStatus(String jobId) {
        ScrapingJob job = jobRepository.findByJobId(jobId)
                .orElseThrow(() -> new JobNotFoundException("Job not found: " + jobId));

        List<String> keywordsFound = dataRepository.findByJobId(jobId)
                .stream()
                .map(ScrapedData::getKeyword)
                .distinct()
                .collect(Collectors.toList());

        String dataSize = formatDataSize(job.getDataSize());

        return JobStatusResponse.builder()
                .status(job.getStatus().name().toLowerCase())
                .jobId(job.getJobId())
                .urlsScraped(job.getUrls())
                .keywordsFound(keywordsFound)
                .dataSize(dataSize)
                .finishedAt(job.getFinishedAt())
                .errorMessage(job.getErrorMessage())
                .build();
    }

    /**
     * Search for keywords using Trie and retrieve matching scraped data.
     *
     * @param request the search request
     * @return search response with results
     */
    @Transactional(readOnly = true)
    public SearchResponse search(SearchRequest request) {
        // Find matching keywords using Trie
        List<String> matchingKeywords = trie.findWordsWithPrefix(
                request.getPrefix(), request.getLimit() * 2);

        if (matchingKeywords.isEmpty()) {
            return SearchResponse.builder()
                    .status("success")
                    .results(new ArrayList<>())
                    .build();
        }

        // Retrieve scraped data for matching keywords
        List<SearchResponse.SearchResult> results = new ArrayList<>();
        
        for (String keyword : matchingKeywords) {
            List<ScrapedData> dataList = dataRepository.findByKeywordContainingIgnoreCase(keyword);
            
            for (ScrapedData data : dataList) {
                if (results.size() >= request.getLimit()) {
                    break;
                }
                
                results.add(SearchResponse.SearchResult.builder()
                        .url(data.getUrl())
                        .matchedContent(data.getMatchedContent())
                        .timestamp(data.getTimestamp())
                        .build());
            }
            
            if (results.size() >= request.getLimit()) {
                break;
            }
        }

        log.info("Search completed for prefix: '{}' - Found {} results", 
                request.getPrefix(), results.size());

        return SearchResponse.builder()
                .status("success")
                .results(results)
                .build();
    }

    /**
     * Format data size in human-readable format.
     *
     * @param bytes size in bytes
     * @return formatted string
     */
    private String formatDataSize(Long bytes) {
        if (bytes == null || bytes == 0) {
            return "0 B";
        }
        
        String[] units = {"B", "KB", "MB", "GB"};
        int unitIndex = 0;
        double size = bytes;
        
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        
        return String.format("%.2f %s", size, units[unitIndex]);
    }
}
