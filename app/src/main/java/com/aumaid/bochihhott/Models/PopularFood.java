package com.aumaid.bochihhott.Models;

public class PopularFood {

    private String mFoodName;
    private String mFoodDesc;
    private String mPrice;
    private int mRating;
    private int mImage;

    public PopularFood(String mFoodName, String mFoodDesc, String mPrice, int mRating, int mImage) {
        this.mFoodName = mFoodName;
        this.mFoodDesc = mFoodDesc;
        this.mPrice = mPrice;
        this.mRating = mRating;
        this.mImage = mImage;
    }

    public String getmFoodName() {
        return mFoodName;
    }

    public void setmFoodName(String mFoodName) {
        this.mFoodName = mFoodName;
    }

    public String getmFoodDesc() {
        return mFoodDesc;
    }

    public void setmFoodDesc(String mFoodDesc) {
        this.mFoodDesc = mFoodDesc;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public int getmRating() {
        return mRating;
    }

    public void setmRating(int mRating) {
        this.mRating = mRating;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }
}
