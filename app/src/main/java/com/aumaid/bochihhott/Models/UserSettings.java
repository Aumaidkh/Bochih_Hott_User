package com.aumaid.bochihhott.Models;

import com.aumaid.bochihhott.DAO.User;
import com.aumaid.bochihhott.DAO.UserAccountSettings;

public class UserSettings {

    private User user;
    private UserAccountSettings userSettings;

    public UserSettings(){

    }

    public UserSettings(User user, UserAccountSettings userSettings) {
        this.user = user;
        this.userSettings = userSettings;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAccountSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserAccountSettings userSettings) {
        this.userSettings = userSettings;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "user=" + user +
                ", userSettings=" + userSettings +
                '}';
    }
}

