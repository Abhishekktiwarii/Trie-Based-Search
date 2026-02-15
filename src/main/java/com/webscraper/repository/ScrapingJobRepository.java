package com.webscraper.repository;

import com.webscraper.entity.ScrapingJob;
import com.webscraper.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapingJobRepository extends JpaRepository<ScrapingJob, Long> {

    Optional<ScrapingJob> findByJobId(String jobId);

    List<ScrapingJob> findByStatusAndScheduledAtBefore(JobStatus status, LocalDateTime dateTime);

    List<ScrapingJob> findByStatus(JobStatus status);
}
