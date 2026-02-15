package com.webscraper.trie;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Trie data structure implementation for efficient prefix-based search and autocompletion.
 * Thread-safe implementation using synchronized methods.
 */
@Component
public class Trie {

    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    /**
     * Insert a word into the Trie.
     *
     * @param word the word to insert
     */
    public synchronized void insert(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }

        TrieNode current = root;
        String normalizedWord = word.toLowerCase().trim();

        for (char c : normalizedWord.toCharArray()) {
            if (!current.hasChild(c)) {
                current.addChild(c, new TrieNode());
            }
            current = current.getChild(c);
        }

        current.setEndOfWord(true);
        current.setFullWord(normalizedWord);
    }

    /**
     * Search for exact word match in the Trie.
     *
     * @param word the word to search
     * @return true if word exists, false otherwise
     */
    public synchronized boolean search(String word) {
        TrieNode node = searchNode(word);
        return node != null && node.isEndOfWord();
    }

    /**
     * Check if any word in the Trie starts with the given prefix.
     *
     * @param prefix the prefix to search
     * @return true if prefix exists, false otherwise
     */
    public synchronized boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }

    /**
     * Find all words that start with the given prefix.
     *
     * @param prefix the prefix to search
     * @return list of words matching the prefix
     */
    public synchronized List<String> findWordsWithPrefix(String prefix) {
        List<String> results = new ArrayList<>();
        
        if (prefix == null || prefix.isEmpty()) {
            return results;
        }

        String normalizedPrefix = prefix.toLowerCase().trim();
        TrieNode node = searchNode(normalizedPrefix);

        if (node == null) {
            return results;
        }

        collectAllWords(node, normalizedPrefix, results);
        return results;
    }

    /**
     * Find words with prefix up to a specified limit.
     *
     * @param prefix the prefix to search
     * @param limit  maximum number of results
     * @return list of words matching the prefix (limited)
     */
    public synchronized List<String> findWordsWithPrefix(String prefix, int limit) {
        List<String> allResults = findWordsWithPrefix(prefix);
        return allResults.size() <= limit ? allResults : allResults.subList(0, limit);
    }

    /**
     * Helper method to search for a node corresponding to a prefix/word.
     *
     * @param prefix the prefix to search
     * @return the TrieNode if found, null otherwise
     */
    private TrieNode searchNode(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return null;
        }

        TrieNode current = root;
        String normalizedPrefix = prefix.toLowerCase().trim();

        for (char c : normalizedPrefix.toCharArray()) {
            if (!current.hasChild(c)) {
                return null;
            }
            current = current.getChild(c);
        }

        return current;
    }

    /**
     * Recursively collect all words from a given node.
     *
     * @param node    starting node
     * @param prefix  current prefix
     * @param results list to store results
     */
    private void collectAllWords(TrieNode node, String prefix, List<String> results) {
        if (node.isEndOfWord()) {
            results.add(node.getFullWord() != null ? node.getFullWord() : prefix);
        }

        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            collectAllWords(entry.getValue(), prefix + entry.getKey(), results);
        }
    }

    /**
     * Clear all data from the Trie.
     */
    public synchronized void clear() {
        root.getChildren().clear();
    }

    /**
     * Get the total number of words in the Trie.
     *
     * @return total word count
     */
    public synchronized int size() {
        return countWords(root);
    }

    private int countWords(TrieNode node) {
        int count = node.isEndOfWord() ? 1 : 0;
        for (TrieNode child : node.getChildren().values()) {
            count += countWords(child);
        }
        return count;
    }
}
