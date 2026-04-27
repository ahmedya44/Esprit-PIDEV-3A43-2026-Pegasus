package com.pegasus.entities;

public class MenuItem {
    private String name;
    private String description;
    private double price;
    private String category;
    private String imageUrl;
    
    public MenuItem(String name, String description, double price, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = "All"; // Catégorie par défaut
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public double getPrice() {
        return price;
    }
    
    public String getCategory() {
        return category;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getImageName() {
        return imageUrl;
    }
}
