<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page session="true" %>

<%
    String userEmail = (String) session.getAttribute("userEmail");

    if (userEmail == null) {
        response.sendRedirect("Login.jsp");
        return;
    }

    // DB credentials
    String name = null;
    String email = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");

        String sql = "SELECT name, email FROM users WHERE email = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, userEmail);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            name = rs.getString("name");
            email = rs.getString("email");
        } else {
            response.sendRedirect("Login.jsp");
            return;
        }

        rs.close();
        ps.close();
        conn.close();
    } catch (Exception e) {
        out.println("<h3 style='color:red;'>Error fetching user info: " + e.getMessage() + "</h3>");
    }
%>

<style>
    .profile-box {
        background-color: #f5f7fa;
        border-radius: 20px;
        padding: 30px;
        margin: 40px auto;
        box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        max-width: 600px;
        text-align: center;
        font-family: Arial, sans-serif;
    }

    .profile-info h2 {
        margin: 0 0 10px;
        font-size: 26px;
        color: #333;
    }

    .profile-info p {
        margin: 5px 0;
        color: #555;
        font-size: 16px;
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

    .profile-buttons a.logout-btn {
        background-color: #dc3545;
    }

    .profile-buttons a:hover {
        background-color: #0056b3;
    }

    .profile-buttons a.logout-btn:hover {
        background-color: #c82333;
        .contact-btn {
        background-color: #17a2b8;
}

.contact-btn:hover {
    background-color: #117a8b;
}
        
    }
</style>
<div class="profile-box">
    <div class="profile-info">
        <h2><%= name %></h2>
        <p><strong>Email:</strong> <%= email %></p>

        <div class="profile-buttons">
        
         <!--     <a href="EditProfile.jsp">✏️ Edit Profile</a>  -->
            
           <!--   <a href="Address.jsp">🏠 Your Address</a> -->
           
            <a href="AddressServlet"> 🏠 Your Address</a>
            
           <!--   <a href="ContactUs.jsp">📞 Contact Us</a> -->
           
            <a href="Contact.jsp" class="contact-btn">📞 Contact Us</a>
            
            <a href="Wishlist.jsp" class="Wishlist-btn"> 💖 Wishlist</a>
            
            <a href="MyOrdersServlet" class="Myorders-btn">📦 My Orders</a>
            
            
            
            <a href="HomeServlet" class="cart-btn home-btn">🏠 Go To Home</a>
            
            <a href="LogoutServlet" class="logout-btn">🚪 Logout</a>
            
            
        </div>
    </div>
</div>
