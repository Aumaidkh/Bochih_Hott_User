package com.aumaid.bochihhott.DAO;

public class Offer {

    private String offer_id;
    private String date_active_from;
    private String date_active_to;
    private float offer_price;
    private String time_active_from;
    private String time_active_to;

    public Offer() {

    }

    public Offer(String offer_id, String date_active_from, String date_active_to, float offer_price, String time_active_from, String time_active_to) {
        this.offer_id = offer_id;
        this.date_active_from = date_active_from;
        this.date_active_to = date_active_to;
        this.offer_price = offer_price;
        this.time_active_from = time_active_from;
        this.time_active_to = time_active_to;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getDate_active_from() {
        return date_active_from;
    }

    public void setDate_active_from(String date_active_from) {
        this.date_active_from = date_active_from;
    }

    public String getDate_active_to() {
        return date_active_to;
    }

    public void setDate_active_to(String date_active_to) {
        this.date_active_to = date_active_to;
    }

    public float getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(float offer_price) {
        this.offer_price = offer_price;
    }

    public String getTime_active_from() {
        return time_active_from;
    }

    public void setTime_active_from(String time_active_from) {
        this.time_active_from = time_active_from;
    }

    public String getTime_active_to() {
        return time_active_to;
    }

    public void setTime_active_to(String time_active_to) {
        this.time_active_to = time_active_to;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "offer_id='" + offer_id + '\'' +
                ", date_active_from='" + date_active_from + '\'' +
                ", date_active_to='" + date_active_to + '\'' +
                ", offer_price=" + offer_price +
                ", time_active_from='" + time_active_from + '\'' +
                ", time_active_to='" + time_active_to + '\'' +
                '}';
    }
}
