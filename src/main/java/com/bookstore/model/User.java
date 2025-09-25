package com.bookstore.model;

import java.io.Serializable; // Import Serializable

public class User implements Serializable { // Implement Serializable
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes

    private int id; // IMPORTANT
    private String name;
    private String email;
    private String password; // Consider not storing password in User object in session for security
    private String mobile;

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}