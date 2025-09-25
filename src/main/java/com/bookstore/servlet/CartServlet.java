//package com.bookstore.servlet;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
//
//import java.io.IOException;
//import java.util.*;
//
//import com.bookstore.model.Book;
//import com.bookstore.model.CartItem;
//import com.bookstore.model.User;
//import com.bookstore.dao.BookDAO;
//
//@WebServlet("/CartServlet")
//public class CartServlet extends HttpServlet {
//    @SuppressWarnings("unchecked")
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        HttpSession session = request.getSession();
//
//        // 💖 STEP 1: Check login
//        User user = (User) session.getAttribute("user");
//
//        if (user == null) {
//            // Not logged in, redirect to Login.jsp
//            response.sendRedirect("Login.jsp?checkout=true");
//            return;
//        }
//
//        // ✅ STEP 2: Proceed with cart actions
//        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
//        if (cart == null) {
//            cart = new HashMap<>();
//        }
//
//        String action = request.getParameter("action");
//        String bookIdStr = request.getParameter("bookId");
//
//        if (bookIdStr == null || bookIdStr.isEmpty()) {
//            response.sendRedirect("Cart.jsp");
//            return;
//        }
//
//        int bookId;
//        try {
//            bookId = Integer.parseInt(bookIdStr);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            response.sendRedirect("Cart.jsp");
//            return;
//        }
//
//        BookDAO bookDAO = new BookDAO();
//
//        switch (action) {
//            case "remove":
//                cart.remove(bookId);
//                break;
//
//            case "update":
//                try {
//                    int quantity = Integer.parseInt(request.getParameter("quantity"));
//                    if (quantity <= 0) {
//                        cart.remove(bookId);
//                    } else {
//                        CartItem item = cart.get(bookId);
//                        if (item != null) {
//                            item.setQuantity(quantity);
//                        }
//                    }
//                } catch (NumberFormatException e) {
//                    e.printStackTrace();
//                }
//                break;
//
//            default: // add
//                Book book = bookDAO.getBookById(bookId);
//                if (book != null) {
//                    if (cart.containsKey(bookId)) {
//                        CartItem item = cart.get(bookId);
//                        item.setQuantity(item.getQuantity() + 1);
//                    } else {
//                        cart.put(bookId, new CartItem(book, 1));
//                    }
//                }
//                break;
//        }
//
//        session.setAttribute("cart", cart);
//        response.sendRedirect("Cart.jsp");
//    }
//}





package com.bookstore.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.*;

import com.bookstore.model.Book;
import com.bookstore.model.CartItem;
import com.bookstore.model.User;
import com.bookstore.dao.BookDAO;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // ✅ Login Check
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("Login.jsp?checkout=true");
            return;
        }

        // ✅ Prepare cart
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }

        String action = request.getParameter("action");
        String bookIdStr = request.getParameter("bookId");

        if (bookIdStr == null || bookIdStr.isEmpty()) {
            response.sendRedirect("Cart.jsp");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("Cart.jsp");
            return;
        }

        BookDAO bookDAO = new BookDAO();

        // 💖 Prevent NullPointerException by defaulting to "add"
        if (action == null) {
            action = "add";
        }

        switch (action) {
            case "remove":
                cart.remove(bookId);
                break;

            case "update":
                try {
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    if (quantity <= 0) {
                        cart.remove(bookId);
                    } else {
                        CartItem item = cart.get(bookId);
                        if (item != null) {
                            item.setQuantity(quantity);
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;

            case "add": // explicitly handled now
            default:
                Book book = bookDAO.getBookById(bookId);
                if (book != null) {
                    if (cart.containsKey(bookId)) {
                        CartItem item = cart.get(bookId);
                        item.setQuantity(item.getQuantity() + 1);
                    } else {
                        cart.put(bookId, new CartItem(book, 1));
                    }
                }
                break;
        }

        session.setAttribute("cart", cart);
        response.sendRedirect("Cart.jsp");
    }
}

