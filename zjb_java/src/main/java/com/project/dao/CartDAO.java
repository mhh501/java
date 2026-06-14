package com.project.dao;

import com.project.entity.Cart;
import com.project.util.DBHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public List<Cart> findByUserId(int userId) {
        List<Cart> list = new ArrayList<>();
        String sql = "SELECT c.*, cl.name as clothesName, cl.price as clothesPrice, cl.image as clothesImage " +
                     "FROM cart c JOIN clothes cl ON c.clothes_id = cl.id WHERE c.user_id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(rowToCart(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Cart findByUserAndClothes(int userId, int clothesId, String size) {
        String sql = "SELECT c.*, cl.name as clothesName, cl.price as clothesPrice, cl.image as clothesImage " +
                     "FROM cart c JOIN clothes cl ON c.clothes_id = cl.id " +
                     "WHERE c.user_id = ? AND c.clothes_id = ? AND c.size = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, clothesId);
            ps.setString(3, size);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rowToCart(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean add(Cart cart) {
        Cart existing = findByUserAndClothes(cart.getUserId(), cart.getClothesId(), cart.getSize());
        if (existing != null) {
            return updateQuantity(existing.getId(), existing.getQuantity() + cart.getQuantity());
        }
        String sql = "INSERT INTO cart (user_id, clothes_id, quantity, size) VALUES (?,?,?,?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cart.getUserId());
            ps.setInt(2, cart.getClothesId());
            ps.setInt(3, cart.getQuantity());
            ps.setString(4, cart.getSize());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateQuantity(int id, int quantity) {
        String sql = "UPDATE cart SET quantity = ? WHERE id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM cart WHERE id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteByUserId(int userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private Cart rowToCart(ResultSet rs) throws SQLException {
        Cart c = new Cart();
        c.setId(rs.getInt("id"));
        c.setUserId(rs.getInt("user_id"));
        c.setClothesId(rs.getInt("clothes_id"));
        c.setQuantity(rs.getInt("quantity"));
        c.setSize(rs.getString("size"));
        c.setClothesName(rs.getString("clothesName"));
        c.setClothesPrice(rs.getDouble("clothesPrice"));
        c.setClothesImage(rs.getString("clothesImage"));
        return c;
    }
}
