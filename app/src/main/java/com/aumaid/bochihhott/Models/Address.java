package com.aumaid.bochihhott.Models;

public class Address {

    /**
     * Declaring variables
     */
    private String address_id;
    private String address_line1;
    private String landmark;
    private String village;
    private String city;
    private String pin_code;

    public Address(){

    }

    public Address(String address_line1, String landmark, String village, String city, String pin_code) {
        this.address_line1 = address_line1;
        this.landmark = landmark;
        this.village = village;
        this.city = city;
        this.pin_code = pin_code;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
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
        return "Address{" +
                "address_line1='" + address_line1 + '\'' +
                ", landmark='" + landmark + '\'' +
                ", village='" + village + '\'' +
                ", city='" + city + '\'' +
                ", pin_code='" + pin_code + '\'' +
                '}';
    }
}
