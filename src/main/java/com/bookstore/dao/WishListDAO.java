package com.bookstore.dao;

import com.bookstore.model.WishListItem;

import java.sql.*;
import java.util.*;

public class WishListDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/onlinebookstore";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "sumanth$3031";

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public boolean addToWishlist(int userId, int bookId) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO wishlist (user_id, book_id) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<WishListItem> getWishlistByUser(int userId) {
        List<WishListItem> list = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT b.* FROM books b JOIN wishlist w ON b.id = w.book_id WHERE w.user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                WishListItem item = new WishListItem();
                item.setBookId(rs.getInt("id"));
                item.setTitle(rs.getString("title"));
                item.setAuthor(rs.getString("author"));
                item.setPrice(rs.getDouble("price"));
                item.setImage(rs.getString("image_url"));
                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean removeFromWishlist(int userId, int bookId) {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM wishlist WHERE user_id = ? AND book_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
