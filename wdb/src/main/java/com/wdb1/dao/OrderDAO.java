package com.wdb1.dao;

import com.wdb1.entity.Order;
import com.wdb1.entity.OrderItem;
import com.wdb1.entity.Cart;
import com.wdb1.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public int createOrder(int userId, List<Cart> cartItems) {
        String orderNo = System.currentTimeMillis() + "";
        double total = 0;
        for (Cart item : cartItems) total += item.getSubtotal();
        
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            String orderSql = "INSERT INTO orders (user_id, order_no, total_price, status) VALUES (?, ?, ?, 'unpaid')";
            int orderId;
            try (PreparedStatement ps = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setString(2, orderNo);
                ps.setDouble(3, total);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) { rs.next(); orderId = rs.getInt(1); }
            }

            String itemSql = "INSERT INTO order_items (order_id, clothes_id, clothes_name, price, quantity, size) VALUES (?,?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(itemSql)) {
                for (Cart item : cartItems) {
                    ps.setInt(1, orderId);
                    ps.setInt(2, item.getClothesId());
                    ps.setString(3, item.getClothesName());
                    ps.setDouble(4, item.getClothesPrice());
                    ps.setInt(5, item.getQuantity());
                    ps.setString(6, item.getSize());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            conn.commit();
            return orderId;
        } catch (SQLException e) {
            if (conn != null) { try { conn.rollback(); } catch (SQLException ex) {} }
            e.printStackTrace();
        } finally {
            if (conn != null) { try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) {} }
        }
        return -1;
    }

    public List<Order> findByUserId(int userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, u.username as userName FROM orders o join users u ON o.user_id = u.id WHERE o.user_id = ? ORDER BY o.create_time DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(rowToOrder(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Order> findAll() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, u.username as userName FROM orders o join users u ON o.user_id = u.id ORDER BY o.create_time DESC";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(rowToOrder(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Order> search(String keyword, String status) {
        StringBuilder sql = new StringBuilder("SELECT o.*, u.username as userName FROM orders o join users u ON o.user_id = u.id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (o.order_no LIKE ? OR u.username LIKE ?)");
            params.add("%" + keyword.trim() + "%");
            params.add("%" + keyword.trim() + "%");
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND o.status = ?");
            params.add(status.trim());
        }
        sql.append(" ORDER BY o.create_time DESC");
        List<Order> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(rowToOrder(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Order getById(int id) {
        String sql = "SELECT o.*, u.username as userName FROM orders o join users u ON o.user_id = u.id WHERE o.id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rowToOrder(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean pay(int id) {
        String sql = "UPDATE orders SET status = 'unshipped', pay_time = NOW() WHERE id = ? AND status = 'unpaid'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean ship(int id) {
        String sql = "UPDATE orders SET status = 'shipped', ship_time = NOW() WHERE id = ? AND status = 'unshipped'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean receive(int id) {
        String sql = "UPDATE orders SET status = 'received', receive_time = NOW() WHERE id = ? AND status = 'shipped'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<OrderItem> findItemsByOrderId(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setClothesId(rs.getInt("clothes_id"));
                    item.setClothesName(rs.getString("clothes_name"));
                    item.setPrice(rs.getDouble("price"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setSize(rs.getString("size"));
                    list.add(item);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    private Order rowToOrder(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setId(rs.getInt("id"));
        o.setUserId(rs.getInt("user_id"));
        o.setOrderNo(rs.getString("order_no"));
        o.setTotalPrice(rs.getDouble("total_price"));
        o.setStatus(rs.getString("status"));
        o.setPayTime(rs.getTimestamp("pay_time"));
        o.setShipTime(rs.getTimestamp("ship_time"));
        o.setReceiveTime(rs.getTimestamp("receive_time"));
        o.setCreateTime(rs.getTimestamp("create_time"));
        o.setUserName(rs.getString("userName"));
        return o;
    }
}
