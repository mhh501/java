package com.pyh1.entity;

public class OrderItem {
    private int id;
    private int orderId;
    private int clothesId;
    private String clothesName;
    private double price;
    private int quantity;
    private String size;

    public OrderItem() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public int getClothesId() { return clothesId; }
    public void setClothesId(int clothesId) { this.clothesId = clothesId; }
    public String getClothesName() { return clothesName; }
    public void setClothesName(String clothesName) { this.clothesName = clothesName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public double getSubtotal() { return price * quantity; }
}
