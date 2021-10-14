package com.aumaid.bochihhott.DAO;

public class City {

    private String city_id;
    private String city_name;
    private String pin_code;

    public City() {
    }

    public City(String city_id, String city_name, String pin_code) {
        this.city_id = city_id;
        this.city_name = city_name;
        this.pin_code = pin_code;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    @Override
    public String toString() {
        return "City{" +
                "city_id='" + city_id + '\'' +
                ", city_name='" + city_name + '\'' +
                ", pin_code='" + pin_code + '\'' +
                '}';
    }
}
