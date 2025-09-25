<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order Confirmation</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f4f4;
            padding: 40px;
        }

        .container {
            background: white;
            max-width: 600px;
            margin: auto;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
            text-align: center;
        }

        h2 {
            color: #28a745;
        }

        .btn-success {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 8px;
        }

        .btn-success:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>✅ Order Placed Successfully!</h2>
    <%
    com.bookstore.model.User currentUser = (com.bookstore.model.User) session.getAttribute("user");
    String name = currentUser != null ? currentUser.getName() : "Guest";
%>
<p>Thank you for shopping with us, <strong><%= name %></strong>!</p>
    
   <!--   <p>Thank you for shopping with us, <strong>< %= session.getAttribute("username") %></strong>!</p>  -->

    <p>Your Order ID: <strong>#<%= request.getAttribute("orderId") %></strong></p>
    <p>Total Amount Paid: <strong>₹<%= request.getAttribute("totalAmount") %></strong></p>

    <a href="HomeServlet" class="btn-success">Continue Shopping</a>
</div>

</body>
</html>
