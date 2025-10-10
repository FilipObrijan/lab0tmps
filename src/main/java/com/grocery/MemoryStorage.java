package com.grocery;

import java.util.ArrayList;
import java.util.List;

/**
 * MemoryStorage implementation - follows DIP and SRP
 * Responsibility: In-memory storage for testing
 * DIP: Implements Storage interface, can be swapped without changing App
 */
public class MemoryStorage implements Storage {
    private final List<Item> items = new ArrayList<>();
    
    @Override
    public List<Item> loadItems() {
        return new ArrayList<>(items); // Return copy to prevent external modification
    }
    
    @Override
    public void saveItems(List<Item> items) {
        this.items.clear();
        this.items.addAll(items);
    }
}