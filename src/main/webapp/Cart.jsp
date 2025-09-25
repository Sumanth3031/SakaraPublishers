<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bookstore.model.CartItem, com.bookstore.model.Book, java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your Cart 🛒</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f9f9f9;
            padding: 30px;
            margin: 0;
            text-align: center;
        }

        h3 {
            color: #333;
            font-size: 28px;
            margin-bottom: 20px;
        }

        table {
            margin: auto;
            width: 90%;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }

        th, td {
            padding: 15px;
            border: 1px solid #ddd;
            text-align: center;
            font-size: 16px;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        input[type="number"] {
            padding: 5px;
            width: 60px;
            text-align: center;
        }

        button {
            padding: 6px 12px;
            border: none;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
        }

        button[type="submit"] {
            background-color: #4CAF50;
            color: white;
        }

        button[type="submit"]:hover {
            background-color: #388E3C;
        }

        .remove-btn {
            background-color: #e74c3c;
            color: white;
        }

        .remove-btn:hover {
            background-color: #c0392b;
        }

        .checkout-btn {
            margin-top: 25px;
            padding: 12px 24px;
            background-color: #007BFF;
            color: white;
            font-size: 16px;
            border: none;
            border-radius: 8px;
        }

        .checkout-btn:hover {
            background-color: #0056b3;
        }

        .empty-msg {
            font-size: 20px;
            color: red;
        }
        
        .cart-btn {
        display: inline-block;
        margin: 10px 15px;
        padding: 10px 20px;
        font-size: 16px;
        color: white;
        background-color: #007bff;
        border: none;
        border-radius: 8px;
        text-decoration: none;
        transition: background-color 0.3s ease;
    }

    .cart-btn:hover {
        background-color: #0056b3;
    }

    .home-btn {
        background-color: #28a745;
    }

    .home-btn:hover {
        background-color: #1e7e34;
    }
    
    </style>
</head>
<body>

<%
    Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
    if (cart == null || cart.isEmpty()) {
%>
    <h3 class="empty-msg">Your cart is empty 💔</h3>
<%
    } else {
        double total = 0;
%>

    <h3>Your Cart 🛒</h3>
    <table>
        <tr>
            <th>Book</th>
            <th>Author</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Subtotal</th>
            <th>Action</th>
        </tr>
<%
    for (CartItem item : cart.values()) {
        Book book = item.getBook();
        if (book == null) continue;

        int quantity = item.getQuantity();
        double subtotal = quantity * book.getPrice();
        total += subtotal;
%>
        <tr>
            <td><%= book.getTitle() %></td>
            <td><%= book.getAuthor() %></td>
            <td><%= book.getPrice() %></td>
            <td>
                <form action="CartServlet" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="bookId" value="<%= book.getId() %>">
                    <input type="number" name="quantity" value="<%= quantity %>" min="1">
                    <button type="submit">Update</button>
                </form>
            </td>
            <td><%= subtotal %></td>
            <td>
                <form action="CartServlet" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="remove">
                    <input type="hidden" name="bookId" value="<%= book.getId() %>">
                    <button type="submit" class="remove-btn">Remove</button>
                </form>
            </td>
        </tr>
<%
    }
%>
        <tr>
            <td colspan="4"><strong>Total:</strong></td>
            <td><strong><%= total %></strong></td>
            <td></td>
        </tr>
    </table>
<!--  
    <form action="Checkout.jsp" method="get">
        <button type="submit" class="checkout-btn">Proceed to Checkout</button>
    </form>
    
    -->
    <!--  
    <form action="ViewAddressServlet" method="get">
    <input type="hidden" name="from" value="checkout" />
    <button type="submit" class="checkout-btn">Proceed to Checkout</button>
</form>
    -->
    <form action="CheckoutServlet" method="get"> <!-- Changed to CheckoutServlet -->
    <button type="submit" class="checkout-btn">Proceed to Checkout</button>
</form>

<%
    }
%>
<div style="text-align:center; margin-top: 30px;">
    <a href="HomeServlet" class="cart-btn">➕ Add More Books</a>
    <a href="HomeServlet" class="cart-btn home-btn">🏠 Go To Home</a>
</div>
<jsp:include page="includes/Footer.jsp" />
</body>
</html>
