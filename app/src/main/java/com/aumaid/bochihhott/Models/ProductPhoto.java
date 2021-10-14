package com.aumaid.bochihhott.Models;

public class ProductPhoto {

    private String product_name;
    private String product_desc;
    private String product_price;
    private String product_offer;
    private String date;
    private String tags;
    private String user_id;

    public ProductPhoto(){

    }

    public ProductPhoto(String product_name, String product_desc, String product_price, String product_offer, String date, String tags, String user_id) {
        this.product_name = product_name;
        this.product_desc = product_desc;
        this.product_price = product_price;
        this.product_offer = product_offer;
        this.date = date;
        this.tags = tags;
        this.user_id = user_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_offer() {
        return product_offer;
    }

    public void setProduct_offer(String product_offer) {
        this.product_offer = product_offer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
