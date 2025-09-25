<%@ page import="com.bookstore.model.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Book book = (Book) request.getAttribute("book");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= book.getTitle() %> - Book Details</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }
        .book-details {
            max-width: 1500px;
            margin: 10px auto;
            display: flex;
            gap: 30px;
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            padding: 30px;
        }
        .book-details img {
            width: 300px;
            height: 400px;
            object-fit: cover;
            border-radius: 10px;
        }
        .book-info {
            flex: 1;
        }
        .book-info h1 {
            font-size: 26px;
            margin-bottom: 10px;
        }
        .book-info p {
            margin: 8px 0;
            font-size: 16px;
            line-height: 1.6;
            color: #333;
        }
        .actions {
            margin-top: 20px;
        }
        .actions form {
            display: inline-block;
            margin-right: 10px;
        }
        .actions button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
        }
        .actions button:hover {
            background-color: #388E3C;
        }

        hr {
            margin: 30px 0;
            border: 1px solid #eee;
        }

        .remaining-books {
            text-align: center;
            margin: 20px auto;
        }
    </style>
</head>
<body>

<jsp:include page="includes/Headerblock.jsp" />

<div class="book-details">
    <img src="<%= request.getContextPath() + "/" + book.getImageUrl().replace(" ", "%20") %>" alt="<%= book.getTitle() %>">

    <div class="book-info">
        <h1><%= book.getTitle() %></h1>
        <p><strong>Author:</strong> <%= book.getAuthor() %></p>
        <p><strong>Price:</strong> ₹<%= book.getPrice() %></p>

        <hr>

        <p><strong>Description:</strong><br> <%= book.getDescription() %></p>

        <hr>

        <p><strong>About the Author:</strong><br> <%= book.getAuthorInfo() %></p>

        <div class="actions">
            <form action="CartServlet" method="post">
                <input type="hidden" name="bookId" value="<%= book.getId() %>">
                <button type="submit">🛒 Add to Cart </button>
            </form>
            <form action="WishListServlet" method="post">
                <input type="hidden" name="bookId" value="<%= book.getId() %>">
                <button type="submit">❤️ Add to Wishlist</button>
            </form>
        </div>
    </div>
</div>
<jsp:include page="includes/Remainingbooks.jsp" />


<jsp:include page="includes/Footer.jsp" />

</body>
</html>
