package com.bookstore.servlet;

import com.bookstore.dao.WishListDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/WishListServlet")
public class WishListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the book ID from the form
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        // Get the logged-in user's ID from the session
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        // If user is logged in
        if (userId != null) {
            WishListDAO dao = new WishListDAO();
            boolean added = dao.addToWishlist(userId, bookId);
            if (added) {
                response.sendRedirect("Wishlist.jsp");
            } else {
                response.sendRedirect("Home.jsp?error=wishlistFail");
            }

        } else {
            // If user is not logged in, redirect to login page
            response.sendRedirect("Login.jsp");
        }
    }
}
