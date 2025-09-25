package com.bookstore.dao;

import com.bookstore.model.Order;
import com.bookstore.model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private Connection conn;

    public OrderDAO(Connection conn) {
        this.conn = conn;
    }

    // ✅ Save Order and return generated ID
    public int saveOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders (user_id, total_amount, address) VALUES (?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setInt(1, order.getUserId());
        pst.setDouble(2, order.getTotalAmount());
        pst.setString(3, order.getAddress());

        int affectedRows = pst.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating order failed, no rows affected.");
        }

        ResultSet generatedKeys = pst.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1); // order ID
        } else {
            throw new SQLException("Creating order failed, no ID obtained.");
        }
    }

    // ✅ Save items for a given order
    public void saveOrderItems(int orderId, List<OrderItem> items) throws SQLException {
        String sql = "INSERT INTO order_items (order_id, book_id, quantity, price) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);

        for (OrderItem item : items) {
            pst.setInt(1, orderId);
            pst.setInt(2, item.getBookId());
            pst.setInt(3, item.getQuantity());
            pst.setDouble(4, item.getPrice());
            pst.addBatch();
        }
        pst.executeBatch();
    }

    // ✅ Get all orders for a user
//    public List<Order> getOrdersByUserId(int userId) {
//        List<Order> orders = new ArrayList<>();
//        try {
//            String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, userId);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                Order order = new Order();
//                order.setId(rs.getInt("id"));
//                order.setUserId(userId);
//                order.setTotalAmount(rs.getDouble("total_amount"));
//                order.setAddress(rs.getString("address"));
//                order.setOrderDate(rs.getTimestamp("order_date")); // assuming you have order_date column
//                orders.add(order);
//            }
//
//            rs.close();
//            ps.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return orders;
//    }

    // ✅ Get all items for a specific order
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        try {
            String sql = "SELECT oi.*, b.title FROM order_items oi JOIN books b ON oi.book_id = b.id WHERE oi.order_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setBookId(rs.getInt("book_id"));
                item.setOrderId(orderId);
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                item.setBookTitle(rs.getString("title")); // set book title
                items.add(item);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
    public List<Order> getOrdersByUserId(int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY id DESC";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setUserId(rs.getInt("user_id"));
            order.setTotalAmount(rs.getDouble("total_amount"));
            order.setAddress(rs.getString("address"));
            order.setOrderDate(rs.getTimestamp("order_date")); // only if this column exists in your DB
            orders.add(order);
        }

        return orders;
    }

}
