package com.bookstore.servlet;

import com.bookstore.dao.BookDAO;
import com.bookstore.model.Book;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/BookDetailsServlet")
public class BookDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int bookId = Integer.parseInt(request.getParameter("bookId"));

            BookDAO bookDAO = new BookDAO();
            Book selectedBook = bookDAO.getBookById(bookId); // the clicked book
            List<Book> allBooks = bookDAO.getAllBooks();     // get all books

            // ✅ Filter out the selected book
            List<Book> remainingBooks = allBooks.stream()
                    .filter(book -> book.getId() != bookId)
                    .collect(Collectors.toList());

            request.setAttribute("book", selectedBook);
            request.setAttribute("remainingBooks", remainingBooks);

            RequestDispatcher dispatcher = request.getRequestDispatcher("Bookdetails.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("Home.jsp");
        }
    }
}
