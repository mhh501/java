package com.wdb1.entity;

import java.sql.Timestamp;

public class Order {
    private int id;
    private int userId;
    private String orderNo;
    private double totalPrice;
    private String status;
    private Timestamp payTime;
    private Timestamp shipTime;
    private Timestamp receiveTime;
    private Timestamp createTime;
    private String userName;

    public Order() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Timestamp getPayTime() { return payTime; }
    public void setPayTime(Timestamp payTime) { this.payTime = payTime; }
    public Timestamp getShipTime() { return shipTime; }
    public void setShipTime(Timestamp shipTime) { this.shipTime = shipTime; }
    public Timestamp getReceiveTime() { return receiveTime; }
    public void setReceiveTime(Timestamp receiveTime) { this.receiveTime = receiveTime; }
    public Timestamp getCreateTime() { return createTime; }
    public void setCreateTime(Timestamp createTime) { this.createTime = createTime; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getStatusText() {
        switch (status) {
            case "unpaid": return "未支付";
            case "unshipped": return "未发货";
            case "shipped": return "已发货";
            case "received": return "已收货";
            default: return status;
        }
    }
}
