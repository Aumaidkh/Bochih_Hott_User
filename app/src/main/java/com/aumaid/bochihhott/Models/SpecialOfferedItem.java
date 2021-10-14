package com.aumaid.bochihhott.Models;

public class SpecialOfferedItem {

    private int mSIImage;
    private String mSIOffer;

    public SpecialOfferedItem(int mSIImage, String mSIOffer) {
        this.mSIImage = mSIImage;
        this.mSIOffer = mSIOffer;
    }

    public int getmSIImage() {
        return mSIImage;
    }

    public void setmSIImage(int mSIImage) {
        this.mSIImage = mSIImage;
    }

    public String getmSIOffer() {
        return mSIOffer;
    }

    public void setmSIOffer(String mSIOffer) {
        this.mSIOffer = mSIOffer;
    }
}
