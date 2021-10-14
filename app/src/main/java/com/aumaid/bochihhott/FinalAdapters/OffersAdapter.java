/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.FinalAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Models.CarouselData;
import com.aumaid.bochihhott.R;

import java.util.ArrayList;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OfferViewHolder> {

    private ArrayList<CarouselData> offers;
    private Context mContext;

    public OffersAdapter(ArrayList<CarouselData> offers,Context mContext){
        this.offers = offers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.final_sample_offers_layout,parent);
        OfferViewHolder holder = new OfferViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class OfferViewHolder extends RecyclerView.ViewHolder{

        private ImageView mOfferImage;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView = itemView.findViewById(R.id.offerImage);
        }
    }
}
