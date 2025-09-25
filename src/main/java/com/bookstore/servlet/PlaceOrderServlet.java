//package com.bookstore.servlet;
//
//import com.bookstore.dao.OrderDAO;
//import com.bookstore.dao.AddressDAO;
//import com.bookstore.model.*;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.util.*;
//
//@WebServlet("/PlaceOrderServlet")
//public class PlaceOrderServlet extends HttpServlet {
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        HttpSession session = request.getSession();
//
//        // ✅ Get user and cart from session
//        User user = (User) session.getAttribute("user");
//        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
//
//        if (user == null || cart == null || cart.isEmpty()) {
//            response.sendRedirect("Checkout.jsp");
//            return;
//        }
//
//        try {
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Books", "root", "sumanth$3031");
//
//            AddressDAO addressDAO = new AddressDAO(conn); // You must have this DAO class
//            Address finalAddress = null;
//
//            // ✅ Check user selection
//            String useSaved = request.getParameter("useSavedAddress");
////            if ("true".equals(useSaved)) {
////                finalAddress = addressDAO.getAddressByUserId(user.getId());
////            } else {
////                // ✅ Build new address from form
////                String name = request.getParameter("name");
////                String doorNo = request.getParameter("doorNo");
////                String line1 = request.getParameter("addressLine1");
////                String line2 = request.getParameter("addressLine2");
////                String city = request.getParameter("city");
////                String state = request.getParameter("state");
////                String pincode = request.getParameter("pincode");
////                String phone = request.getParameter("phone");
////
////                if (name == null || name.isEmpty() || doorNo == null || city == null || state == null || pincode == null || phone == null) {
////                    request.setAttribute("error", "Please fill in all address fields.");
////                    request.getRequestDispatcher("Checkout.jsp").forward(request, response);
////                    return;
////                }
////
////                finalAddress = new Address();
////                finalAddress.setName(name);
////                finalAddress.setDoorNo(doorNo);
////                finalAddress.setAddressLine1(line1);
////                finalAddress.setAddressLine2(line2);
////                finalAddress.setCity(city);
////                finalAddress.setState(state);
////                finalAddress.setPincode(pincode);
////                finalAddress.setPhoneNumber(phone);
////            }
//
//            // ✅ Prepare Order object
//            Order order = new Order();
//            order.setUserId(user.getId());
//
//            double totalAmount = 0;
//            List<OrderItem> orderItems = new ArrayList<>();
//
//            for (CartItem item : cart.values()) {
//                Book book = item.getBook();
//                int quantity = item.getQuantity();
//                double price = book.getPrice();
//                totalAmount += quantity * price;
//
//                OrderItem orderItem = new OrderItem();
//                orderItem.setBookId(book.getId());
//                orderItem.setQuantity(quantity);
//                orderItem.setPrice(price);
//                orderItems.add(orderItem);
//            }
//
//            // ✅ Convert address to string
//            String fullAddress = finalAddress.getName() + ", " +
//                                 finalAddress.getDoorNo() + ", " +
//                                 finalAddress.getAddressLine1() + ", " +
//                                 (finalAddress.getAddressLine2() != null ? finalAddress.getAddressLine2() + ", " : "") +
//                                 finalAddress.getCity() + ", " +
//                                 finalAddress.getState() + " - " +
//                                 finalAddress.getPincode() + ". Phone: " +
//                                 finalAddress.getPhoneNumber();
//
//            order.setTotalAmount(totalAmount);
//            order.setAddress(fullAddress);
//            order.setItems(orderItems);
//
//            // ✅ Save order
//            OrderDAO orderDAO = new OrderDAO(conn);
//            int orderId = orderDAO.saveOrder(order);
//            orderDAO.saveOrderItems(orderId, orderItems);
//
//            // ✅ Clear cart
//            session.removeAttribute("cart");
//
//            // ✅ Send to confirmation page
//            request.setAttribute("orderId", orderId);
//            request.setAttribute("totalAmount", totalAmount);
//            request.getRequestDispatcher("OrderConfirmation.jsp").forward(request, response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute("errorMessage", "Something went wrong while placing your order 💔");
//            request.getRequestDispatcher("Checkout.jsp").forward(request, response);
//
//        }
//    }
//}



package com.bookstore.servlet;

import com.bookstore.dao.OrderDAO;
import com.bookstore.dao.AddressDAO;
import com.bookstore.model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

@WebServlet("/PlaceOrderServlet")
public class PlaceOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // ✅ Get user and cart from session
        User user = (User) session.getAttribute("user");
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");

        if (user == null || cart == null || cart.isEmpty()) {
            response.sendRedirect("Checkout.jsp");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");

            AddressDAO addressDAO = new AddressDAO(conn);
            Address finalAddress = null;

            // ✅ Get saved address from DB using user email
            finalAddress = addressDAO.getAddressByUserEmail(user.getEmail());

            // ✅ If somehow address not found, show error
            if (finalAddress == null) {
                request.setAttribute("errorMessage", "Shipping address not found. Please fill the form again.");
                request.getRequestDispatcher("Checkout.jsp").forward(request, response);
                return;
            }

            // ✅ Prepare Order object
            Order order = new Order();
            order.setUserId(user.getId());

            double totalAmount = 0;
            List<OrderItem> orderItems = new ArrayList<>();

            for (CartItem item : cart.values()) {
                Book book = item.getBook();
                int quantity = item.getQuantity();
                double price = book.getPrice();
                totalAmount += quantity * price;

                OrderItem orderItem = new OrderItem();
                orderItem.setBookId(book.getId());
                orderItem.setQuantity(quantity);
                orderItem.setPrice(price);
                orderItems.add(orderItem);
            }

            // ✅ Convert Address to formatted string
            String fullAddress = finalAddress.getName() + ", " +
                                 finalAddress.getDoorNo() + ", " +
                                 finalAddress.getAddressLine1() + ", " +
                                 (finalAddress.getAddressLine2() != null ? finalAddress.getAddressLine2() + ", " : "") +
                                 finalAddress.getCity() + ", " +
                                 finalAddress.getState() + " - " +
                                 finalAddress.getPincode() + ", " +
                                 finalAddress.getCountry() + ". Phone: " +
                                 finalAddress.getPhoneNumber();

            order.setTotalAmount(totalAmount);
            order.setAddress(fullAddress);
            order.setItems(orderItems);

            // ✅ Save order to DB
            OrderDAO orderDAO = new OrderDAO(conn);
            int orderId = orderDAO.saveOrder(order);
            orderDAO.saveOrderItems(orderId, orderItems);

            // ✅ Clear cart after successful order
            session.removeAttribute("cart");

            // ✅ Set confirmation details and go to confirmation page
            request.setAttribute("orderId", orderId);
            request.setAttribute("totalAmount", totalAmount);
            request.getRequestDispatcher("OrderConfirmation.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Something went wrong while placing your order 💔");
            request.getRequestDispatcher("Checkout.jsp").forward(request, response);
        }
    }
}

