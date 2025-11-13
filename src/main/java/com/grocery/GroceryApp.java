package com.grocery;

import java.util.List;

public class GroceryApp {
    private static final String STORAGE_FILE = "grocery-list.json";
    
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            System.exit(1);
        }
        
        // Use FileStorage for persistence (DIP in action)
        Storage storage = new FileStorage(STORAGE_FILE);
        App app = new App(storage);
        
        try {
            String command = args[0].toLowerCase();
            
            switch (command) {
                case "add":
                    handleAdd(app, args);
                    break;
                case "list":
                    handleList(app);
                    break;
                case "check":
                    handleCheck(app, args);
                    break;
                case "remove":
                    handleRemove(app, args);
                    break;
                case "clear":
                    handleClear(app);
                    break;
                case "help":
                    printUsage();
                    break;
                default:
                    System.err.println("Unknown command: " + command);
                    printUsage();
                    System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private static void handleAdd(App app, String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: add \"item name\"");
            System.exit(1);
        }
        
        String itemName = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        Item item = app.add(itemName);
        System.out.println("Added item " + item.getId() + ": " + item.getName());
    }
    
    private static void handleList(App app) {
        List<Item> items = app.list();
        if (items.isEmpty()) {
            System.out.println("No items in your grocery list.");
        } else {
            System.out.println("Grocery List:");
            for (Item item : items) {
                System.out.println("  " + item);
            }
        }
    }
    
    private static void handleCheck(App app, String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: check <id>");
            System.exit(1);
        }
        
        try {
            int id = Integer.parseInt(args[1]);
            Item item = app.check(id);
            System.out.println("Checked off item " + id + ": " + item.getName());
        } catch (NumberFormatException e) {
            System.err.println("Invalid ID: " + args[1]);
            System.exit(1);
        }
    }
    
    private static void handleRemove(App app, String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: remove <id>");
            System.exit(1);
        }
        
        try {
            int id = Integer.parseInt(args[1]);
            app.remove(id);
            System.out.println("Removed item " + id);
        } catch (NumberFormatException e) {
            System.err.println("Invalid ID: " + args[1]);
            System.exit(1);
        }
    }
    
    private static void handleClear(App app) {
        app.clear();
        System.out.println("Cleared all items from grocery list.");
    }
    
    private static void printUsage() {
        System.out.println("Grocery List CLI - SOLID Principles Demo");
        System.out.println("Usage:");
        System.out.println("  add \"item name\"  - Add a new item");
        System.out.println("  list             - List all items");
        System.out.println("  check <id>       - Mark item as checked");
        System.out.println("  remove <id>      - Remove an item");
        System.out.println("  clear            - Remove all items");
        System.out.println("  help             - Show this help");
    }
}