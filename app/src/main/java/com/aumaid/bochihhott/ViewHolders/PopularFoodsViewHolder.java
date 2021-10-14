package com.aumaid.bochihhott.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.PopularFoodsListener;
import com.aumaid.bochihhott.R;

public class PopularFoodsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mPFImage;
    private TextView mPFName;
    private TextView mPFDesc;
    private TextView mPFPrice;
    private RatingBar mPFRating;

    private PopularFoodsListener popularFoodsListener;


    public PopularFoodsViewHolder(@NonNull View itemView, PopularFoodsListener popularFoodsListener) {
        super(itemView);

        mPFImage = itemView.findViewById(R.id.popularFoodItemImage);
        mPFName = itemView.findViewById(R.id.popularFoodItemName);
        mPFDesc = itemView.findViewById(R.id.popularFoodItemDesc);
        mPFPrice = itemView.findViewById(R.id.popularFoodItemPrice);
        mPFRating = itemView.findViewById(R.id.popularFoodItemRating);

        this.popularFoodsListener = popularFoodsListener;

        itemView.setOnClickListener(this);

    }

    public ImageView getmPFImage() {
        return mPFImage;
    }

    public void setmPFImage(ImageView mPFImage) {
        this.mPFImage = mPFImage;
    }

    public TextView getmPFName() {
        return mPFName;
    }

    public void setmPFName(TextView mPFName) {
        this.mPFName = mPFName;
    }

    public TextView getmPFDesc() {
        return mPFDesc;
    }

    public void setmPFDesc(TextView mPFDesc) {
        this.mPFDesc = mPFDesc;
    }

    public TextView getmPFPrice() {
        return mPFPrice;
    }

    public void setmPFPrice(TextView mPFPrice) {
        this.mPFPrice = mPFPrice;
    }

    public RatingBar getmPFRating() {
        return mPFRating;
    }

    public void setmPFRating(RatingBar mPFRating) {
        this.mPFRating = mPFRating;
    }

    @Override
    public void onClick(View v) {
        popularFoodsListener.onPopularFoodClicked(getAdapterPosition());
    }
}
