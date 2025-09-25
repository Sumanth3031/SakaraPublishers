//package com.bookstore.servlet;
//
//import java.io.IOException;
//
//import java.sql.Connection;
//
//import java.sql.DriverManager;
//
//import java.sql.PreparedStatement;
//
//import java.sql.ResultSet;
//
//import java.sql.SQLException;
//
////import java.io.IOException;
//
////import java.sql.*;
//
////import jakarta.servlet.ServletException;
//
////import jakarta.servlet.annotation.WebServlet;
//
////import jakarta.servlet.http.HttpServlet;
//
////import jakarta.servlet.http.HttpServletRequest;
//
////import jakarta.servlet.http.HttpServletResponse;
//
////import jakarta.servlet.http.HttpSession;
//
//import jakarta.servlet.ServletException;
//
//import jakarta.servlet.annotation.WebServlet;
//
//import jakarta.servlet.http.HttpServlet;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//import jakarta.servlet.http.HttpServletResponse;
//
//import jakarta.servlet.http.HttpSession;
//
//@WebServlet("/LoginServlet")
//
//public class LoginServlet extends HttpServlet {
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//
//			throws ServletException, IOException {
//
//		String email = request.getParameter("emailOrMobile");
//
//		String password = request.getParameter("password");
//
//		Connection conn = null;
//
//		PreparedStatement pst = null;
//
//		ResultSet rs = null;
//
//		try {
//
//			Class.forName("com.mysql.cj.jdbc.Driver");
//
//			conn = DriverManager.getConnection(
//
//					"jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");
//
//			String sql = "SELECT * FROM users WHERE email=? AND password=?";
//
//			pst = conn.prepareStatement(sql);
//
//			pst.setString(1, email);
//
//			pst.setString(2, password);
//
//			rs = pst.executeQuery();
//
//			if (rs.next()) {
//
//				int userId = rs.getInt("id"); // ✅ this is needed!
//
//				String name = rs.getString("name");
//
//				String dbEmail = rs.getString("email");
//
//				HttpSession session = request.getSession();
//
//				session.setAttribute("userName", name);
//
//				session.setAttribute("userEmail", dbEmail);
//
//				session.setAttribute("userId", userId); // ✅ this is required!
//
//				response.sendRedirect("HomeServlet");
//
//			} else {
//
//				response.sendRedirect("Login.jsp?error=1");
//
//			}
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//
//			response.setContentType("text/html");
//
//			response.getWriter().println("<h2 style='color:red;'>Something went wrong: " + e.getMessage() + "</h2>");
//
//		} finally {
//
//			try {
//
//				if (rs != null)
//					rs.close();
//
//				if (pst != null)
//					pst.close();
//
//				if (conn != null)
//					conn.close();
//
//			} catch (SQLException ex) {
//
//				ex.printStackTrace();
//
//			}
//
//		}
//
//	}
//
//}

//
//
//package com.bookstore.servlet;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//// No need to import User model if you're not putting a User object in session,
//// but keeping it imported won't hurt.
//import com.bookstore.model.User; 
//
//
//@WebServlet("/LoginServlet")
//public class LoginServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String inputIdentifier = request.getParameter("emailOrMobile");
//        String password = request.getParameter("password");
//
//        Connection conn = null;
//        PreparedStatement pst = null;
//        ResultSet rs = null;
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");
//
//            String sql = "SELECT id, name, email FROM users WHERE (email=? OR mobile=?) AND password=?";
//            pst = conn.prepareStatement(sql);
//            pst.setString(1, inputIdentifier);
//            pst.setString(2, inputIdentifier);
//            pst.setString(3, password);
//
//            rs = pst.executeQuery();
//
//            if (rs.next()) {
//                int userId = rs.getInt("id");
//                String name = rs.getString("name");
//                String dbEmail = rs.getString("email"); 
//
//                HttpSession session = request.getSession();
//                session.setAttribute("userName", name);
//                session.setAttribute("userEmail", dbEmail); 
//                session.setAttribute("userId", userId);
//
//                response.sendRedirect("HomeServlet"); 
//            } else {
//                // Login failed - Redirect to Login.jsp (uppercase L)
//                response.sendRedirect("Login.jsp?error=1"); // <<<--- CONFIRMED: Uppercase 'L'
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setContentType("text/html");
//            response.getWriter().println("<h2 style='color:red;'>Something went wrong: " + e.getMessage() + "</h2>");
//        } finally {
//            try {
//                if (rs != null) rs.close();
//                if (pst != null) pst.close();
//                if (conn != null) conn.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//}



//package com.bookstore.servlet;
//
//import java.io.IOException;
//import java.sql.*;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//import com.bookstore.model.User;
//
//@WebServlet("/LoginServlet")
//public class LoginServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String inputIdentifier = request.getParameter("emailOrMobile");
//        String password = request.getParameter("password");
//
//        Connection conn = null;
//        PreparedStatement pst = null;
//        ResultSet rs = null;
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");
//
//            String sql = "SELECT id, name, email FROM users WHERE (email=? OR mobile=?) AND password=?";
//            pst = conn.prepareStatement(sql);
//            pst.setString(1, inputIdentifier);
//            pst.setString(2, inputIdentifier);
//            pst.setString(3, password);
//
//            rs = pst.executeQuery();
//
//            if (rs.next()) {
//                int userId = rs.getInt("id");
//                String name = rs.getString("name");
//                String dbEmail = rs.getString("email");
//
//                // 💖 Create and populate User object
//                User user = new User();
//                user.setId(userId);
//                user.setName(name);
//                user.setEmail(dbEmail);
//
//                // 💖 Store User object in session
//                HttpSession session = request.getSession();
//                session.setAttribute("user", user); // ✅ This is what CheckoutServlet expects
//
//                response.sendRedirect("HomeServlet");
//            } else {
//                response.sendRedirect("Login.jsp?error=1");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setContentType("text/html");
//            response.getWriter().println("<h2 style='color:red;'>Something went wrong: " + e.getMessage() + "</h2>");
//        } finally {
//            try {
//                if (rs != null) rs.close();
//                if (pst != null) pst.close();
//                if (conn != null) conn.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//}



package com.bookstore.servlet;

import java.io.IOException;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.bookstore.model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String inputIdentifier = request.getParameter("emailOrMobile");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");

            String sql = "SELECT id, name, email FROM users WHERE (email=? OR mobile=?) AND password=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, inputIdentifier);
            pst.setString(2, inputIdentifier);
            pst.setString(3, password);

            rs = pst.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String name = rs.getString("name");
                String dbEmail = rs.getString("email");

                // ✅ Create User object and store it in session
                User user = new User();
                user.setId(userId);
                user.setName(name);
                user.setEmail(dbEmail);

                HttpSession session = request.getSession();
                session.setAttribute("user", user); // 🔥 This line fixes the issue

                // Optional: store individual values too
                session.setAttribute("userId", userId);
                session.setAttribute("userName", name);
                session.setAttribute("userEmail", dbEmail);

                System.out.println("[LoginServlet] Logged in user saved in session: " + user.getEmail());

                response.sendRedirect("HomeServlet");

            } else {
                response.sendRedirect("Login.jsp?error=1");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().println("<h2 style='color:red;'>Something went wrong: " + e.getMessage() + "</h2>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

