package com.project.entity;

public class Clothes {
    private int id;
    private String name;
    private String style;
    private String category;
    private String size;
    private double price;
    private int stock;
    private String image;
    private String description;

    public Clothes() {}
    public Clothes(int id, String name, String style, String category, String size, double price, int stock, String image, String description) {
        this.id = id;
        this.name = name;
        this.style = style;
        this.category = category;
        this.size = size;
        this.price = price;
        this.stock = stock;
        this.image = image;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStyle() { return style; }
    public void setStyle(String style) { this.style = style; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
