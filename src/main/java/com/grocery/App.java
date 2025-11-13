package com.grocery;

import java.util.List;
import java.util.Optional;
// still thinking
public class App {
    private final Storage storage;
    private int nextId = 1;
    
    public App(Storage storage) {
        this.storage = storage;
        initializeNextId();
    }
    
    private void initializeNextId() {
        List<Item> items = storage.loadItems();
        nextId = items.stream()
                .mapToInt(Item::getId)
                .max()
                .orElse(0) + 1;
    }
    
    public Item add(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty");
        }
        
        List<Item> items = storage.loadItems();
        Item newItem = new Item(nextId++, name.trim());
        items.add(newItem);
        storage.saveItems(items);
        
        return newItem;
    }
    
    public List<Item> list() {
        return storage.loadItems();
    }
    
    public Item check(int id) {
        List<Item> items = storage.loadItems();
        Optional<Item> itemOpt = items.stream()
                .filter(item -> item.getId() == id)
                .findFirst();
        
        if (!itemOpt.isPresent()) {
            throw new IllegalArgumentException("Item with ID " + id + " not found");
        }
        
        Item item = itemOpt.get();
        item.setChecked(true);
        storage.saveItems(items);
        
        return item;
    }
    
    public void remove(int id) {
        List<Item> items = storage.loadItems();
        boolean removed = items.removeIf(item -> item.getId() == id);
        
        if (!removed) {
            throw new IllegalArgumentException("Item with ID " + id + " not found");
        }
        
        storage.saveItems(items);
    }
    
    public void clear() {
        storage.saveItems(List.of());
    }
}