package com.webscraper.trie;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a node in the Trie data structure.
 * Each node contains a map of children and a flag indicating if it's the end of a word.
 */
@Data
public class TrieNode {

    private Map<Character, TrieNode> children;
    private boolean isEndOfWord;
    private String fullWord;

    public TrieNode() {
        this.children = new HashMap<>();
        this.isEndOfWord = false;
        this.fullWord = null;
    }

    public TrieNode getChild(char c) {
        return children.get(c);
    }

    public void addChild(char c, TrieNode node) {
        children.put(c, node);
    }

    public boolean hasChild(char c) {
        return children.containsKey(c);
    }
}
