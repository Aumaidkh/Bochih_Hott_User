/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.ReviewsAndRatings.Models;

public class ReviewModel {

    private String profile_pic; //
    private String review_id;
    private String res_id; //
    private String timestamp; //
    private String body; //
    private float ratings;
    private String username; //

    public ReviewModel(){

    }

    public ReviewModel(String profile_pic, String res_id, String timestamp, String body, String username) {
        this.profile_pic = profile_pic;
        this.res_id = res_id;
        this.timestamp = timestamp;
        this.body = body;
        this.username = username;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ReviewModel{" +
                "profile_pic='" + profile_pic + '\'' +
                ", review_id='" + review_id + '\'' +
                ", res_id='" + res_id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", body='" + body + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
