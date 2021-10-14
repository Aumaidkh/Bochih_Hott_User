package com.aumaid.bochihhott.DAO;

public class CustomersCart {

    private String cart_id;
    private String food_item_id;

    public CustomersCart() {
    }

    public CustomersCart(String cart_id, String food_item_id) {
        this.cart_id = cart_id;
        this.food_item_id = food_item_id;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getFood_item_id() {
        return food_item_id;
    }

    public void setFood_item_id(String food_item_id) {
        this.food_item_id = food_item_id;
    }

    @Override
    public String toString() {
        return "CustomersCart{" +
                "cart_id='" + cart_id + '\'' +
                ", food_item_id='" + food_item_id + '\'' +
                '}';
    }
}
