package com.aumaid.bochihhott.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.RestaurantListener;
import com.aumaid.bochihhott.R;

public class FinalNearByRestaurantsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mRestaurantImage; //
    private TextView mRestaurantName;//
    private TextView mRestaurantAddress;
   // private TextView mFoodCategory;
    private TextView mRestaurantRating;
    private TextView mDistance;
    private TextView mTime;
    private RestaurantListener restaurantListener;


    public FinalNearByRestaurantsViewHolder(@NonNull View itemView, RestaurantListener restaurantListener) {
        super(itemView);

        mRestaurantImage = itemView.findViewById(R.id.restaurantPhoto);
        mRestaurantName = itemView.findViewById(R.id.restaurantNameTv);
        mRestaurantAddress = itemView.findViewById(R.id.restaurantAddressTv);
      //  mFoodCategory = itemView.findViewById(R.id.foodItemCategory);
        mRestaurantRating = itemView.findViewById(R.id.ratingTv);
        mDistance = itemView.findViewById(R.id.distanceTv);
        mTime = itemView.findViewById(R.id.deliveryTimeTv);
        this.restaurantListener = restaurantListener;

        itemView.setOnClickListener(this);


    }

    public TextView getmTime() {
        return mTime;
    }

    public void setmTime(TextView mTime) {
        this.mTime = mTime;
    }

    public ImageView getmRestaurantImage() {
        return mRestaurantImage;
    }

    public TextView getmRestaurantName() {
        return mRestaurantName;
    }

    public TextView getmRestaurantAddress() {
        return mRestaurantAddress;
    }

   // public TextView getmFoodCategory() {
   //     return mFoodCategory;
  //  }

    public TextView getmRestaurantRating() {
        return mRestaurantRating;
    }

    public TextView getmDistance() {
        return mDistance;
    }

    @Override
    public void onClick(View v) {

        restaurantListener.onRestaurantClicked(getAdapterPosition());
        //fragment.openProductDescriptionFragment(getAdapterPosition(),v.findViewById(R.id.foodImage));


    }
}
