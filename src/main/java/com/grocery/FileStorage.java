package com.grocery;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * FileStorage implementation - follows DIP and SRP
 * Responsibility: File-based persistence using simple text format
 * DIP: Implements Storage interface, can be swapped without changing App
 * OCP: New storage types can be added without modifying existing code
 */
public class FileStorage implements Storage {
    private final String filePath;
    
    public FileStorage(String filePath) {
        this.filePath = filePath;
    }
    
    @Override
    public List<Item> loadItems() {
        try {
            if (!Files.exists(Paths.get(filePath))) {
                return new ArrayList<>();
            }
            
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            List<Item> items = new ArrayList<>();
            
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    boolean checked = Boolean.parseBoolean(parts[2]);
                    String createdAt = parts[3];
                    items.add(new Item(id, name, checked, createdAt));
                }
            }
            
            return items;
            
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading items: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    @Override
    public void saveItems(List<Item> items) {
        try {
            List<String> lines = new ArrayList<>();
            for (Item item : items) {
                String line = item.getId() + "|" + item.getName() + "|" + 
                             item.isChecked() + "|" + item.getCreatedAt();
                lines.add(line);
            }
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            System.err.println("Error saving items: " + e.getMessage());
        }
    }
}