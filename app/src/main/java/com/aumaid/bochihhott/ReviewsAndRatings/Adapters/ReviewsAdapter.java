/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.ReviewsAndRatings.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ReviewsAndRatings.Models.ReviewModel;
import com.aumaid.bochihhott.Utils.StringManipulation;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {

    private static final String TAG = "ReviewsAdapter";

    private ArrayList<ReviewModel> reviews;
    private Context mContext;

    public ReviewsAdapter(ArrayList<ReviewModel> reviews, Context mContext) {
        this.reviews = reviews;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.final_sample_review_layout,parent,false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReviewsAdapter.ReviewHolder holder, int position) {
        ReviewModel review = reviews.get(position);
        try{
            holder.review.setText(review.getBody());
            holder.timestamp.setText(StringManipulation.extractDate(review.getTimestamp()));
            holder.username.setText(review.getUsername());
            Glide.with(mContext)
                    .load(review.getProfile_pic())
                    .into(holder.profilePic);
        }catch (NullPointerException e){
            Log.d(TAG, "onBindViewHolder: "+e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    /*Review View Holder*/
    public class ReviewHolder extends RecyclerView.ViewHolder {

        private ImageView profilePic;
        private TextView username;
        private TextView timestamp;
        private TextView review;

        public ReviewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.reviewerProfilePic);
            username = itemView.findViewById(R.id.reviewerUserName);
            timestamp = itemView.findViewById(R.id.reviewTimestamp);
            review = itemView.findViewById(R.id.review);
        }
    }
}
