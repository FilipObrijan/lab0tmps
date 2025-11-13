package com.grocery;

import java.util.ArrayList;
import java.util.List;

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