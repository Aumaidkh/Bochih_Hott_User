package com.aumaid.bochihhott.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.R;

public class SpecialOffersItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView mSOImage;
    private TextView mSOOffer;

    public SpecialOffersItemViewHolder(@NonNull View itemView) {
        super(itemView);
        
        mSOImage = itemView.findViewById(R.id.specialOfferedFoodItemImage);
        mSOOffer = itemView.findViewById(R.id.specialOfferedFoodItemOffer);
        
    }

    public ImageView getmSOImage() {
        return mSOImage;
    }

    public void setmSOImage(ImageView mSOImage) {
        this.mSOImage = mSOImage;
    }

    public TextView getmSOOffer() {
        return mSOOffer;
    }

    public void setmSOOffer(TextView mSOOffer) {
        this.mSOOffer = mSOOffer;
    }
}
