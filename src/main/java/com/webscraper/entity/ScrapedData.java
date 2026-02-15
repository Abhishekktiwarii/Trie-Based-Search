package com.webscraper.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "scraped_data", indexes = {
        @Index(name = "idx_job_id", columnList = "jobId"),
        @Index(name = "idx_url", columnList = "url")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrapedData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String jobId;

    @Column(nullable = false, length = 2048)
    private String url;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String matchedContent;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(length = 500)
    private String keyword;

    @Column(name = "data_size")
    private Long dataSize;
}
