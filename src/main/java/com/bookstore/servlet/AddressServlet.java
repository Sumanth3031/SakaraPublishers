//package com.bookstore.servlet;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//import java.io.IOException;
//import java.sql.*;
//import com.bookstore.model.Address; // Make sure you have this model
//
//@WebServlet("/AddressServlet")
//public class AddressServlet extends HttpServlet {
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        request.setCharacterEncoding("UTF-8");
//
//        String name = request.getParameter("name");
//        String email = request.getParameter("user_email"); // Make sure this is passed from your forms
//        String doorNumber = request.getParameter("door_number");
//        String addressLine1 = request.getParameter("address_line1");
//        String addressLine2 = request.getParameter("address_line2");
//        String city = request.getParameter("city");
//        String state = request.getParameter("state");
//        String pincode = request.getParameter("pincode");
//        String country = request.getParameter("country");
//        String phoneNumber = request.getParameter("phone_number");
//        String nearbyLocation = request.getParameter("nearby_location");
//        String placeType = request.getParameter("place_type");
//
//        Connection conn = null;
//        PreparedStatement ps = null;
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");
//            
//            // Determine if it's an edit or new insert
//            boolean isEdit = checkIfAddressExists(email, conn); // New helper method
//            
//            String sql;
//            if (isEdit) {
//                // Assuming user_email is the primary key or unique identifier for the address
//                sql = "UPDATE address SET name=?, door_number=?, address_line1=?, address_line2=?, city=?, state=?, pincode=?, country=?, phone_number=?, nearby_location=?, place_type=? WHERE user_email=?";
//            } else {
//                sql = "INSERT INTO address (name, user_email, door_number, address_line1, address_line2, city, state, pincode, country, phone_number, nearby_location, place_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//            }
//
//            ps = conn.prepareStatement(sql);
//            if (isEdit) {
//                ps.setString(1, name); // Added name for update
//                ps.setString(2, doorNumber);
//                ps.setString(3, addressLine1);
//                ps.setString(4, addressLine2);
//                ps.setString(5, city);
//                ps.setString(6, state);
//                ps.setString(7, pincode);
//                ps.setString(8, country);
//                ps.setString(9, phoneNumber);
//                ps.setString(10, nearbyLocation);
//                ps.setString(11, placeType);
//                ps.setString(12, email); // WHERE clause
//            } else {
//                ps.setString(1, name);
//                ps.setString(2, email);
//                ps.setString(3, doorNumber);
//                ps.setString(4, addressLine1);
//                ps.setString(5, addressLine2);
//                ps.setString(6, city);
//                ps.setString(7, state);
//                ps.setString(8, pincode);
//                ps.setString(9, country);
//                ps.setString(10, phoneNumber);
//                ps.setString(11, nearbyLocation);
//                ps.setString(12, placeType);
//            }
//
//            int rowInserted = ps.executeUpdate();
//
//            if (rowInserted > 0) {
//            	request.setAttribute("successMessage", "Dear " + name + ", your address has been saved successfully 💌");
//            	// If saving from "My Account -> Address", redirect back to My Account page or confirmation
//                // If saving from Checkout, we need to redirect back to CheckoutServlet
//                String redirectUrl = request.getParameter("redirectAfterSave");
//                if ("checkout".equals(redirectUrl)) {
//                    response.sendRedirect("CheckoutServlet"); // Redirect back to checkout to display updated address
//                } else {
//                    RequestDispatcher dispatcher = request.getRequestDispatcher("Address.jsp"); // Or a success view for My Account
//                    dispatcher.forward(request, response);
//                }
//            } else {
//                response.getWriter().println("<h3 style='color:red;'>Failed to save address.</h3>");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
//        } finally {
//            try {
//                if (ps != null) ps.close();
//                if (conn != null) conn.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    // New doGet method to retrieve address by user_email (useful for Address.jsp)
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        
//        String userEmail = request.getParameter("user_email"); // Expecting user_email as a parameter
//
//        Address address = null;
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");
//
//            String sql = "SELECT * FROM address WHERE user_email = ?";
//            ps = conn.prepareStatement(sql);
//            ps.setString(1, userEmail);
//            rs = ps.executeQuery();
//
//            if (rs.next()) {
//                address = new Address();
//                address.setName(rs.getString("name"));
//                address.setUserEmail(rs.getString("user_email"));
//                address.setDoorNo(rs.getString("door_number"));
//                address.setAddressLine1(rs.getString("address_line1"));
//                address.setAddressLine2(rs.getString("address_line2"));
//                address.setCity(rs.getString("city"));
//                address.setState(rs.getString("state"));
//                address.setPincode(rs.getString("pincode"));
//                address.setCountry(rs.getString("country"));
//                address.setPhoneNumber(rs.getString("phone_number"));
//                address.setNearbyLocation(rs.getString("nearby_location"));
//                address.setPlaceType(rs.getString("place_type"));
//            }
//            
//            request.setAttribute("address", address); // Set the address in request scope
//            RequestDispatcher dispatcher = request.getRequestDispatcher("Address.jsp"); // Forward to Address.jsp to display
//            dispatcher.forward(request, response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().println("<h3 style='color:red;'>Error retrieving address: " + e.getMessage() + "</h3>");
//        } finally {
//            try {
//                if (rs != null) rs.close();
//                if (ps != null) ps.close();
//                if (conn != null) conn.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    // Helper method to check if an address exists for a given email
//    private boolean checkIfAddressExists(String userEmail, Connection conn) throws SQLException {
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            String sql = "SELECT COUNT(*) FROM address WHERE user_email = ?";
//            ps = conn.prepareStatement(sql);
//            ps.setString(1, userEmail);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1) > 0;
//            }
//            return false;
//        } finally {
//            if (rs != null) rs.close();
//            if (ps != null) ps.close();
//        }
//    }
//}














