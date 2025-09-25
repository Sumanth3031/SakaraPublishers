package com.bookstore.servlet;

import com.bookstore.model.Address;
import com.bookstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        Address address = null;

        if (user != null) {
            String userEmail = user.getEmail();
            try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");
                PreparedStatement pst = conn.prepareStatement("SELECT * FROM address WHERE user_email = ?");
            ) {
                Class.forName("com.mysql.cj.jdbc.Driver");

                pst.setString(1, userEmail);
                ResultSet rs = pst.executeQuery();

                if (rs.next() && rs.getString("name") != null && !rs.getString("name").isEmpty()) {
                    address = new Address();
                    address.setName(rs.getString("name"));
                    address.setUserEmail(rs.getString("user_email"));
                    address.setDoorNo(rs.getString("door_number"));
                    address.setAddressLine1(rs.getString("address_line1"));
                    address.setAddressLine2(rs.getString("address_line2"));
                    address.setCity(rs.getString("city"));
                    address.setState(rs.getString("state"));
                    address.setPincode(rs.getString("pincode"));
                    address.setCountry(rs.getString("country"));
                    address.setPhoneNumber(rs.getString("phone_number"));
                    address.setNearbyLocation(rs.getString("nearby_location"));
                    address.setPlaceType(rs.getString("place_type"));
                }

                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error fetching address: " + e.getMessage());
            }
        }

        // 💖 This is what matters for our JSTL-free Checkout.jsp
        request.setAttribute("address", address);

        // Forward to JSP
        request.getRequestDispatcher("Checkout.jsp").forward(request, response);
    }
}
