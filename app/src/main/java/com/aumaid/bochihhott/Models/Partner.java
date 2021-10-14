package com.aumaid.bochihhott.Models;


import java.io.Serializable;

public class Partner implements Serializable {

    private String restaurant_id;
    private String menu;
    private String name;
    private String photo;
    private String address;
    private String email;
    private String password;
    private String city;
    private String messaging_token;
    private String phone_number;
    private Float ratings;

    public Float getRatings() {
        return ratings;
    }

    public void setRatings(Float ratings) {
        this.ratings = ratings;
    }

    public Partner() {
    }

    public Partner(String name, String address, String email, String password, String city, String phone_number) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.password = password;
        this.city = city;
        this.phone_number = phone_number;
        this.restaurant_id=null;
        this.photo=null;
        this.ratings=0.0f;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMessaging_token() {
        return messaging_token;
    }

    public void setMessaging_token(String messaging_token) {
        this.messaging_token = messaging_token;
    }

    //    public Partner(String restaurant_id, String name, String address, String email, String password, String city, String phone_number) {
//        this.restaurant_id = restaurant_id;
//        this.name = name;
//        this.address = address;
//        this.email = email;
//        this.password = password;
//        this.city = city;
//        this.phone_number = phone_number;
//    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        return "Partner{" +
                "restaurant_id='" + restaurant_id + '\'' +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                ", messaging_token='" + messaging_token + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", ratings=" + ratings +
                '}';
    }
}
