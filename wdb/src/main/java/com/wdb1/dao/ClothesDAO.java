package com.wdb1.dao;

import com.wdb1.entity.Clothes;
import com.wdb1.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClothesDAO {

    public List<Clothes> findAll() {
        List<Clothes> list = new ArrayList<>();
        String sql = "SELECT * FROM clothes ORDER BY id DESC";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(rowToClothes(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Clothes> search(String keyword, String category, String style) {
        StringBuilder sql = new StringBuilder("SELECT * FROM clothes WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND name LIKE ?");
            params.add("%" + keyword.trim() + "%");
        }
        if (category != null && !category.trim().isEmpty()) {
            sql.append(" AND category = ?");
            params.add(category.trim());
        }
        if (style != null && !style.trim().isEmpty()) {
            sql.append(" AND style = ?");
            params.add(style.trim());
        }
        sql.append(" ORDER BY id DESC");
        List<Clothes> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++)
                ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(rowToClothes(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Clothes getById(int id) {
        String sql = "SELECT * FROM clothes WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rowToClothes(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean add(Clothes c) {
        String sql = "INSERT INTO clothes (name, style, category, size, price, stock, image_url, description) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getStyle());
            ps.setString(3, c.getCategory());
            ps.setString(4, c.getSize());
            ps.setDouble(5, c.getPrice());
            ps.setInt(6, c.getStock());
            ps.setString(7, c.getImageUrl());
            ps.setString(8, c.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean update(Clothes c) {
        String sql = "UPDATE clothes SET name=?, style=?, category=?, size=?, price=?, stock=?, image_url=?, description=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getStyle());
            ps.setString(3, c.getCategory());
            ps.setString(4, c.getSize());
            ps.setDouble(5, c.getPrice());
            ps.setInt(6, c.getStock());
            ps.setString(7, c.getImageUrl());
            ps.setString(8, c.getDescription());
            ps.setInt(9, c.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM clothes WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<String> getCategories() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM clothes";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(rs.getString("category"));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<String> getStyles() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT style FROM clothes";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(rs.getString("style"));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    private Clothes rowToClothes(ResultSet rs) throws SQLException {
        Clothes c = new Clothes();
        c.setId(rs.getInt("id"));
        c.setName(rs.getString("name"));
        c.setStyle(rs.getString("style"));
        c.setCategory(rs.getString("category"));
        c.setSize(rs.getString("size"));
        c.setPrice(rs.getDouble("price"));
        c.setStock(rs.getInt("stock"));
        c.setImageUrl(rs.getString("image_url"));
        c.setDescription(rs.getString("description"));
        return c;
    }
}
