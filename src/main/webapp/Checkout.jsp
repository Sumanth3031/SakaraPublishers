
<%@ page import="java.util.*, com.bookstore.model.CartItem, com.bookstore.model.Address, com.bookstore.model.Book, com.bookstore.model.User" %>
<%@ page session="true" %>

<html>
<head>
    <title>Checkout | Sakara Bookstore</title>
    <style>
body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #f7f7f7;
    margin: 0;
    padding: 0;
}

.container {
    width: 90%;
    max-width: 1000px;
    background-color: #fff;
    margin: 40px auto;
    padding: 30px;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0,0,0,0.1);
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 30px;
}

table th, table td {
    padding: 12px;
    text-align: center;
    border: 1px solid #ddd;
}

table th {
    background-color: #f2f2f2;
}

input[type="text"], textarea {
    padding: 10px;
    width: 60%;
    border-radius: 5px;
    border: 1px solid #ccc;
    margin: 5px 0 15px 0;
}

input[type="submit"], .btn {
    padding: 10px 20px;
    background-color: #007bff;
    color: #fff;
    font-weight: bold;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

input[type="submit"]:hover, .btn:hover {
    background-color: #0056b3;
}

.section {
    margin-bottom: 30px;
}

.address-display-box {
    max-height: 120px;
    overflow-y: auto;
    border: 1px solid #ccc;
    padding: 15px;
    border-radius: 8px;
    background-color: #f8f8f8;
    margin-bottom: 15px;
}

.address-display-box p {
    margin: 0;
    line-height: 1.6;
}

.form-group {
    margin-bottom: 10px;
}

.green {
    color: green;
}
</style>
</head>
<body>

<%
   Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
   User currentUser = (User) session.getAttribute("user");
   Address address = (Address) request.getAttribute("address"); // you must set this in CheckoutServlet
   double total = 0;
%>

<div class="container">
    <h2>Checkout</h2>
    <hr/>

    <div class="section">
        <h3>Cart Items</h3>
        <table>
            <tr>
                <th>Book</th>
                <th>Title</th>
                <th>Qty</th>
                <th>Price</th>
                <th>Subtotal</th>
            </tr>
            <%
                if (cart != null && !cart.isEmpty()) {
                    for (CartItem item : cart.values()) {
                        double subtotal = item.getBook().getPrice() * item.getQuantity();
                        total += subtotal;
            %>
            <tr>
                <td><img src="<%= item.getBook().getImageUrl() %>" width="100" height="120" /></td>
                <td><%= item.getBook().getTitle() %></td>
                <td><%= item.getQuantity() %></td>
                <td><%= item.getBook().getPrice() %></td>
                <td><%= subtotal %></td>
            </tr>
            <% } } else { %>
            <tr><td colspan="5">No items in your cart.</td></tr>
            <% } %>
        </table>
    </div>

    <div class="section">
        <h3>Shipping Address</h3>

        <%
            if (address != null) {
        %>
            <div class="address-display-box">
                <p>
                    <strong><%= address.getName() %></strong><br/>
                    <%= address.getDoorNo() %>, <%= address.getAddressLine1() %>, <%= address.getAddressLine2() %><br/>
                    <%= address.getCity() %>, <%= address.getState() %> - <%= address.getPincode() %><br/>
                    <strong>Phone:</strong> <%= address.getPhoneNumber() %><br/>
                </p>
            </div>

            <form action="PlaceOrderServlet" method="post">
             <!--     <input type="hidden" name="addressOption" value="useSaved" /> -->
                <input type="hidden" name="useSavedAddress" value="true" />
                
                <input type="submit" value="Proceed to Payment" class="btn" />
            </form>
        <%
            } else {
        %>
            <p>No address found. Please enter your shipping address:</p>
            <form action="AddressServlet" method="post">
                <input type="hidden" name="redirectAfterSave" value="checkout" />
                <input type="hidden" name="user_email" value="<%= currentUser.getEmail() %>" />

                <div class="form-group">
                    <label>Full Name:</label>
                    <input type="text" name="name" value="<%= currentUser.getName() %>" required />
                </div>
                <div class="form-group">
                    <label>Door No:</label>
                    <input type="text" name="door_number" required />
                </div>
                <div class="form-group">
                    <label>Address Line 1:</label>
                    <input type="text" name="address_line1" required />
                </div>
                <div class="form-group">
                    <label>Address Line 2:</label>
                    <input type="text" name="address_line2" />
                </div>
                <div class="form-group">
                    <label>City:</label>
                    <input type="text" name="city" required />
                </div>
                <div class="form-group">
                    <label>State:</label>
                    <input type="text" name="state" required />
                </div>
                <div class="form-group">
                    <label>Pincode:</label>
                    <input type="text" name="pincode" required />
                </div>
                <div class="form-group">
                    <label>Country:</label>
                    <input type="text" name="country" value="India" required />
                </div>
                <div class="form-group">
                    <label>Phone Number:</label>
                    <input type="text" name="phone_number" required />
                </div>
                <div class="form-group">
                    <label>Nearby Location:</label>
                    <input type="text" name="nearby_location" />
                </div>
                <div class="form-group">
                    <label>Place Type:</label>
                    <input type="text" name="place_type" />
                </div>

                <input type="submit" value="Save Address and Proceed" class="btn" />
            </form>
        <%
            }
        %>
    </div>

    <div class="section">
        <h3>Payment Method</h3>
        <label><input type="radio" name="paymentMethod" value="COD" checked /> Cash on Delivery</label><br/>
        <label><input type="radio" name="paymentMethod" value="UPI" disabled /> UPI (Coming Soon)</label>
    </div>

    <div class="section">
        <p><strong>Estimated Delivery:</strong> Between 3-5 Days</p>
        <p class="green">100% secure payment | Easy returns within 7 days</p>
    </div>

    <div class="section">
        <h3>Total: <%= total %></h3>
    </div>
</div>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
    <p style="color: red;"><%= errorMessage %></p>
<%
    }
%>


</body>
</html>
