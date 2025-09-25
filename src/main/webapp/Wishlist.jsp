<%@ page import="java.util.*, com.bookstore.dao.WishListDAO, com.bookstore.model.WishListItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<style>
    .wishlist-container {
        padding: 30px;
    }

    .wishlist-title {
        text-align: center;
        color: #333;
    }

    .empty-wishlist {
        text-align: center;
        color: gray;
        font-size: 18px;
    }

    .wishlist-grid {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        gap: 30px;
        margin-top: 20px;
    }

    .wishlist-item {
        border: 1px solid #ccc;
        padding: 15px;
        width: 250px;
        text-align: center;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }

    .wishlist-item img {
        width: 150px;
        height: 200px;
        object-fit: cover;
    }

    .wishlist-item h3 {
        margin: 10px 0 5px 0;
        font-size: 18px;
        color: #222;
    }

    .wishlist-item p {
        margin: 5px 0;
    }

    .wishlist-price {
        color: green;
        font-weight: bold;
    }

    .remove-button {
        background-color: crimson;
        color: white;
        border: none;
        padding: 8px 12px;
        border-radius: 5px;
        cursor: pointer;
        margin-top: 10px;
    }

    .remove-button:hover {
        background-color: darkred;
    }
    .profile-buttons {
        margin-top: 20px;
    }

    .profile-buttons a {
        display: inline-block;
        text-decoration: none;
        margin: 10px 8px;
        background-color: #007bff;
        color: white;
        padding: 10px 16px;
        border-radius: 8px;
        font-size: 14px;
        transition: background-color 0.3s ease;
    }
    .profile-buttons a:hover {
        background-color: #0056b3;
    }
</style>



<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect("Login.jsp");
        return;
    }

    WishListDAO dao = new WishListDAO();
    List<WishListItem> wishlist = dao.getWishlistByUser(userId);
%>

<div class="wishlist-container">
    <h2 class="wishlist-title">My Wishlist 💖</h2>

    <%
        if (wishlist.isEmpty()) {
    %>
        <p class="empty-wishlist">You haven’t added anything to your wishlist yet 😢</p>
    <%
        } else {
    %>

    <div class="wishlist-grid">
        <% for (WishListItem item : wishlist) { %>
            <div class="wishlist-item">
                 <img src="<%= item.getImage().replace(" ", "%20") %>" alt="Book Cover" style="height: 220px; width: 150px;">
                <h3><%= item.getTitle() %></h3>
                <p>by <%= item.getAuthor() %></p>
                <p class="wishlist-price">₹ <%= item.getPrice() %></p>

                <form action="RemoveWishListServlet" method="get">
                    <input type="hidden" name="bookId" value="<%= item.getBookId() %>">
                    <button type="submit" class="remove-button">Remove</button>
                </form>
                <a href="HomeServlet" class="home-btn">Go Back To Home 🏠 </a>
            </div>
        <% } %>
    </div>

    <%
        }
    %>
    
</div>

<jsp:include page="includes/Footer.jsp" />

