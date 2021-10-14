/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.Models;

import android.view.Menu;

public class MenuItem {

    private String category_name;
    private String category_icon;
    private boolean isSelected=false;


    public MenuItem(){

    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public MenuItem(String category_name, String category_icon) {
        this.category_name = category_name;
        this.category_icon = category_icon;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_icon() {
        return category_icon;
    }

    public void setCategory_icon(String category_icon) {
        this.category_icon = category_icon;
    }
}
