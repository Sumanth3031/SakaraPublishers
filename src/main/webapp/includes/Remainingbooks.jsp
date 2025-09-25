<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bookstore.model.Book" %>

<style>
    .remaining-section {
        margin-top: 50px;
        padding: 20px;
        background-color: #f9f9f9;
        text-align: center;
    }
    .remaining-section h2 {
        font-size: 24px;
        color: #333;
        margin-bottom: 20px;
    }
    .remaining-books {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        gap: 20px;
    }
    .remaining-card {
        width: 200px;
        background: #fff;
        border-radius: 10px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        padding: 15px;
        box-sizing: border-box;
        transition: transform 0.3s ease;
        text-align: center;
    }
    .remaining-card:hover {
        transform: scale(1.05);
    }
    .remaining-card img {
        width: 100%;
        height: 250px;
        object-fit: cover;
        border-radius: 5px;
    }
    .remaining-card h3 {
        font-size: 16px;
        margin: 10px 0 5px;
        color: #333;
    }
    .remaining-card p {
        font-size: 14px;
        color: #555;
        margin: 5px 0;
    }
</style>

<div class="remaining-section">
    <h2>📚 More Books You May Like</h2>

    <div class="remaining-books">
        <%
            List<Book> remainingBooks = (List<Book>) request.getAttribute("remainingBooks");
            if (remainingBooks != null && !remainingBooks.isEmpty()) {
                for (Book book : remainingBooks) {
                    String img = book.getImageUrl() != null ? book.getImageUrl().replace(" ", "%20") : "default.png";
        %>
        <div class="remaining-card">
            <a href="BookDetailsServlet?bookId=<%= book.getId() %>">
                <img src="<%= request.getContextPath() + "/" + img %>" alt="<%= book.getTitle() %>" />
                <h3><%= book.getTitle() %></h3>
            </a>
            <p><strong>Author:</strong> <%= book.getAuthor() %></p>
            <p><strong>Price:</strong> ₹<%= book.getPrice() %></p>
        </div>
        <%
                }
            } else {
        %>
            <p style="color:red;">No other books found 😢</p>
        <%
            }
        %>
    </div>
</div>
