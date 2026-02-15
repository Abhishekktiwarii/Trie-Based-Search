package com.webscraper.repository;

import com.webscraper.entity.ScrapedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapedDataRepository extends JpaRepository<ScrapedData, Long> {

    List<ScrapedData> findByJobId(String jobId);

    @Query("SELECT DISTINCT sd.keyword FROM ScrapedData sd WHERE sd.keyword IS NOT NULL")
    List<String> findAllKeywords();

    @Query("SELECT sd FROM ScrapedData sd WHERE LOWER(sd.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ScrapedData> searchByKeyword(@Param("keyword") String keyword);

    List<ScrapedData> findByKeywordContainingIgnoreCase(String keyword);
}
