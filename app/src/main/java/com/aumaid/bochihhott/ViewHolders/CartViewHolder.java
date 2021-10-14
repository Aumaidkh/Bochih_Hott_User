package com.aumaid.bochihhott.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.RecyclerViewListener;
import com.aumaid.bochihhott.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



    private TextView mFoodName;
    private TextView mPrice;
    private TextView mItemRating;
    private TextView mRestaurantInfo;
    private ImageView mItemPhoto;
    private RelativeLayout mRemoveBtn;
    private ImageView mHeartIcon;
    private RelativeLayout mAddBtn;
    private TextView mQuantity;

    private RecyclerViewListener mListener;

    public CartViewHolder(@NonNull View itemView, RecyclerViewListener mListener) {
        super(itemView);

        mFoodName = itemView.findViewById(R.id.cartInfoTv);
        mPrice = itemView.findViewById(R.id.itemPriceTv);
        mItemPhoto = itemView.findViewById(R.id.productImage);
        mRestaurantInfo = itemView.findViewById(R.id.restaurantName);
        mRemoveBtn = itemView.findViewById(R.id.removeQuantityBtn);
        mAddBtn = itemView.findViewById(R.id.addQuantityBtn);
        mQuantity = itemView.findViewById(R.id.quantity);
        mItemRating = itemView.findViewById(R.id.ratingTv);
        mHeartIcon = itemView.findViewById(R.id.favoritesIcon);

        this.mListener = mListener;

        mRemoveBtn.setOnClickListener(this);
        mAddBtn.setOnClickListener(this);

    }

    public TextView getmItemRating() {
        return mItemRating;
    }

    public ImageView getmHeartIcon() {
        return mHeartIcon;
    }

    public RelativeLayout getmAddBtn() {
        return mAddBtn;
    }

    public TextView getmQuantity() {
        return mQuantity;
    }

    public ImageView getmItemPhoto() {
        return mItemPhoto;
    }

    public TextView getmFoodName() {
        return mFoodName;
    }

    public TextView getmPrice() {
        return mPrice;
    }

    public TextView getmRestaurantInfo() {
        return mRestaurantInfo;
    }

    public RelativeLayout getmRemoveBtn() {
        return mRemoveBtn;
    }

    @Override
    public void onClick(View v) {
        mListener.onViewClicked(v,getAdapterPosition());
    }
}
