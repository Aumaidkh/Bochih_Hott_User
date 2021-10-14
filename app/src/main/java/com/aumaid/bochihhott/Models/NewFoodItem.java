package com.aumaid.bochihhott.Models;

import java.io.Serializable;

public class NewFoodItem implements Serializable {

    private String item_id;
    private boolean active;
    private String category_id;
    private String description;
    private String item_name;
    private String quantity;
    private String item_photo;
    private float item_ratings;
    private String price;
    private String restaurant_name;
    private String restaurant_address;
    private String restaurant_id;


    public NewFoodItem() {

    }

    public NewFoodItem(String item_id, boolean active, String category_id, String description, String item_name, String item_photo, float item_ratings, String price, String restaurant_name, String restaurant_address, String restaurant_id) {
        this.item_id = item_id;
        this.active = active;
        this.category_id = category_id;
        this.description = description;
        this.item_name = item_name;
        this.item_photo = item_photo;
        this.item_ratings = item_ratings;
        this.price = price;
        this.restaurant_name = restaurant_name;
        this.restaurant_address = restaurant_address;
        this.restaurant_id = restaurant_id;
    }



    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_address() {
        return restaurant_address;
    }

    public void setRestaurant_address(String restaurant_address) {
        this.restaurant_address = restaurant_address;
    }

    public float getItem_ratings() {
        return item_ratings;
    }

    public void setItem_ratings(float item_ratings) {
        this.item_ratings = item_ratings;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_photo() {
        return item_photo;
    }

    public void setItem_photo(String item_photo) {
        this.item_photo = item_photo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "item_id='" + item_id + '\'' +
                ", active=" + active +
                ", category_id='" + category_id + '\'' +
                ", description='" + description + '\'' +
                ", item_name='" + item_name + '\'' +
                ", item_photo='" + item_photo + '\'' +
                ", item_ratings=" + item_ratings +
                ", price='" + price + '\'' +
                ", restaurant_name='" + restaurant_name + '\'' +
                ", restaurant_address='" + restaurant_address + '\'' +
                ", restaurant_id='" + restaurant_id + '\'' +
                '}';
    }
}