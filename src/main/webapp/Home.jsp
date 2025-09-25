<%@ page import="java.util.List, com.bookstore.model.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // ✅ Session check to prevent unauthenticated access
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("Login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Online Bookstore</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            text-align: center;
        }
        h1 {
            margin-top: 30px;
            font-size: 32px;
            color: #333;
        }
        .greeting {
            font-size: 20px;
            margin-top: 10px;
        }
        .book-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 20px;
            padding: 20px;
        }
        .book-card {
            flex: 0 1 calc(25% - 40px);
            max-width: 220px;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            padding: 15px;
            box-sizing: border-box;
            text-align: center;
            transition: 0.3s ease;
        }
        .book-card img {
            width: 100%;
            height: auto;
            max-height: 250px;
            object-fit: cover;
            border-radius: 5px;
        }
        .book-card:hover {
            transform: scale(1.05);
            box-shadow: 0 8px 20px rgba(0,0,0,0.2);
        }

        .book-card:hover img {
            transform: scale(1.1);
        }

        .book-card h3 {
            margin: 15px 0 5px;
            font-size: 18px;
            color: #333;
        }
        .book-card p {
            margin: 5px 0;
            font-size: 14px;
            color: #555;
        }
        .book-card button {
            margin-top: 10px;
            padding: 8px 14px;
            background: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
        }
        .book-card button:hover {
            background: #388E3C;
        }

        @media screen and (max-width: 1000px) {
            .book-card {
                flex: 0 1 calc(33% - 40px);
            }
        }
        @media screen and (max-width: 700px) {
            .book-card {
                flex: 0 1 calc(50% - 40px);
            }
        }
        @media screen and (max-width: 500px) {
            .book-card {
                flex: 0 1 calc(100% - 40px);
            }
        }

    </style>
</head>
<body>

    <jsp:include page="includes/Headerblock.jsp" />

    <h4>📚 Online Bookstore</h4>
    <div class="greeting">
        <% String userName = (String) session.getAttribute("userName"); %>
        Hello, <strong><%= userName != null ? userName : "Guest" %>!</strong> Welcome to your personal book library 📖✨
    </div>

    <div class="book-container">
        <%
            List<Book> books = (List<Book>) request.getAttribute("bookList");
            if (books != null && !books.isEmpty()) {
                for (Book book : books) {
                    String img = book.getImageUrl() != null 
                        ? book.getImageUrl().replace(" ", "%20") 
                        : "default.png";
        %>

        <div class="book-card">
            <a href="BookDetailsServlet?bookId=<%= book.getId() %>">
                <img src="<%= request.getContextPath() + "/" + img %>" alt="<%= book.getTitle() %>">
                <h3><%= book.getTitle() %></h3>
            </a>
            <p><strong>Author:</strong> <%= book.getAuthor() %></p>
            <p><strong>Price:</strong> ₹<%= book.getPrice() %></p>
            <form action="CartServlet" method="post">
                <input type="hidden" name="bookId" value="<%= book.getId() %>">
                <button type="submit">Add to Cart</button>
            </form>
            <form action="WishListServlet" method="post">
                <input type="hidden" name="bookId" value="<%= book.getId() %>">
                <button type="submit">❤️ Add to Wishlist</button>
            </form>
        </div>

        <%
                }
            } else {
        %>
            <p style="color:red;">No books to display right now 😢</p>
        <%
            }
        %>
    </div>

    <jsp:include page="includes/Homevideo.jsp" /> 
    <jsp:include page="includes/Keycustomers.jsp" />
    <jsp:include page="includes/Customerreviews.jsp" />  
    <jsp:include page="includes/Footer.jsp" />
    
</body>
</html>
