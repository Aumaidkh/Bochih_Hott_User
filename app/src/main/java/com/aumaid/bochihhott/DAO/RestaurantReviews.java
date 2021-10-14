package com.aumaid.bochihhott.DAO;

public class RestaurantReviews {

    private String review_id;
    private String customer_id;
    private String restaurant_id;
    private String message;

    public RestaurantReviews() {
    }

    public RestaurantReviews(String review_id, String customer_id, String restaurant_id, String message) {
        this.review_id = review_id;
        this.customer_id = customer_id;
        this.restaurant_id = restaurant_id;
        this.message = message;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RestaurantReviews{" +
                "review_id='" + review_id + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", restaurant_id='" + restaurant_id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
