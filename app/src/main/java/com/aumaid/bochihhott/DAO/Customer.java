package com.aumaid.bochihhott.DAO;

public class Customer {

    private String address;
    private String city;
    private String email;
    private String name;
    private String password;
    private String phone_number;
    private String profile_pic;
    private String user_id;

    public Customer() {

    }

    public Customer(String address, String city, String email, String name, String password, String phone_number, String profile_pic, String user_id) {
        this.address = address;
        this.city = city;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone_number = phone_number;
        this.profile_pic = profile_pic;
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", profile_pic='" + profile_pic + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
