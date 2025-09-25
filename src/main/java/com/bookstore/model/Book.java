package com.bookstore.model;


public class Book {
    private int id;
    private String title;
    private String author;
    private double price;
    private String imageUrl;  // added field
    
    private String description;
    private String authorInfo;

    public Book() { }

    public Book(int id, String title, String author, double price, String imageUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorInfo() {
        return authorInfo;
    }
    public void setAuthorInfo(String authorInfo) {
        this.authorInfo = authorInfo;
    }

    @Override
    public String toString() {
        return "Book{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", price=" + price +
               ", imageUrl='" + imageUrl + '\'' +
               '}';
    }
}
