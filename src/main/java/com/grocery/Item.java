package com.grocery;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Item model class - follows Single Responsibility Principle (SRP)
 * Responsibility: Data representation for grocery items only
 */
public class Item {
    private final int id;
    private final String name;
    private boolean checked;
    private final String createdAt;
    
    public Item(int id, String name) {
        this.id = id;
        this.name = name;
        this.checked = false;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
    
    // Constructor for loading from storage
    public Item(int id, String name, boolean checked, String createdAt) {
        this.id = id;
        this.name = name;
        this.checked = checked;
        this.createdAt = createdAt;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isChecked() {
        return checked;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        String status = checked ? "[✓]" : "[ ]";
        return String.format("%d. %s %s (added: %s)", id, status, name, createdAt.substring(0, 10));
    }
}