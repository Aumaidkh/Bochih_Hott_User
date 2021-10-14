package com.aumaid.bochihhott.Models;

public class Category {

    private String categoryName;
    private  int categoryIcon;
    private boolean isSelected;



    public Category(String categoryName, int categoryIcon) {
        this.categoryName = categoryName;
        this.categoryIcon = categoryIcon;
        isSelected = false;

    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(int categoryIcon) {
        this.categoryIcon = categoryIcon;
    }
}
