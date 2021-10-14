package com.aumaid.bochihhott.Models;

import java.io.Serializable;

public class CartItem_ implements Serializable {

    private String user_id;
    private String item_id;
    private int quantity;
    private FoodItem item;

    public CartItem_() {
    }

    public CartItem_(String user_id, String item_id, int quantity, FoodItem item) {
        this.user_id = user_id;
        this.item_id = item_id;
        this.quantity = quantity;
        this.item = item;
    }

    public FoodItem getItem() {
        return item;
    }

    public void setItem(FoodItem item) {
        this.item = item;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem_{" +
                "user_id='" + user_id + '\'' +
                ", item_id='" + item_id + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
