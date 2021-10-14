package com.aumaid.bochihhott.Models;

import java.io.Serializable;

public class Addresss implements Serializable {

    /**
     * Declaring variables
     */
    private String full_name;
    private String phone_number;
    private String address_id;
    private String address;
    private String landmark;
    private String city;
    private String pin_code;
    private boolean is_default;

    public Addresss(){

    }

    public Addresss(String full_name,String phone_number,String address, String landmark,  String city, String pin_code) {
        this.address = address;
        this.landmark = landmark;
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.city = city;
        this.pin_code = pin_code;
        this.is_default = false;
    }

    public boolean isIs_default() {
        return is_default;
    }

    public void setIs_default(boolean is_default) {
        this.is_default = is_default;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    @Override
    public String toString() {
        return "Addresss{" +
                "full_name='" + full_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", address_id='" + address_id + '\'' +
                ", address='" + address + '\'' +
                ", landmark='" + landmark + '\'' +
                ", city='" + city + '\'' +
                ", pin_code='" + pin_code + '\'' +
                '}';
    }
}
