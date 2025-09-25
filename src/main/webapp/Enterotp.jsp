<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Enter OTP</title>
    <style>
        body {
            background-color: #f0f8ff;
            font-family: 'Segoe UI', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .otp-box {
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0,0,0,0.2);
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            font-size: 16px;
            border-radius: 8px;
            border: 1px solid #ccc;
        }
        button {
            margin-top: 15px;
            width: 100%;
            padding: 10px;
            background-color: #74ebd5;
            border: none;
            color: white;
            font-weight: bold;
            border-radius: 8px;
            cursor: pointer;
        }
        .error {
            color: red;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<div class="otp-box">
    <h2>Enter OTP</h2>
    <form action="VerifyOtpServlet" method="post">
        <input type="text" name="otp" placeholder="Enter 6-digit OTP" pattern="\\d{6}" required>
        <button type="submit">Verify OTP</button>
        <% if (request.getParameter("error") != null) { %>
            <div class="error">Invalid OTP or OTP Expired</div>
        <% } %>
    </form>
</div>

</body>
</html>
