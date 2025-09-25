package com.bookstore.servlet;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/SendOtpServlet")
public class SendOtpServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mobile = request.getParameter("mobile");
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");

            // 1. Check if mobile exists
            String checkSql = "SELECT * FROM users WHERE mobile=?";
            pst = conn.prepareStatement(checkSql);
            pst.setString(1, mobile);
            rs = pst.executeQuery();

            if (rs.next()) {
                // 2. Generate OTP
                int otp = new Random().nextInt(900000) + 100000;

                // 3. Save OTP in DB
                String insertOtpSql = "INSERT INTO otp_table(mobile, otp, created_at) VALUES (?, ?, NOW())";
                pst = conn.prepareStatement(insertOtpSql);
                pst.setString(1, mobile);
                pst.setInt(2, otp);
                pst.executeUpdate();

                // 4. Send real OTP via SMS
                SmsSender.sendOtp(mobile, otp);

                // 5. Store mobile in session to validate later
                HttpSession session = request.getSession();
                session.setAttribute("otpMobile", mobile);

                // 6. Redirect to OTP verification page
             //   response.sendRedirect("Enterotp.jsp");
                response.sendRedirect("Enterotp.jsp");

               // response.sendRedirect(request.getContextPath() + "/Enterotp.jsp");

            } else {
                response.sendRedirect("Login.jsp?mobileNotFound=1");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
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


    // ✅ Inner SMS Sender Class (Fast2SMS Integration)
//    static class SmsSender {
//        public static void sendOtp(String mobile, int otp) throws IOException {
//            
//        //	String apiKey = "YOUR_FAST2SMS_API_KEY"; // 🔐 Replace with your real Fast2SMS key
//        	String apiKey = "wHgcrkUmX3SWKbFzls49G5MdfxiqTy80h7ZjtAIN2eDuL1BYnpbU5NkW6IjeVrwYCiLuZh7PFfls2zJM"; // 🔐 Replace with your real Fast2SMS key
//            
//            
//            String message = "Your OTP for Sakara Bookstore login is: " + otp;
//
//            String url = "https://www.fast2sms.com/dev/bulkV2";
//            String data = "authorization=" + apiKey +
//                    "&sender_id=TXTIND" +
//                    "&message=" + URLEncoder.encode(message, "UTF-8") +
//                    "&language=english" +
//                    "&route=v3" +
//                    "&numbers=" + mobile;
//
//            URL obj = new URL(url);
//            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//            con.setRequestMethod("POST");
//            con.setDoOutput(true);
//            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//
//            DataOutputStream out = new DataOutputStream(con.getOutputStream());
//            out.writeBytes(data);
//            out.flush();
//            out.close();
//
//            int responseCode = con.getResponseCode();
//            if (responseCode == 200) {
//                System.out.println("✅ OTP sent successfully to " + mobile);
//            } else {
//                System.out.println("❌ Failed to send OTP. Response Code: " + responseCode);
//            }
//        }
//    }
//}

    static class SmsSender {
        public static void sendOtp(String mobile, int otp) throws IOException {

            String apiKey = "5kDLJuSxELuIqJ0ETlYWBbJUu7w8uAlgjV4xK35dikr0cI292tpqOxggxWNX";

            String message = "Your OTP for Sakara Bookstore login is: " + otp;

            String url = "https://www.fast2sms.com/dev/bulkV2";
            String data = "sender_id=TXTIND" +
                          "&message=" + URLEncoder.encode(message, "UTF-8") +
                          "&language=english" +
                          "&route=v3" +
                          "&numbers=" + mobile;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            
            // ✅ Send API key in header, not in form data
            con.setRequestProperty("authorization", apiKey);

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(data);
            out.flush();
            out.close();

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                System.out.println("✅ OTP sent successfully to " + mobile);
            } else {
                System.out.println("❌ Failed to send OTP. Response Code: " + responseCode);

                // Optional: print response for debugging
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println("Response: " + response.toString());
            }
        }
    }
}

    
    
    