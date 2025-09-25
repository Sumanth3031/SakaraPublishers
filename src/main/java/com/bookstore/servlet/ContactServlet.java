package com.bookstore.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;
//
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        try {
            // Save to DB
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");

            String sql = "INSERT INTO contact_messages (name, email, message) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, message);
            ps.executeUpdate();

            ps.close();
            conn.close();

//            // Email to admin
//            final String fromEmail = "vaddesumanth31@gmail.com";      // your Gmail
//            final String appPassword = "fefshiutyczmwyjd";      // your Gmail App Password
//            final String toEmail = "sakarapublishers@gmail.com"; // admin email
//
//            Properties props = new Properties();
//            props.put("mail.smtp.host", "smtp.gmail.com");
//            props.put("mail.smtp.port", "587");
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.starttls.enable", "true");
//
//            Session mailSession = Session.getInstance(props, new Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(fromEmail, appPassword);
//                }
//            });
//
//            Message msg = new MimeMessage(mailSession);
//            msg.setFrom(new InternetAddress(fromEmail, "Sakara Contact"));
//            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
//            msg.setSubject("📩 New Contact Message from " + name);
//            msg.setText("📧 Email: " + email + "\n\n📝 Message:\n" + message);
//
//            Transport.send(msg);

            request.setAttribute("contactMessage", "Your message was sent successfully!");
            request.setAttribute("showProfileButton", true);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("contactMessage", "Something went wrong. Please try again.");
        }

        RequestDispatcher rd = request.getRequestDispatcher("Contact.jsp");
        rd.forward(request, response);
    }
}



//
//package com.bookstore.servlet;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/ContactServlet")
//public class ContactServlet extends HttpServlet {
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String name = request.getParameter("name");
//        String email = request.getParameter("email");
//        String message = request.getParameter("message");
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection conn = DriverManager.getConnection(
//                "jdbc:mysql://localhost:3306/onlinebookstore", "root", "sumanth$3031");
//
//            String sql = "INSERT INTO contact_messages (name, email, message) VALUES (?, ?, ?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, name);
//            ps.setString(2, email);
//            ps.setString(3, message);
//            ps.executeUpdate();
//
//            ps.close();
//            conn.close();
//
//            // ✅ Set a flag to show "Go to Profile" in JSP
//            request.setAttribute("contactMessage", "Your message was sent successfully!");
//            request.setAttribute("showProfileButton", true);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute("contactMessage", "Something went wrong. Please try again.");
//        }
//
//        RequestDispatcher rd = request.getRequestDispatcher("Contact.jsp");
//        rd.forward(request, response);
//    }
//}