//
//package com.bookstore.servlet;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//import java.io.IOException;
//import java.sql.*;
//import com.bookstore.model.Address;
//
//@WebServlet("/AddressServlet")
//public class AddressServlet extends HttpServlet {
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        request.setCharacterEncoding("UTF-8");
//
//        String name = request.getParameter("name");
//        String email = request.getParameter("user_email");
//        String doorNumber = request.getParameter("door_number");
//        String addressLine1 = request.getParameter("address_line1");
//        String addressLine2 = request.getParameter("address_line2");
//        String city = request.getParameter("city");
//        String state = request.getParameter("state");
//        String pincode = request.getParameter("pincode");
//        String country = request.getParameter("country");
//        String phoneNumber = request.getParameter("phone_number");
//        String nearbyLocation = request.getParameter("nearby_location");
//        String placeType = request.getParameter("place_type");
//
//        Connection conn = null;
//        PreparedStatement ps = null;
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");
//
//            boolean isEdit = checkIfAddressExists(email, conn);
//
//            String sql;
//            if (isEdit) {
//                sql = "UPDATE address SET name=?, door_number=?, address_line1=?, address_line2=?, city=?, state=?, pincode=?, country=?, phone_number=?, nearby_location=?, place_type=? WHERE user_email=?";
//            } else {
//                sql = "INSERT INTO address (name, user_email, door_number, address_line1, address_line2, city, state, pincode, country, phone_number, nearby_location, place_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//            }
//
//            ps = conn.prepareStatement(sql);
//            if (isEdit) {
//                ps.setString(1, name);
//                ps.setString(2, doorNumber);
//                ps.setString(3, addressLine1);
//                ps.setString(4, addressLine2);
//                ps.setString(5, city);
//                ps.setString(6, state);
//                ps.setString(7, pincode);
//                ps.setString(8, country);
//                ps.setString(9, phoneNumber);
//                ps.setString(10, nearbyLocation);
//                ps.setString(11, placeType);
//                ps.setString(12, email);
//            } else {
//                ps.setString(1, name);
//                ps.setString(2, email);
//                ps.setString(3, doorNumber);
//                ps.setString(4, addressLine1);
//                ps.setString(5, addressLine2);
//                ps.setString(6, city);
//                ps.setString(7, state);
//                ps.setString(8, pincode);
//                ps.setString(9, country);
//                ps.setString(10, phoneNumber);
//                ps.setString(11, nearbyLocation);
//                ps.setString(12, placeType);
//            }
//
//            int rowInserted = ps.executeUpdate();
//
//            if (rowInserted > 0) {
//                request.setAttribute("successMessage", "Dear " + name + ", your address has been saved successfully 💌");
//                String redirectUrl = request.getParameter("redirectAfterSave");
//
//                if ("checkout".equals(redirectUrl)) {
//                    response.sendRedirect("CheckoutServlet");
//                } else {
//                    request.setAttribute("edit", "true"); // show form as filled
//                    RequestDispatcher dispatcher = request.getRequestDispatcher("Address.jsp");
//                    dispatcher.forward(request, response);
//                }
//            } else {
//                response.getWriter().println("<h3 style='color:red;'>Failed to save address.</h3>");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
//        } finally {
//            try {
//                if (ps != null) ps.close();
//                if (conn != null) conn.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String userEmail = request.getParameter("user_email");
//        Address address = null;
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");
//
//            String sql = "SELECT * FROM address WHERE user_email = ?";
//            ps = conn.prepareStatement(sql);
//            ps.setString(1, userEmail);
//            rs = ps.executeQuery();
//
//            if (rs.next()) {
//                address = new Address();
//                address.setName(rs.getString("name"));
//                address.setUserEmail(rs.getString("user_email"));
//                address.setDoorNo(rs.getString("door_number"));
//                address.setAddressLine1(rs.getString("address_line1"));
//                address.setAddressLine2(rs.getString("address_line2"));
//                address.setCity(rs.getString("city"));
//                address.setState(rs.getString("state"));
//                address.setPincode(rs.getString("pincode"));
//                address.setCountry(rs.getString("country"));
//                address.setPhoneNumber(rs.getString("phone_number"));
//                address.setNearbyLocation(rs.getString("nearby_location"));
//                address.setPlaceType(rs.getString("place_type"));
//            }
//
//            request.setAttribute("address", address);
//            request.setAttribute("edit", "true"); // Enable edit mode
//            RequestDispatcher dispatcher = request.getRequestDispatcher("Address.jsp");
//            dispatcher.forward(request, response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().println("<h3 style='color:red;'>Error retrieving address: " + e.getMessage() + "</h3>");
//        } finally {
//            try {
//                if (rs != null) rs.close();
//                if (ps != null) ps.close();
//                if (conn != null) conn.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    private boolean checkIfAddressExists(String userEmail, Connection conn) throws SQLException {
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            String sql = "SELECT COUNT(*) FROM address WHERE user_email = ?";
//            ps = conn.prepareStatement(sql);
//            ps.setString(1, userEmail);
//            rs = ps.executeQuery();
//            return rs.next() && rs.getInt(1) > 0;
//        } finally {
//            if (rs != null) rs.close();
//            if (ps != null) ps.close();
//        }
//    }
//}




