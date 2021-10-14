package com.aumaid.bochihhott.Models;

public class Restaurant {

    private String restaurantId;
    private String username;
    private String email;
    private String password;


    public Restaurant(){

    }

    public Restaurant(String restaurantId, String username, String email, String password) {
        this.restaurantId = restaurantId;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId='" + restaurantId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
