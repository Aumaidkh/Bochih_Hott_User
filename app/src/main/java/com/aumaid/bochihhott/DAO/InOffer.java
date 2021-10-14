package com.aumaid.bochihhott.DAO;

public class InOffer {

    private String in_offer_id;
    private String menu_id;
    private String offer_id;

    public InOffer() {
    }

    public InOffer(String in_offer_id, String menu_id, String offer_id) {
        this.in_offer_id = in_offer_id;
        this.menu_id = menu_id;
        this.offer_id = offer_id;
    }

    public String getIn_offer_id() {
        return in_offer_id;
    }

    public void setIn_offer_id(String in_offer_id) {
        this.in_offer_id = in_offer_id;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    @Override
    public String toString() {
        return "InOffer{" +
                "in_offer_id='" + in_offer_id + '\'' +
                ", menu_id='" + menu_id + '\'' +
                ", offer_id='" + offer_id + '\'' +
                '}';
    }
}
