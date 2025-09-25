package com.bookstore.servlet;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	String name = request.getParameter("fullName");
    	String email = request.getParameter("emailOrMobile");
    	String password = request.getParameter("password");
    	String mobile = request.getParameter("mobileNumber");


        Connection con = null;
        PreparedStatement pst = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");

            String sql = "INSERT INTO users(name, email, password, mobile) VALUES (?, ?, ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, password);
            pst.setString(4, mobile);


            int rows = pst.executeUpdate();
            if (rows > 0) {
                response.sendRedirect("Login.jsp?register=success");
            } else {
                response.sendRedirect("Login.jsp?register=failure");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Login.jsp?register=error");
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
