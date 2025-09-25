package com.bookstore.servlet;

import com.bookstore.dao.WishListDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/RemoveWishListServlet")
public class RemoveWishListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        int bookId = Integer.parseInt(request.getParameter("bookId"));

        if (userId != null) {
            WishListDAO dao = new WishListDAO();
            dao.removeFromWishlist(userId, bookId);
        }

        response.sendRedirect("Wishlist.jsp");
    }
}
