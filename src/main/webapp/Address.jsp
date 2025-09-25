<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" import="java.sql.*" %>
<%@ page import="com.bookstore.model.Address" %>

<%
    String name = (String) session.getAttribute("userName");
    String email = (String) session.getAttribute("userEmail");

    if (name == null || email == null) {
        response.sendRedirect("Login.jsp");
        return;
    }

    // Updated variable names to match servlet attributes
    Address savedAddress = (Address) request.getAttribute("address");
    String mode = (String) request.getAttribute("mode");
    boolean isEditMode = "edit".equals(mode);

    String successMessage = (String) request.getAttribute("successMessage");

    String door = "", line1 = "", line2 = "", city = "", state = "", pin = "", country = "", phone = "", nearby = "", place = "";
    if (savedAddress != null) {
        door = savedAddress.getDoorNo();
        line1 = savedAddress.getAddressLine1();
        line2 = savedAddress.getAddressLine2();
        city = savedAddress.getCity();
        state = savedAddress.getState();
        pin = savedAddress.getPincode();
        country = savedAddress.getCountry();
        phone = savedAddress.getPhoneNumber();
        nearby = savedAddress.getNearbyLocation();
        place = savedAddress.getPlaceType();
    }
%>

<html>
<head>
    <title>Manage Address</title>
    <style>
        body { font-family: 'Segoe UI'; background-color: #f2f2f2; padding: 30px; }
        .box {
            background: #fff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 6px 12px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: auto;
        }
        .box h2 { color: #333; text-align: center; margin-bottom: 20px; }
        input, select {
            width: 100%; padding: 10px; margin: 10px 0;
            border-radius: 8px; border: 1px solid #ccc;
        }
        button, .btn {
            background-color: #28a745; color: white;
            padding: 12px; width: 100%;
            border: none; font-size: 16px;
            border-radius: 8px; cursor: pointer;
            text-decoration: none; display: block; text-align: center;
        }
        button:hover, .btn:hover { background-color: #218838; }
        .message { color: green; text-align: center; margin-bottom: 10px; }
        .address-display-box {
            border: 1px solid #ccc;
            background: #f9f9f9;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="box">

    <% if (successMessage != null) { %>
        <p class="message"><%= successMessage %></p>
    <% } %>

    <% if ("view".equals(mode) && savedAddress != null) { %>
        <!-- Show saved address in view mode -->
        <div class="address-display-box">
            <h3>Your Saved Address</h3>
            <p><strong><%= savedAddress.getName() %></strong></p>
            <p><%= savedAddress.getDoorNo() %>, <%= savedAddress.getAddressLine1() %>, <%= savedAddress.getAddressLine2() %></p>
            <p><%= savedAddress.getCity() %>, <%= savedAddress.getState() %> - <%= savedAddress.getPincode() %></p>
            <p><%= savedAddress.getCountry() %></p>
            <p><strong>Phone:</strong> <%= savedAddress.getPhoneNumber() %></p>
            <p><strong>Nearby:</strong> <%= savedAddress.getNearbyLocation() %></p>
            <p><strong>Place Type:</strong> <%= savedAddress.getPlaceType() %></p>
        </div>

        <!-- Update Address Button -->
        <form action="AddressServlet" method="get">
            <input type="hidden" name="mode" value="edit" />
            <button type="submit">Update Address</button>
        </form>
        

    <% } else { %>
        <!-- Show form to add or edit address -->
        <h2><%= isEditMode ? "Edit Your Address" : "Add New Address" %></h2>

        <form action="AddressServlet" method="post">
            <input type="text" name="name" value="<%= name %>" readonly />
            <input type="text" name="user_email" value="<%= email %>" readonly />
            <input type="text" name="door_number" placeholder="Door Number" value="<%= door %>" required />
            <input type="text" name="address_line1" placeholder="Address Line 1" value="<%= line1 %>" required />
            <input type="text" name="address_line2" placeholder="Address Line 2" value="<%= line2 %>" />
            <input type="text" name="city" placeholder="City" value="<%= city %>" required />
            <input type="text" name="state" placeholder="State" value="<%= state %>" required />
            <input type="text" name="pincode" placeholder="Pincode" value="<%= pin %>" required />
            <input type="text" name="country" placeholder="Country" value="<%= country %>" required />
            <input type="text" name="phone_number" placeholder="Phone Number" value="<%= phone %>" required />
            <input type="text" name="nearby_location" placeholder="Nearby Location" value="<%= nearby %>" />
            <select name="place_type" required>
                <option value="">Select Place Type</option>
                <option value="Home" <%= "Home".equals(place) ? "selected" : "" %>>Home</option>
                <option value="Work" <%= "Work".equals(place) ? "selected" : "" %>>Work</option>
            </select>
            <button type="submit"><%= isEditMode ? "Update Address" : "Save Address" %></button>
        </form>
        
    <% } %>
    

</div>

</body>
</html>
