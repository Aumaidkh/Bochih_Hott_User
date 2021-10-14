package com.aumaid.bochihhott.DAO;

public class PlacedOrder {

    private String placed_order_id;
    private String customer_id;
    private String delivery_address;
    private float discount;
    private String estimated_delivery_time;
    private float price;
    private String order_time;
    private float final_price;
    private String status;
    private String restaurant_id;
    private String timestamp;

    public PlacedOrder() {
    }

    public PlacedOrder(String customer_id, String delivery_address, float discount, String estimated_delivery_time, float price, String order_time, float final_price, String restaurant_id, String timestamp) {
        this.customer_id = customer_id;
        this.delivery_address = delivery_address;
        this.discount = discount;
        this.estimated_delivery_time = estimated_delivery_time;
        this.price = price;
        this.order_time = order_time;
        this.final_price = final_price;
        this.restaurant_id = restaurant_id;
        this.timestamp = timestamp;
        this.status = "processing";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlaced_order_id() {
        return placed_order_id;
    }

    public void setPlaced_order_id(String placed_order_id) {
        this.placed_order_id = placed_order_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getEstimated_delivery_time() {
        return estimated_delivery_time;
    }

    public void setEstimated_delivery_time(String estimated_delivery_time) {
        this.estimated_delivery_time = estimated_delivery_time;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public float getFinal_price() {
        return final_price;
    }

    public void setFinal_price(float final_price) {
        this.final_price = final_price;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PlacedOrderModel{" +
                "placed_order_id='" + placed_order_id + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", delivery_address='" + delivery_address + '\'' +
                ", discount=" + discount +
                ", estimated_delivery_time='" + estimated_delivery_time + '\'' +
                ", price=" + price +
                ", order_time='" + order_time + '\'' +
                ", final_price=" + final_price +
                ", status='" + status + '\'' +
                ", restaurant_id='" + restaurant_id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
