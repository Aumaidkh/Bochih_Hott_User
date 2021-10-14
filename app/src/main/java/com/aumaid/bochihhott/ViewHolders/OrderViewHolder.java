package com.aumaid.bochihhott.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.R;

import org.w3c.dom.Text;

public class OrderViewHolder extends RecyclerView.ViewHolder {

    /**
     * Declaring Widgets*/
    private ImageView mOrderImage;
    private TextView mRestaurantName;
    private TextView mRestaurantAddress;
    private TextView mPrice;
    private TextView mItems;
    private TextView mTimeStamp;



    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        //Binding Widgets
        mOrderImage = itemView.findViewById(R.id.orderImage);
        mRestaurantName = itemView.findViewById(R.id.restaurantNameTv);
        mRestaurantAddress = itemView.findViewById(R.id.restaurantAddressTv);
        mPrice = itemView.findViewById(R.id.priceTv);
        mItems = itemView.findViewById(R.id.orderItems);
        mTimeStamp = itemView.findViewById(R.id.orderOnTv);
    }

    public ImageView getmOrderImage() {
        return mOrderImage;
    }

    public TextView getmRestaurantName() {
        return mRestaurantName;
    }

    public TextView getmRestaurantAddress() {
        return mRestaurantAddress;
    }

    public TextView getmPrice() {
        return mPrice;
    }

    public TextView getmItems() {
        return mItems;
    }

    public TextView getmTimeStamp() {
        return mTimeStamp;
    }
}
