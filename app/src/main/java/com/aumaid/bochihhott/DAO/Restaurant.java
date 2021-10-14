package com.aumaid.bochihhott.DAO;

public class Restaurant {

    private String restaurant_id;
    private String restaurant_name;
    private String restaurant_address;
    private String restaurant_photo;
    private String city_id;
    private float ratings;
    private String review_id;

    public Restaurant() {
    }

    public Restaurant(String restaurant_id, String restaurant_name, String restaurant_address, String restaurant_photo, String city_id, float ratings, String review_id) {
        this.restaurant_id = restaurant_id;
        this.restaurant_name = restaurant_name;
        this.restaurant_address = restaurant_address;
        this.restaurant_photo = restaurant_photo;
        this.city_id = city_id;
        this.ratings = ratings;
        this.review_id = review_id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
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

    public String getRestaurant_photo() {
        return restaurant_photo;
    }

    public void setRestaurant_photo(String restaurant_photo) {
        this.restaurant_photo = restaurant_photo;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurant_id='" + restaurant_id + '\'' +
                ", restaurant_name='" + restaurant_name + '\'' +
                ", restaurant_address='" + restaurant_address + '\'' +
                ", restaurant_photo='" + restaurant_photo + '\'' +
                ", city_id='" + city_id + '\'' +
                ", ratings=" + ratings +
                ", review_id='" + review_id + '\'' +
                '}';
    }
}
