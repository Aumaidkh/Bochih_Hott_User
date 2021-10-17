/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.FinalAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Models.CarouselData;
import com.aumaid.bochihhott.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OfferViewHolder> {

    private static final String TAG = "OffersAdapter";

    private ArrayList<CarouselData> offers;
    private Context mContext;

    public OffersAdapter(ArrayList<CarouselData> offers,Context mContext){
        this.offers = offers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.final_sample_offers_layout,parent,false);
        OfferViewHolder viewHolder = new OfferViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        CarouselData offer = offers.get(position);

        Log.d(TAG, "onBindViewHolder: Offer Image :"+offer.getImageUrl());
        //Setting Image for the offer on the recycler view
        try{
        Glide.with(mContext)
                .load(offer.getImageUrl())
                .into(holder.mOfferImage);
        }catch (NullPointerException e){
            Log.d(TAG, "onBindViewHolder: "+e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }


    public class OfferViewHolder extends RecyclerView.ViewHolder{

        private ImageView mOfferImage;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);

            mOfferImage = itemView.findViewById(R.id.offerImage);
        }
    }
}
