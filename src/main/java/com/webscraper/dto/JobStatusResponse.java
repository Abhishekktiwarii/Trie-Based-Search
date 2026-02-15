package com.webscraper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobStatusResponse {

    private String status;
    private String jobId;
    private List<String> urlsScraped;
    private List<String> keywordsFound;
    private String dataSize;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime finishedAt;

    private String errorMessage;
}
