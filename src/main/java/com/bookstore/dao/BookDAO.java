package com.bookstore.dao;

import com.bookstore.model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/onlinebookstore";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "sumanth$3031";

    private static final String SELECT_BOOK_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SELECT_ALL_BOOKS = "SELECT * FROM books";

    public Book getBookById(int id) {
        Book book = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getDouble("price"),
                    rs.getString("image_url")
                );
                // ✅ Set description and authorInfo
                book.setDescription(rs.getString("description"));
                book.setAuthorInfo(rs.getString("author_info"));
            }

            rs.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }

    // ✅ Updated to include description and author_info
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_BOOKS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getDouble("price"),
                    rs.getString("image_url")
                );
                // ✅ Set description and authorInfo
                book.setDescription(rs.getString("description"));
                book.setAuthorInfo(rs.getString("author_info"));
                books.add(book);
            }

            rs.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
