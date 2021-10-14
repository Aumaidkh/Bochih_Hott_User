package com.aumaid.bochihhott.Models;

public class CartItem {

    private String itemName;
    private int quantity;
    private String restaurantName;
    private int price;

    public CartItem(String itemName, int quantity, String restaurantName, int price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.restaurantName = restaurantName;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
