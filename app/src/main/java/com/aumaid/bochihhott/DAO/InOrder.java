package com.aumaid.bochihhott.DAO;

public class InOrder {

    private String in_order_id;
    private long item_price;
    private String food_item_id;
    private String offer_id;
    private String placed_order_id;
    private int quantity;
    private int total_price;

    public InOrder() {
    }

    public InOrder(String in_order_id, long item_price, String food_item_id, String offer_id, String placed_order_id, int quantity, int total_price) {
        this.in_order_id = in_order_id;
        this.item_price = item_price;
        this.food_item_id = food_item_id;
        this.offer_id = offer_id;
        this.placed_order_id = placed_order_id;
        this.quantity = quantity;
        this.total_price = total_price;
    }

    public String getIn_order_id() {
        return in_order_id;
    }

    public void setIn_order_id(String in_order_id) {
        this.in_order_id = in_order_id;
    }

    public long getItem_price() {
        return item_price;
    }

    public void setItem_price(long item_price) {
        this.item_price = item_price;
    }

    public String getFood_item_id() {
        return food_item_id;
    }

    public void setFood_item_id(String food_item_id) {
        this.food_item_id = food_item_id;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getPlaced_order_id() {
        return placed_order_id;
    }

    public void setPlaced_order_id(String placed_order_id) {
        this.placed_order_id = placed_order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    @Override
    public String toString() {
        return "InOrder{" +
                "in_order_id='" + in_order_id + '\'' +
                ", item_price=" + item_price +
                ", food_item_id='" + food_item_id + '\'' +
                ", offer_id='" + offer_id + '\'' +
                ", placed_order_id='" + placed_order_id + '\'' +
                ", quantity=" + quantity +
                ", total_price=" + total_price +
                '}';
    }
}
