package com.webscraper.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    @NotBlank(message = "Prefix cannot be blank")
    private String prefix;

    @Min(value = 1, message = "Limit must be at least 1")
    private Integer limit = 10;
}