//
//package com.bookstore.servlet;
//
//import com.bookstore.model.Address;
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//import java.io.IOException;
//import java.sql.*;
//
//@WebServlet("/AddressServlet")
//public class AddressServlet extends HttpServlet {
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        request.setCharacterEncoding("UTF-8");
//
//        String name = request.getParameter("name");
//        String email = request.getParameter("user_email");
//        String doorNumber = request.getParameter("door_number");
//        String addressLine1 = request.getParameter("address_line1");
//        String addressLine2 = request.getParameter("address_line2");
//        String city = request.getParameter("city");
//        String state = request.getParameter("state");
//        String pincode = request.getParameter("pincode");
//        String country = request.getParameter("country");
//        String phoneNumber = request.getParameter("phone_number");
//        String nearbyLocation = request.getParameter("nearby_location");
//        String placeType = request.getParameter("place_type");
//
//        Connection conn = null;
//        PreparedStatement ps = null;
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");
//
//            boolean isEdit = checkIfAddressExists(email, conn);
//
//            String sql;
//            if (isEdit) {
//                sql = "UPDATE address SET name=?, door_number=?, address_line1=?, address_line2=?, city=?, state=?, pincode=?, country=?, phone_number=?, nearby_location=?, place_type=? WHERE user_email=?";
//            } else {
//                sql = "INSERT INTO address (name, user_email, door_number, address_line1, address_line2, city, state, pincode, country, phone_number, nearby_location, place_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//            }
//
//            ps = conn.prepareStatement(sql);
//            if (isEdit) {
//                ps.setString(1, name);
//                ps.setString(2, doorNumber);
//                ps.setString(3, addressLine1);
//                ps.setString(4, addressLine2);
//                ps.setString(5, city);
//                ps.setString(6, state);
//                ps.setString(7, pincode);
//                ps.setString(8, country);
//                ps.setString(9, phoneNumber);
//                ps.setString(10, nearbyLocation);
//                ps.setString(11, placeType);
//                ps.setString(12, email);
//            } else {
//                ps.setString(1, name);
//                ps.setString(2, email);
//                ps.setString(3, doorNumber);
//                ps.setString(4, addressLine1);
//                ps.setString(5, addressLine2);
//                ps.setString(6, city);
//                ps.setString(7, state);
//                ps.setString(8, pincode);
//                ps.setString(9, country);
//                ps.setString(10, phoneNumber);
//                ps.setString(11, nearbyLocation);
//                ps.setString(12, placeType);
//            }
//
//            int result = ps.executeUpdate();
//
//            if (result > 0) {
//                request.setAttribute("successMessage", "Dear " + name + ", your address has been saved successfully 💌");
//            }
//
//            // Now forward to GET logic to re-display the saved address
//            response.sendRedirect("AddressServlet?user_email=" + email);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
//        } finally {
//            try {
//                if (ps != null) ps.close();
//                if (conn != null) conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String email = request.getParameter("user_email");
//        HttpSession session = request.getSession();
//        session.setAttribute("userEmail", email); // To ensure Address.jsp gets session email
//
//        Address address = null;
//        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");
//             PreparedStatement ps = conn.prepareStatement("SELECT * FROM address WHERE user_email = ?")) {
//
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            ps.setString(1, email);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                address = new Address();
//                address.setName(rs.getString("name"));
//                address.setUserEmail(rs.getString("user_email"));
//                address.setDoorNo(rs.getString("door_number"));
//                address.setAddressLine1(rs.getString("address_line1"));
//                address.setAddressLine2(rs.getString("address_line2"));
//                address.setCity(rs.getString("city"));
//                address.setState(rs.getString("state"));
//                address.setPincode(rs.getString("pincode"));
//                address.setCountry(rs.getString("country"));
//                address.setPhoneNumber(rs.getString("phone_number"));
//                address.setNearbyLocation(rs.getString("nearby_location"));
//                address.setPlaceType(rs.getString("place_type"));
//
//                request.setAttribute("savedAddress", address);
//            }
//
//            RequestDispatcher dispatcher;
//            if (request.getParameter("edit") != null) {
//                request.setAttribute("editMode", true);
//            }
//            dispatcher = request.getRequestDispatcher("Address.jsp");
//            dispatcher.forward(request, response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().println("<h3 style='color:red;'>Failed to load address: " + e.getMessage() + "</h3>");
//        }
//    }
//
//    private boolean checkIfAddressExists(String email, Connection conn) throws SQLException {
//        String sql = "SELECT COUNT(*) FROM address WHERE user_email = ?";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, email);
//            ResultSet rs = ps.executeQuery();
//            return rs.next() && rs.getInt(1) > 0;
//        }
//    }
//}
//
//package com.bookstore.servlet;
//
//import com.bookstore.model.Address;
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//import java.io.IOException;
//import java.sql.*;
//
//@WebServlet("/AddressServlet")
//public class AddressServlet extends HttpServlet {
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        request.setCharacterEncoding("UTF-8");
//        HttpSession session = request.getSession();
//
//        String name = request.getParameter("name");
//        String email = request.getParameter("user_email");
//        String doorNumber = request.getParameter("door_number");
//        String addressLine1 = request.getParameter("address_line1");
//        String addressLine2 = request.getParameter("address_line2");
//        String city = request.getParameter("city");
//        String state = request.getParameter("state");
//        String pincode = request.getParameter("pincode");
//        String country = request.getParameter("country");
//        String phoneNumber = request.getParameter("phone_number");
//        String nearbyLocation = request.getParameter("nearby_location");
//        String placeType = request.getParameter("place_type");
//
//        Connection conn = null;
//        PreparedStatement ps = null;
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");
//
//            boolean isEdit = checkIfAddressExists(email, conn);
//
//            String sql;
//            if (isEdit) {
//                sql = "UPDATE address SET name=?, door_number=?, address_line1=?, address_line2=?, city=?, state=?, pincode=?, country=?, phone_number=?, nearby_location=?, place_type=? WHERE user_email=?";
//            } else {
//                sql = "INSERT INTO address (name, user_email, door_number, address_line1, address_line2, city, state, pincode, country, phone_number, nearby_location, place_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//            }
//
//            ps = conn.prepareStatement(sql);
//            if (isEdit) {
//                ps.setString(1, name);
//                ps.setString(2, doorNumber);
//                ps.setString(3, addressLine1);
//                ps.setString(4, addressLine2);
//                ps.setString(5, city);
//                ps.setString(6, state);
//                ps.setString(7, pincode);
//                ps.setString(8, country);
//                ps.setString(9, phoneNumber);
//                ps.setString(10, nearbyLocation);
//                ps.setString(11, placeType);
//                ps.setString(12, email);
//            } else {
//                ps.setString(1, name);
//                ps.setString(2, email);
//                ps.setString(3, doorNumber);
//                ps.setString(4, addressLine1);
//                ps.setString(5, addressLine2);
//                ps.setString(6, city);
//                ps.setString(7, state);
//                ps.setString(8, pincode);
//                ps.setString(9, country);
//                ps.setString(10, phoneNumber);
//                ps.setString(11, nearbyLocation);
//                ps.setString(12, placeType);
//            }
//
//            int result = ps.executeUpdate();
//
//            if (result > 0) {
//                request.setAttribute("successMessage", "Dear " + name + ", your address has been saved successfully 💌");
//                session.setAttribute("userName", name);
//                session.setAttribute("userEmail", email);
//            }
//
//            // ✅ After saving, directly forward to doGet (which fetches and shows address)
//            doGet(request, response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
//        } finally {
//            try {
//                if (ps != null) ps.close();
//                if (conn != null) conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        HttpSession session = request.getSession();
//        String email = (String) session.getAttribute("userEmail");
//
//        if (email == null) {
//            response.sendRedirect("Login.jsp");
//            return;
//        }
//
//        Address address = null;
//
//        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");
//             PreparedStatement ps = conn.prepareStatement("SELECT * FROM address WHERE user_email = ?")) {
//
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            ps.setString(1, email);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                address = new Address();
//                address.setName(rs.getString("name"));
//                address.setUserEmail(rs.getString("user_email"));
//                address.setDoorNo(rs.getString("door_number"));
//                address.setAddressLine1(rs.getString("address_line1"));
//                address.setAddressLine2(rs.getString("address_line2"));
//                address.setCity(rs.getString("city"));
//                address.setState(rs.getString("state"));
//                address.setPincode(rs.getString("pincode"));
//                address.setCountry(rs.getString("country"));
//                address.setPhoneNumber(rs.getString("phone_number"));
//                address.setNearbyLocation(rs.getString("nearby_location"));
//                address.setPlaceType(rs.getString("place_type"));
//
//                request.setAttribute("savedAddress", address);
//            }
//
//            if (request.getParameter("edit") != null) {
//                request.setAttribute("editMode", true);
//            }
//
//            RequestDispatcher dispatcher = request.getRequestDispatcher("Address.jsp");
//            dispatcher.forward(request, response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().println("<h3 style='color:red;'>Failed to load address: " + e.getMessage() + "</h3>");
//        }
//    }
//
//    private boolean checkIfAddressExists(String email, Connection conn) throws SQLException {
//        String sql = "SELECT COUNT(*) FROM address WHERE user_email = ?";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, email);
//            ResultSet rs = ps.executeQuery();
//            return rs.next() && rs.getInt(1) > 0;
//        }
//    }
//}


