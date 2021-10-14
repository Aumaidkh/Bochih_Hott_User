package com.aumaid.bochihhott.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Order implements Serializable {

    private String placed_order_id;
    private String customer_name;
    private String contact_number;
    private String customer_id; //
    private String delivery_address;//
    private String delivery_address_id;
    private String estimated_delivery_time;
    private float price;//
    private String order_time;
    private String timestamp;
    private String status;//
    private ArrayList<FoodItem> items;//
    private ArrayList<String> res_ids;//
    private HashMap<String, String> map;//

    public Order() {
    }



    public Order(String customer_id, String delivery_address, String estimated_delivery_time, float price, String order_time, String timestamp, ArrayList<FoodItem> items) {
        this.customer_id = customer_id;
        this.delivery_address = delivery_address;
        this.estimated_delivery_time = estimated_delivery_time;
        this.price = price;
        this.order_time = order_time;
        this.timestamp = timestamp;
        this.items = items;
        this.status = "processing";
        this.map = new HashMap<>();

    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDelivery_address_id() {
        return delivery_address_id;
    }

    public ArrayList<String> getRes_ids() {
        return res_ids;
    }

    public void setRes_ids(ArrayList<String> res_ids) {
        this.res_ids = res_ids;
    }

    public void setDelivery_address_id(String delivery_address_id) {
        this.delivery_address_id = delivery_address_id;
    }

    public ArrayList<FoodItem> getItems() {
        return items;
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }

    public void setItems(ArrayList<FoodItem> items) {
        this.items = items;
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



    @Override
    public String toString() {
        return "PlacedOrderModel{" +
                "placed_order_id='" + placed_order_id + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", delivery_address='" + delivery_address + '\'' +
                ", estimated_delivery_time='" + estimated_delivery_time + '\'' +
                ", price=" + price +
                ", order_time='" + order_time + '\'' +
                ", delivery_address_id='" + delivery_address_id + '\'' +
                //", restaurant_id='" + restaurant_id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }


   /* public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
*/
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    
    

}
