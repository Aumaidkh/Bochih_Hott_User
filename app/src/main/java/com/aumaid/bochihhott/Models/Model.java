package com.aumaid.bochihhott.Models;

public class Model {

    private String imageUrl;

    public Model() {
    }

    public Model(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Model{" +
                "imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
