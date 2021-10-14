package com.aumaid.bochihhott.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Home.HomeFragment;
import com.aumaid.bochihhott.Home.ProductDescriptionFragment;
import com.aumaid.bochihhott.Interfaces.FoodItemListener;
import com.aumaid.bochihhott.R;

public class FoodItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mFoodImage;
    private TextView mFoodItemName;
    private TextView mFoodRestaurantAddress;
   // private TextView mFoodCategory;
    private TextView mFoodRating;
    private TextView mFoodPrice;
    private FoodItemListener foodItemListener;


    public FoodItemViewHolder(@NonNull View itemView, FoodItemListener listener) {
        super(itemView);

        mFoodImage = itemView.findViewById(R.id.foodImage);
        mFoodItemName = itemView.findViewById(R.id.itemNameTv);
        mFoodRestaurantAddress = itemView.findViewById(R.id.restaurantAddressTv);
      //  mFoodCategory = itemView.findViewById(R.id.foodItemCategory);
        mFoodRating = itemView.findViewById(R.id.ratingTv);
        mFoodPrice = itemView.findViewById(R.id.priceTv);
        this.foodItemListener = listener;

        itemView.setOnClickListener(this);


    }

    public ImageView getmFoodImage() {
        return mFoodImage;
    }

    public TextView getmFoodItemName() {
        return mFoodItemName;
    }

    public TextView getmFoodRestaurantAddress() {
        return mFoodRestaurantAddress;
    }

   // public TextView getmFoodCategory() {
   //     return mFoodCategory;
  //  }

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
