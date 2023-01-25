package com.example.foodare.model;

public class Post {
    public String username;
    public String restaurant;
    public String meal;
    public String rate;
    public String description;

    public Post(String username, String restaurant, String meal, String rate, String description) {
        this.username = username;
        this.restaurant = restaurant;
        this.meal = meal;
        this.rate = rate;
        this.description = description;
    }
}