//package com.bookstore.servlet;
//
//import com.bookstore.model.Address;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//import java.io.IOException;
//import java.sql.*;
//
//@WebServlet("/AddressServlet")
//public class AddressServlet extends HttpServlet {
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String email = request.getParameter("user_email");
//
//        Address address = new Address();
//        address.setUserEmail(email);
//        address.setName(request.getParameter("name"));
//        address.setDoorNo(request.getParameter("door_number"));
//        address.setAddressLine1(request.getParameter("address_line1"));
//        address.setAddressLine2(request.getParameter("address_line2"));
//        address.setCity(request.getParameter("city"));
//        address.setState(request.getParameter("state"));
//        address.setPincode(request.getParameter("pincode"));
//        address.setCountry(request.getParameter("country"));
//        address.setPhoneNumber(request.getParameter("phone_number"));
//        address.setNearbyLocation(request.getParameter("nearby_location"));
//        address.setPlaceType(request.getParameter("place_type"));
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");
//
//            // ✅ Check if address already exists
//            String checkSql = "SELECT * FROM address WHERE user_email = ?";
//            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
//            checkStmt.setString(1, email);
//            ResultSet rs = checkStmt.executeQuery();
//
//            if (rs.next()) {
//                // ✅ Update existing
//                String updateSql = "UPDATE address SET name=?, door_number=?, address_line1=?, address_line2=?, city=?, state=?, pincode=?, country=?, phone_number=?, nearby_location=?, place_type=? WHERE user_email=?";
//                PreparedStatement pst = conn.prepareStatement(updateSql);
//                pst.setString(1, address.getName());
//                pst.setString(2, address.getDoorNo());
//                pst.setString(3, address.getAddressLine1());
//                pst.setString(4, address.getAddressLine2());
//                pst.setString(5, address.getCity());
//                pst.setString(6, address.getState());
//                pst.setString(7, address.getPincode());
//                pst.setString(8, address.getCountry());
//                pst.setString(9, address.getPhoneNumber());
//                pst.setString(10, address.getNearbyLocation());
//                pst.setString(11, address.getPlaceType());
//                pst.setString(12, email);
//                pst.executeUpdate();
//            } else {
//                // ✅ Insert new
//                String insertSql = "INSERT INTO address (user_email, name, door_number, address_line1, address_line2, city, state, pincode, country, phone_number, nearby_location, place_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//                PreparedStatement pst = conn.prepareStatement(insertSql);
//                pst.setString(1, email);
//                pst.setString(2, address.getName());
//                pst.setString(3, address.getDoorNo());
//                pst.setString(4, address.getAddressLine1());
//                pst.setString(5, address.getAddressLine2());
//                pst.setString(6, address.getCity());
//                pst.setString(7, address.getState());
//                pst.setString(8, address.getPincode());
//                pst.setString(9, address.getCountry());
//                pst.setString(10, address.getPhoneNumber());
//                pst.setString(11, address.getNearbyLocation());
//                pst.setString(12, address.getPlaceType());
//                pst.executeUpdate();
//            }
//
//            rs.close();
//            checkStmt.close();
//
//            // ✅ After saving, forward to CheckoutServlet (stays in page)
//            request.getRequestDispatcher("CheckoutServlet").forward(request, response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute("errorMessage", "Error saving address: " + e.getMessage());
//            request.getRequestDispatcher("Checkout.jsp").forward(request, response);
//        }
//    }
//}

