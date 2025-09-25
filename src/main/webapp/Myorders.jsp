<%@ page import="java.util.*, com.bookstore.model.Order" %>
<%@ page session="true" %>

<html>
<head>
    <title>📦 My Orders</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f4f4f4;
            padding: 40px;
        }

        h2 {
            color: #333;
            text-align: center;
        }

        table {
            margin: 30px auto;
            border-collapse: collapse;
            width: 90%;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0,0,0,0.05);
        }

        th, td {
            padding: 15px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .no-orders {
            text-align: center;
            margin-top: 40px;
            color: red;
            font-size: 20px;
        }

    </style>
</head>
<body>

<h2> My Orders</h2>

<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");

    if (orders == null || orders.isEmpty()) {
%>
    <div class="no-orders">You haven't placed any orders yet 💔</div>
<%
    } else {
%>
    <table>
        <tr>
            <th>Order ID</th>
            <th>Total Amount</th>
            <th>Address</th>
            <th>Order Date</th>
        </tr>
<%
        for (Order order : orders) {
%>
        <tr>
            <td>#<%= order.getId() %></td>
            <td><%= order.getTotalAmount() %></td>
            <td><%= order.getAddress() %></td>
            <td><%= order.getOrderDate() %></td>
        </tr>
<%
        }
%>
    </table>
<%
    }
%>

</body>
</html>
