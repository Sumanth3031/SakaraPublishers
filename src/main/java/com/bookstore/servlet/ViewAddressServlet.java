package com.bookstore.servlet;

import com.bookstore.model.Address;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/ViewAddressServlet")
public class ViewAddressServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        String email = (String) session.getAttribute("userEmail");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");

            String sql = "SELECT * FROM address WHERE user_email = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                // ✅ Create Address object from result
                Address address = new Address();
                address.setName(rs.getString("name"));
                address.setDoorNo(rs.getString("door_number"));
                address.setAddressLine1(rs.getString("address_line1"));
                address.setAddressLine2(rs.getString("address_line2"));
                address.setCity(rs.getString("city"));
                address.setState(rs.getString("state"));
                address.setPincode(rs.getString("pincode"));
                address.setCountry(rs.getString("country"));
                address.setPhoneNumber(rs.getString("phone_number"));

                // ✅ Save in session
                session.setAttribute("address", address);

                // ✅ Check if from checkout
                String from = request.getParameter("from");
                if ("checkout".equals(from)) {
                    request.setAttribute("address", address); // needed for request scope
                    request.getRequestDispatcher("Checkout.jsp").forward(request, response);
                // return;
                    
                } else {
                    response.sendRedirect("Profile.jsp"); // or wherever you want normally
                }

            } else {
                // No address found
                response.sendRedirect("Address.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