package com.bookstore.servlet;

import com.bookstore.model.Address;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/AddressServlet")
public class AddressServlet extends HttpServlet {

    // 💌 Handles form submission (Save/Update address)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("user_email");

        Address address = new Address();
        address.setUserEmail(email);
        address.setName(request.getParameter("name"));
        address.setDoorNo(request.getParameter("door_number"));
        address.setAddressLine1(request.getParameter("address_line1"));
        address.setAddressLine2(request.getParameter("address_line2"));
        address.setCity(request.getParameter("city"));
        address.setState(request.getParameter("state"));
        address.setPincode(request.getParameter("pincode"));
        address.setCountry(request.getParameter("country"));
        address.setPhoneNumber(request.getParameter("phone_number"));
        address.setNearbyLocation(request.getParameter("nearby_location"));
        address.setPlaceType(request.getParameter("place_type"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");

            // ✅ Check if address already exists
            String checkSql = "SELECT * FROM address WHERE user_email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // ✅ Update existing
                String updateSql = "UPDATE address SET name=?, door_number=?, address_line1=?, address_line2=?, city=?, state=?, pincode=?, country=?, phone_number=?, nearby_location=?, place_type=? WHERE user_email=?";
                PreparedStatement pst = conn.prepareStatement(updateSql);
                pst.setString(1, address.getName());
                pst.setString(2, address.getDoorNo());
                pst.setString(3, address.getAddressLine1());
                pst.setString(4, address.getAddressLine2());
                pst.setString(5, address.getCity());
                pst.setString(6, address.getState());
                pst.setString(7, address.getPincode());
                pst.setString(8, address.getCountry());
                pst.setString(9, address.getPhoneNumber());
                pst.setString(10, address.getNearbyLocation());
                pst.setString(11, address.getPlaceType());
                pst.setString(12, email);
                pst.executeUpdate();
            } else {
                // ✅ Insert new
                String insertSql = "INSERT INTO address (user_email, name, door_number, address_line1, address_line2, city, state, pincode, country, phone_number, nearby_location, place_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(insertSql);
                pst.setString(1, email);
                pst.setString(2, address.getName());
                pst.setString(3, address.getDoorNo());
                pst.setString(4, address.getAddressLine1());
                pst.setString(5, address.getAddressLine2());
                pst.setString(6, address.getCity());
                pst.setString(7, address.getState());
                pst.setString(8, address.getPincode());
                pst.setString(9, address.getCountry());
                pst.setString(10, address.getPhoneNumber());
                pst.setString(11, address.getNearbyLocation());
                pst.setString(12, address.getPlaceType());
                pst.executeUpdate();
            }

            rs.close();
            checkStmt.close();
            conn.close();

            // ✅ After saving, go back to Checkout page
            request.getRequestDispatcher("CheckoutServlet").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error saving address: " + e.getMessage());
            request.getRequestDispatcher("Checkout.jsp").forward(request, response);
        }
    }

    // 💌 Handles GET request from profile link "Your Address"
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = (String) request.getSession().getAttribute("userEmail");
        String mode = request.getParameter("mode"); // 💡 Read mode=edit from URL

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");

            String sql = "SELECT * FROM address WHERE user_email = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // ✅ Address found — always set the address
                Address address = new Address();
                address.setUserEmail(email);
                address.setName(rs.getString("name"));
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

                request.setAttribute("address", address);
            }

            // ✅ Now set mode
            if ("edit".equals(mode)) {
                request.setAttribute("mode", "edit");  // Show form in JSP
            } else {
                request.setAttribute("mode", "view");  // Default: show saved address
            }

            rs.close();
            pst.close();
            conn.close();

            request.getRequestDispatcher("Address.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load address: " + e.getMessage());
            request.getRequestDispatcher("Profile.jsp").forward(request, response);
        }
    }
}

