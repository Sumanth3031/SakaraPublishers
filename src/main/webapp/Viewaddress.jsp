<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.ResultSet" %>
<%
    ResultSet rs = (ResultSet) request.getAttribute("addressData");
%>

<html>
<head>
    <title>Your Address</title>
    <style>
        body {
            font-family: Arial;
            background-color: #f9f9f9;
            padding: 30px;
        }

        .address-box {
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: auto;
        }

        .address-box h2 {
            color: #333;
            margin-bottom: 20px;
        }

        .address-box p {
            font-size: 16px;
            margin: 8px 0;
            color: #555;
        }

        .edit-btn {
            margin-top: 20px;
            display: inline-block;
            background-color: #007bff;
            color: white;
            padding: 10px 16px;
            text-decoration: none;
            border-radius: 8px;
        }

        .edit-btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<div class="address-box">
    <h2>📬 Your Saved Address</h2>
    <p><strong>Name:</strong> <%= rs.getString("name") %></p>
    <p><strong>Door Number:</strong> <%= rs.getString("door_number") %></p>
    <p><strong>Address Line 1:</strong> <%= rs.getString("address_line1") %></p>
    <p><strong>Address Line 2:</strong> <%= rs.getString("address_line2") %></p>
    <p><strong>City:</strong> <%= rs.getString("city") %></p>
    <p><strong>State:</strong> <%= rs.getString("state") %></p>
    <p><strong>Pincode:</strong> <%= rs.getString("pincode") %></p>
    <p><strong>Country:</strong> <%= rs.getString("country") %></p>
    <p><strong>Phone:</strong> <%= rs.getString("phone_number") %></p>
    <p><strong>Nearby Location:</strong> <%= rs.getString("nearby_location") %></p>
    <p><strong>Place Type:</strong> <%= rs.getString("place_type") %></p>

    <!-- 💡 This button sends user to the form with edit mode -->
    <a class="edit-btn" href="Address.jsp?edit=true">✏️ Edit Address</a>
</div>

</body>
</html>
