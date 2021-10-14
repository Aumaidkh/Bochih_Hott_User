package com.aumaid.bochihhott.ViewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.RecyclerViewListener;
import com.aumaid.bochihhott.R;

public class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mItemPhoto;
    private TextView mItemName;
    private TextView mItemPrice;
    private TextView mRatingsTv;
    private RatingBar mRatingsBar;
    private ImageButton mRemoveBtn;
    private RecyclerViewListener mListener;

    public FavoritesViewHolder(@NonNull View itemView, RecyclerViewListener mListener) {
        super(itemView);

        mItemPhoto = itemView.findViewById(R.id.itemPhoto);
        mItemName = itemView.findViewById(R.id.item_name);
        mItemPrice = itemView.findViewById(R.id.item_price);
        mRatingsTv = itemView.findViewById(R.id.ratings);
        mRatingsBar = itemView.findViewById(R.id.ratings_bar);
        mRemoveBtn = itemView.findViewById(R.id.removeBtn);
        this.mListener = mListener;

        mRemoveBtn.setOnClickListener(this::onClick);


    }

    public ImageButton getmRemoveBtn() {
        return mRemoveBtn;
    }

    public void setmRemoveBtn(ImageButton mRemoveBtn) {
        this.mRemoveBtn = mRemoveBtn;
    }

    public ImageView getmItemPhoto() {
        return mItemPhoto;
    }

    public void setmItemPhoto(ImageView mItemPhoto) {
        this.mItemPhoto = mItemPhoto;
    }

    public TextView getmItemName() {
        return mItemName;
    }

    public void setmItemName(TextView mItemName) {
        this.mItemName = mItemName;
    }

    public TextView getmItemPrice() {
        return mItemPrice;
    }

    public void setmItemPrice(TextView mItemPrice) {
        this.mItemPrice = mItemPrice;
    }

    public TextView getmRatingsTv() {
        return mRatingsTv;
    }

    public void setmRatingsTv(TextView mRatingsTv) {
        this.mRatingsTv = mRatingsTv;
    }

    public RatingBar getmRatingsBar() {
        return mRatingsBar;
    }

    public void setmRatingsBar(RatingBar mRatingsBar) {
        this.mRatingsBar = mRatingsBar;
    }

    @Override
    public void onClick(View v) {
        mListener.onViewClicked(v,getAdapterPosition());
    }
}
