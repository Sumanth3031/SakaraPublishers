package com.bookstore.servlet;

import java.io.IOException;
import java.sql.*;
import java.time.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/VerifyOtpServlet")
public class VerifyOtpServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String inputOtp = request.getParameter("otp");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("otpMobile") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        String mobile = (String) session.getAttribute("otpMobile");

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");

            // ✅ Get the latest OTP for this mobile
            String sql = "SELECT otp, created_at FROM otp_table WHERE mobile=? ORDER BY created_at DESC LIMIT 1";
            pst = conn.prepareStatement(sql);
            pst.setString(1, mobile);
            rs = pst.executeQuery();

            if (rs.next()) {
                int dbOtp = rs.getInt("otp");
                Timestamp createdAt = rs.getTimestamp("created_at");

                // ✅ Check if OTP is expired
                LocalDateTime createdTime = createdAt.toLocalDateTime();
                LocalDateTime now = LocalDateTime.now();
                Duration duration = Duration.between(createdTime, now);

                if (String.valueOf(dbOtp).equals(inputOtp) && duration.toMinutes() <= 2) {
                    // ✅ OTP is valid and not expired

                    // 💖 Get user details for session
                    String userSql = "SELECT name, email FROM users WHERE mobile=?";
                    pst = conn.prepareStatement(userSql);
                    pst.setString(1, mobile);
                    rs = pst.executeQuery();

                    if (rs.next()) {
                        String name = rs.getString("name");
                        String email = rs.getString("email");

                        session.setAttribute("userName", name);
                        session.setAttribute("userEmail", email);

                        // ✅ Go to home page
                        response.sendRedirect("HomeServlet");
                        return;
                    }
                }
            }

            // ❌ OTP invalid or expired
            response.sendRedirect("Enterotp.jsp?error=1");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
