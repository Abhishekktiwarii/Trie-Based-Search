package com.webscraper.service;

import com.webscraper.entity.ScrapedData;
import com.webscraper.exception.ScrapingException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service for web scraping operations using JSoup.
 */
@Service
@Slf4j
public class WebScraperService {

    private static final int TIMEOUT_MS = 10000;
    private static final int MAX_CONTENT_LENGTH = 5000;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";

    /**
     * Scrape a URL and extract content matching the given keywords.
     *
     * @param url      the URL to scrape
     * @param keywords keywords to search for
     * @param jobId    the job ID
     * @return list of ScrapedData entities
     */
    public List<ScrapedData> scrapeUrl(String url, List<String> keywords, String jobId) {
        List<ScrapedData> results = new ArrayList<>();

        try {
            log.info("Scraping URL: {} for job: {}", url, jobId);
            
            Document document = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .timeout(TIMEOUT_MS)
                    .get();

            String fullContent = document.body().text();
            
            for (String keyword : keywords) {
                String matchedContent = extractMatchedContent(fullContent, keyword);
                
                if (!matchedContent.isEmpty()) {
                    ScrapedData data = ScrapedData.builder()
                            .jobId(jobId)
                            .url(url)
                            .content(truncateContent(fullContent))
                            .matchedContent(matchedContent)
                            .keyword(keyword)
                            .timestamp(LocalDateTime.now())
                            .dataSize((long) fullContent.length())
                            .build();
                    
                    results.add(data);
                    log.info("Found keyword '{}' in URL: {}", keyword, url);
                }
            }

        } catch (Exception e) {
            log.error("Error scraping URL: {} - {}", url, e.getMessage());
            throw new ScrapingException("Failed to scrape URL: " + url, e);
        }

        return results;
    }

    /**
     * Extract content around the matched keyword.
     *
     * @param content the full content
     * @param keyword the keyword to search
     * @return extracted content snippet
     */
    private String extractMatchedContent(String content, String keyword) {
        if (content == null || keyword == null) {
            return "";
        }

        Pattern pattern = Pattern.compile(".{0,100}" + Pattern.quote(keyword) + ".{0,100}", 
                                         Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            return matcher.group().trim();
        }

        // Fallback: check if keyword exists anywhere in content
        if (content.toLowerCase().contains(keyword.toLowerCase())) {
            int index = content.toLowerCase().indexOf(keyword.toLowerCase());
            int start = Math.max(0, index - 100);
            int end = Math.min(content.length(), index + keyword.length() + 100);
            return content.substring(start, end).trim();
        }

        return "";
    }

    /**
     * Truncate content to a reasonable length for storage.
     *
     * @param content the content to truncate
     * @return truncated content
     */
    private String truncateContent(String content) {
        if (content == null) {
            return "";
        }
        return content.length() > MAX_CONTENT_LENGTH 
                ? content.substring(0, MAX_CONTENT_LENGTH) + "..." 
                : content;
    }

    /**
     * Validate if a URL is accessible.
     *
     * @param url the URL to validate
     * @return true if accessible, false otherwise
     */
    public boolean isUrlAccessible(String url) {
        try {
            Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .timeout(TIMEOUT_MS)
                    .get();
            return true;
        } catch (Exception e) {
            log.warn("URL not accessible: {} - {}", url, e.getMessage());
            return false;
        }
    }
}
