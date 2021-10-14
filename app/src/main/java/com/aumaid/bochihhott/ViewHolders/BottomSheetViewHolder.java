package com.aumaid.bochihhott.ViewHolders;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.R;

public class BottomSheetViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "BottomSheetViewHolder";

    private ImageView mItemPhoto;
    private TextView mItemName;
    private TextView mItemQuantity;
    private TextView mItemPrice;

    public BottomSheetViewHolder(@NonNull View itemView) {
        super(itemView);
        Log.d(TAG, "BottomSheetViewHolder: Binding Widgets....");

        mItemPhoto = itemView.findViewById(R.id.productImage);
        mItemQuantity = itemView.findViewById(R.id.itemQuantity);
        mItemPrice = itemView.findViewById(R.id.itemPriceTv);
        mItemName = itemView.findViewById(R.id.itemNameTv);
    }

    public ImageView getmItemPhoto() {
        return mItemPhoto;
    }

    public TextView getmItemName() {
        return mItemName;
    }

    public TextView getmItemQuantity() {
        return mItemQuantity;
    }

    public TextView getmItemPrice() {
        return mItemPrice;
    }
}
