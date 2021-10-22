package com.aumaid.bochihhott.DAO;

import java.io.Serializable;

public class User implements Serializable {

    private String profile_photo;
    private String phone_number;
    private String email;
    private String username;
    private String password;
    private String description;
    private String isPartner;

    public User(){

    }

    public User(String phone_number, String email, String username, String password,String isPartner) {
        this.phone_number = phone_number;
        this.email = email;
        this.username = username;
        this.password = password;
        this.isPartner = isPartner;
    }

    @Override
    public String toString() {
        return "User{" +
                "profile_photo='" + profile_photo + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", isPartner='" + isPartner + '\'' +
                '}';
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(String isPartner) {
        this.isPartner = isPartner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
