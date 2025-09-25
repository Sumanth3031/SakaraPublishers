package com.bookstore.servlet;

import com.bookstore.model.Order;
import com.bookstore.dao.OrderDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

@WebServlet("/MyOrdersServlet")
public class MyOrdersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        com.bookstore.model.User user = (com.bookstore.model.User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        try {
            // Connect to DB
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");

            OrderDAO orderDAO = new OrderDAO(conn);
            List<Order> orderList = orderDAO.getOrdersByUserId(user.getId());

            request.setAttribute("orders", orderList);
            request.getRequestDispatcher("Myorders.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace(); // ✅ print the real error to Tomcat console
            response.setContentType("text/plain");
            response.getWriter().println("Error: " + e.getMessage());
        }

    }
}
