package com.webscraper.config;

import com.webscraper.entity.ScrapingJob;
import com.webscraper.enums.JobStatus;
import com.webscraper.repository.ScrapingJobRepository;
import com.webscraper.service.ScrapingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Scheduled task executor to process pending scraping jobs.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledJobExecutor {

    private final ScrapingJobRepository jobRepository;
    private final ScrapingService scrapingService;

    /**
     * Check for pending jobs that are due for execution.
     * Runs every minute.
     */
    @Scheduled(fixedRate = 60000) // Run every 60 seconds
    public void executePendingJobs() {
        LocalDateTime now = LocalDateTime.now();
        
        List<ScrapingJob> pendingJobs = jobRepository
                .findByStatusAndScheduledAtBefore(JobStatus.PENDING, now);

        if (!pendingJobs.isEmpty()) {
            log.info("Found {} pending jobs to execute", pendingJobs.size());
            
            for (ScrapingJob job : pendingJobs) {
                log.info("Executing scheduled job: {}", job.getJobId());
                scrapingService.executeScrapingAsync(job.getJobId());
            }
        }
    }
}
