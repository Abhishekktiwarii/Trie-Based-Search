package com.webscraper.entity;

import com.webscraper.enums.JobStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "scraping_job")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrapingJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String jobId;

    @ElementCollection
    @CollectionTable(name = "job_urls", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "url", length = 2048)
    private List<String> urls;

    @ElementCollection
    @CollectionTable(name = "job_keywords", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "keyword")
    private List<String> keywords;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;

    private LocalDateTime scheduledAt;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private Long dataSize;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;
}
