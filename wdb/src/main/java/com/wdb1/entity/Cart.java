package com.wdb1.entity;

public class Cart {
    private int id;
    private int userId;
    private int clothesId;
    private int quantity;
    private double price;
    private String size;
    private String clothesName;
    private double clothesPrice;
    private String clothesImage;

    public Cart() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getClothesId() { return clothesId; }
    public void setClothesId(int clothesId) { this.clothesId = clothesId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public String getClothesName() { return clothesName; }
    public void setClothesName(String clothesName) { this.clothesName = clothesName; }
    public double getClothesPrice() { return clothesPrice; }
    public void setClothesPrice(double clothesPrice) { this.clothesPrice = clothesPrice; }
    public String getClothesImage() { return clothesImage; }
    public void setClothesImage(String clothesImage) { this.clothesImage = clothesImage; }
    public double getSubtotal() { return (price > 0 ? price : clothesPrice) * quantity; }
}
