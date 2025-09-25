<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    .contact-container {
        max-width: 600px;
        margin: 50px auto;
        padding: 30px;
        background-color: #f0f4f8;
        border-radius: 16px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
        font-family: 'Segoe UI', sans-serif;
    }

    .contact-container h2 {
        text-align: center;
        margin-bottom: 20px;
    }

    .contact-container label {
        display: block;
        margin-bottom: 8px;
        font-weight: bold;
    }

    .contact-container input,
    .contact-container textarea {
        width: 100%;
        padding: 10px;
        margin-bottom: 20px;
        border-radius: 8px;
        border: 1px solid #ccc;
    }

    .contact-container button {
        display: block;
        width: 100%;
        padding: 12px;
        background-color: #007bff;
        color: white;
        font-size: 16px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .contact-container button:hover {
        background-color: #0056b3;
    }

    .success-message {
        color: green;
        font-weight: bold;
        text-align: center;
        margin-top: 20px;
    }

    .profile-button {
        display: block;
        margin: 20px auto 0;
        padding: 12px 24px;
        background-color: #28a745;
        color: white;
        font-size: 16px;
        border: none;
        border-radius: 8px;
        text-align: center;
        text-decoration: none;
        transition: background-color 0.3s ease;
    }

    .profile-button:hover {
        background-color: #1e7e34;
    }
</style>

<div class="contact-container">
    <h2>📞 Contact Us</h2>
    <form action="ContactServlet" method="post">
        <label for="name">Your Name:</label>
        <input type="text" id="name" name="name" required>

        <label for="email">Your Email:</label>
        <input type="email" id="email" name="email" required>

        <label for="message">Your Message:</label>
        <textarea id="message" name="message" rows="5" required></textarea>

        <button type="submit">Send Message</button>
    </form>
<%
    String contactMessage = (String) request.getAttribute("contactMessage");
    Boolean showProfileButton = (Boolean) request.getAttribute("showProfileButton");
    if (contactMessage != null) {
%>
    <p style="color: green; font-weight: bold; text-align: center;"><%= contactMessage %></p>

    <% if (showProfileButton != null && showProfileButton) { %>
        <div style="text-align: center; margin-top: 20px;">
            <form action="Profile.jsp" method="get">
                <button type="submit" style="
                    padding: 10px 20px;
                    background-color: #28a745;
                    color: white;
                    border: none;
                    border-radius: 8px;
                    font-size: 16px;
                    cursor: pointer;">
                    Go to Profile
                </button>
            </form>
        </div>
    <% } %>
<%
    }
%>
</div>