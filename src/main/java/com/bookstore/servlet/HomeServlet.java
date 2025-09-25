//package com.bookstore.servlet;
//
//import com.bookstore.model.Book;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@WebServlet("/HomeServlet")
//
//
//public class HomeServlet extends HttpServlet {
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        List<Book> bookList = new ArrayList<>();
//
//        String dbURL = "jdbc:mysql://localhost:3306/onlinebookstore";
//        String dbUser = "root";
//        String dbPass = "sumanth$3031";
//
//        String sql = "SELECT id, title, author, price, image_url FROM books";
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                Book book = new Book(
//                    rs.getInt("id"),
//                    rs.getString("title"),
//                    rs.getString("author"),
//                    rs.getDouble("price"),
//                    rs.getString("image_url")  // fetch from your DB column named image_url
//                );
//                bookList.add(book);
//            }
//
//            rs.close();
//            ps.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // ✅ Send bookList to Home.jsp
//        request.setAttribute("bookList", bookList);
//        RequestDispatcher rd = request.getRequestDispatcher("Home.jsp");
//        rd.forward(request, response);
//    }
//}



//
//package com.bookstore.servlet;
//
//import com.bookstore.model.Book;
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession; // ✅ Import this for session
//
//import java.io.IOException;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@WebServlet("/HomeServlet")
//public class HomeServlet extends HttpServlet {
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        // ✅ Step 1: Check if user is logged in
//        HttpSession session = request.getSession(false); // don't create new session
//        if (session == null || session.getAttribute("userName") == null) {
//            response.sendRedirect("login.jsp");
//            return;
//        }
//
//        // ✅ Step 2: Continue loading books
//        List<Book> bookList = new ArrayList<>();
//
//        String dbURL = "jdbc:mysql://localhost:3306/onlinebookstore";
//        String dbUser = "root";
//        String dbPass = "sumanth$3031";
//
//        String sql = "SELECT id, title, author, price, image_url FROM books";
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                Book book = new Book(
//                    rs.getInt("id"),
//                    rs.getString("title"),
//                    rs.getString("author"),
//                    rs.getDouble("price"),
//                    rs.getString("image_url")
//                );
//                bookList.add(book);
//            }
//
//            rs.close();
//            ps.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // ✅ Send bookList to Home.jsp
//        request.setAttribute("bookList", bookList);
//        RequestDispatcher rd = request.getRequestDispatcher("Home.jsp");
//        rd.forward(request, response);
//    }
//}


package com.bookstore.servlet;

import com.bookstore.model.Book;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/HomeServlet")

public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Book> bookList = new ArrayList<>();

        String dbURL = "jdbc:mysql://localhost:3306/onlinebookstore";
        String dbUser = "root";
        String dbPass = "sumanth$3031";

        String sql = "SELECT id, title, author, price, image_url FROM books";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getString("image_url")
                );
                bookList.add(book);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("bookList", bookList);
        RequestDispatcher rd = request.getRequestDispatcher("Home.jsp");
        rd.forward(request, response);
    }
}


