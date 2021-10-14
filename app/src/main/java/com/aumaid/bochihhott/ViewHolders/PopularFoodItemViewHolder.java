package com.aumaid.bochihhott.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.FoodItemListener;
import com.aumaid.bochihhott.R;

public class PopularFoodItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mFoodImage;
    private TextView mFoodRestaurantName;
    private TextView mFoodRestaurantAddress;
    private TextView mFoodCategory;
    private TextView mFoodRating;
    private TextView mFoodPrice;
    private FoodItemListener foodItemListener;


    public PopularFoodItemViewHolder(@NonNull View itemView, FoodItemListener listener) {
        super(itemView);

        mFoodImage = itemView.findViewById(R.id.foodImage);
        mFoodRestaurantName = itemView.findViewById(R.id.foodItemRestaurantName);
        mFoodRestaurantAddress = itemView.findViewById(R.id.foodItemRestaurantAddress);
        mFoodCategory = itemView.findViewById(R.id.foodItemCategory);
        mFoodRating = itemView.findViewById(R.id.foodItemRating);
        mFoodPrice = itemView.findViewById(R.id.foodItemPrice);
        this.foodItemListener = listener;

        itemView.setOnClickListener(this);


    }

    public ImageView getmFoodImage() {
        return mFoodImage;
    }

    public TextView getmFoodRestaurantName() {
        return mFoodRestaurantName;
    }

    public TextView getmFoodRestaurantAddress() {
        return mFoodRestaurantAddress;
    }

    public TextView getmFoodCategory() {
        return mFoodCategory;
    }

    public TextView getmFoodRating() {
        return mFoodRating;
    }

    public TextView getmFoodPrice() {
        return mFoodPrice;
    }

    @Override
    public void onClick(View v) {

        foodItemListener.onFoodClicked(getAdapterPosition());
        //fragment.openProductDescriptionFragment(getAdapterPosition(),v.findViewById(R.id.foodImage));


    }
}
