package com.aumaid.bochihhott.FinalAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.RestaurantListener;
import com.aumaid.bochihhott.Models.Partner;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ViewHolders.RestaurantsViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FinalFeaturedRestaurantsAdapter extends RecyclerView.Adapter<RestaurantsViewHolder> {

    private ArrayList<Partner> mPartners;
    private Context mContext;
    private RestaurantListener restaurantListener;

  //  private HomeFragment fragment;

    public FinalFeaturedRestaurantsAdapter(ArrayList<Partner> mPartners, Context mContext, RestaurantListener restaurantListener) {
        this.mPartners = mPartners;
        this.mContext = mContext;
        this.restaurantListener = restaurantListener;

    }


    @NonNull
    @Override
    public RestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_homefragment_restaurants_layout,parent,false);
        RestaurantsViewHolder viewHolder = new RestaurantsViewHolder(view,restaurantListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantsViewHolder holder, int position) {

        Partner item = mPartners.get(position);

        Glide.with(mContext)
                .load(item.getPhoto())
                .placeholder(R.drawable.burger1)
                .into(holder.getmRestaurantImage());
        holder.getmRestaurantName().setText(item.getName());
        holder.getmRestaurantAddress().setText(item.getAddress()+", "+item.getCity());
       // holder.getmFoodCategory().setText(item.getCategory_id());
       if(item.getRatings()!=null){
           holder.getmRestaurantRating().setText(item.getRatings()+"");
       }
        holder.getmDistance().setText("10 Km");
        holder.getmTime().setText("10 - 14 mins");


    }

    @Override
    public int getItemCount() {
        return mPartners.size();
    }

    /**
     * This method is used to add food items in the recycler view
     * @param item*/
    public void addFoodItems(Partner item){
        mPartners.add(item);
    }
}
