package com.grocery;

import java.util.List;

/**
 * Storage interface - follows Dependency Inversion Principle (DIP)
 * High-level modules (App) depend on this abstraction, not concrete implementations
 */
public interface Storage {
    List<Item> loadItems();
    void saveItems(List<Item> items);
}