package com.webscraper.trie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Trie data structure.
 */
class TrieTest {

    private Trie trie;

    @BeforeEach
    void setUp() {
        trie = new Trie();
    }

    @Test
    void testInsertAndSearch() {
        // Given
        trie.insert("technology");
        trie.insert("tech");
        trie.insert("innovation");

        // When & Then
        assertTrue(trie.search("technology"));
        assertTrue(trie.search("tech"));
        assertTrue(trie.search("innovation"));
        assertFalse(trie.search("techno"));
    }

    @Test
    void testStartsWith() {
        // Given
        trie.insert("technology");
        trie.insert("technical");
        trie.insert("technique");

        // When & Then
        assertTrue(trie.startsWith("tech"));
        assertTrue(trie.startsWith("techn"));
        assertFalse(trie.startsWith("inno"));
    }

    @Test
    void testFindWordsWithPrefix() {
        // Given
        trie.insert("technology");
        trie.insert("technical");
        trie.insert("technique");
        trie.insert("innovation");

        // When
        List<String> results = trie.findWordsWithPrefix("tech");

        // Then
        assertEquals(3, results.size());
        assertTrue(results.contains("technology"));
        assertTrue(results.contains("technical"));
        assertTrue(results.contains("technique"));
    }

    @Test
    void testFindWordsWithPrefixLimit() {
        // Given
        trie.insert("technology");
        trie.insert("technical");
        trie.insert("technique");
        trie.insert("techno");

        // When
        List<String> results = trie.findWordsWithPrefix("tech", 2);

        // Then
        assertEquals(2, results.size());
    }

    @Test
    void testInsertNullOrEmpty() {
        // When
        trie.insert(null);
        trie.insert("");
        trie.insert("   ");

        // Then
        assertEquals(0, trie.size());
    }

    @Test
    void testCaseInsensitivity() {
        // Given
        trie.insert("Technology");

        // When & Then
        assertTrue(trie.search("technology"));
        assertTrue(trie.search("TECHNOLOGY"));
        assertTrue(trie.startsWith("tech"));
        assertTrue(trie.startsWith("TECH"));
    }

    @Test
    void testSize() {
        // Given
        assertEquals(0, trie.size());

        // When
        trie.insert("tech");
        trie.insert("technology");
        trie.insert("innovation");

        // Then
        assertEquals(3, trie.size());
    }

    @Test
    void testClear() {
        // Given
        trie.insert("technology");
        trie.insert("innovation");
        assertEquals(2, trie.size());

        // When
        trie.clear();

        // Then
        assertEquals(0, trie.size());
        assertFalse(trie.search("technology"));
    }

    @Test
    void testEmptyPrefixSearch() {
        // Given
        trie.insert("technology");

        // When
        List<String> results = trie.findWordsWithPrefix("");

        // Then
        assertTrue(results.isEmpty());
    }

    @Test
    void testNoPrefixMatch() {
        // Given
        trie.insert("technology");
        trie.insert("innovation");

        // When
        List<String> results = trie.findWordsWithPrefix("xyz");

        // Then
        assertTrue(results.isEmpty());
    }
}
