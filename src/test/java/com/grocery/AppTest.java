package com.grocery;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

/**
 * Unit tests demonstrating Dependency Inversion Principle (DIP)
 * Tests use MemoryStorage instead of FileStorage, proving that App
 * doesn't depend on concrete storage implementations
 */
public class AppTest {
    private App app;
    private Storage storage;
    
    @Before
    public void setUp() {
        // DIP in action: Using MemoryStorage for tests
        storage = new MemoryStorage();
        app = new App(storage);
    }
    
    @Test
    public void testAddCreatesItem() {
        Item item = app.add("Bananas");
        
        assertEquals(1, item.getId());
        assertEquals("Bananas", item.getName());
        assertFalse(item.isChecked());
        
        List<Item> items = app.list();
        assertEquals(1, items.size());
        assertEquals("Bananas", items.get(0).getName());
    }
    
    @Test
    public void testAddMultipleItems() {
        app.add("Bananas");
        app.add("Apples");
        
        List<Item> items = app.list();
        assertEquals(2, items.size());
        assertEquals("Bananas", items.get(0).getName());
        assertEquals("Apples", items.get(1).getName());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyItemThrowsException() {
        app.add("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddNullItemThrowsException() {
        app.add(null);
    }
    
    @Test
    public void testCheckMarksItemAsCompleted() {
        app.add("Bananas");
        Item checkedItem = app.check(1);
        
        assertTrue(checkedItem.isChecked());
        assertEquals("Bananas", checkedItem.getName());
        
        List<Item> items = app.list();
        assertTrue(items.get(0).isChecked());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCheckNonExistentItemThrowsException() {
        app.check(999);
    }
    
    @Test
    public void testRemoveDeletesItem() {
        app.add("Bananas");
        app.add("Apples");
        
        app.remove(1);
        
        List<Item> items = app.list();
        assertEquals(1, items.size());
        assertEquals("Apples", items.get(0).getName());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNonExistentItemThrowsException() {
        app.remove(999);
    }
    
    @Test
    public void testListOrderingIsCreationOrder() {
        app.add("First");
        app.add("Second");
        app.add("Third");
        
        List<Item> items = app.list();
        assertEquals("First", items.get(0).getName());
        assertEquals("Second", items.get(1).getName());
        assertEquals("Third", items.get(2).getName());
    }
    
    @Test
    public void testClearRemovesAllItems() {
        app.add("Bananas");
        app.add("Apples");
        
        app.clear();
        
        List<Item> items = app.list();
        assertTrue(items.isEmpty());
    }
    
    @Test
    public void testEmptyListReturnsEmptyList() {
        List<Item> items = app.list();
        assertTrue(items.isEmpty());
    }
}