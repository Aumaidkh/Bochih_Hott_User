package com.aumaid.bochihhott.DAO;

public class UserAccountSettings {

    private String full_name;
    private String profile_photo;
    private long phone_number;


    public UserAccountSettings() {

    }

    public UserAccountSettings(String full_name, String profile_photo, long phone_number) {
        this.full_name = full_name;
        this.profile_photo = profile_photo;
        this.phone_number = phone_number;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "UserAccountSettings{" +
                "full_name='" + full_name + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                ", phone_number=" + phone_number +
                '}';
    }
}
