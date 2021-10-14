package com.aumaid.bochihhott.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.RecyclerViewListener;
import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ViewHolders.FavoritesViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {

    private ArrayList<FoodItem> mFavoriteItems;
    private Context mContext;
    private RecyclerViewListener mViewListener;

    public FavoritesAdapter(ArrayList<FoodItem> mFavoriteItems, Context mContext, RecyclerViewListener mViewListener) {
        this.mFavoriteItems = mFavoriteItems;
        this.mContext = mContext;
        this.mViewListener = mViewListener;
    }


    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sample_favorites_layout,parent,false);
        FavoritesViewHolder favoritesViewHolder = new FavoritesViewHolder(view, mViewListener);
        return favoritesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {

        FoodItem item = mFavoriteItems.get(position);

        //Setting Image
        Glide.with(mContext)
                .load(item.getItem_photo())
                .into(holder.getmItemPhoto());

        //Other Details
        holder.getmItemName().setText(item.getItem_name());
        holder.getmItemPrice().setText(item.getPrice());
        holder.getmRatingsTv().setText(Float.toString(item.getItem_ratings()));
        holder.getmRatingsBar().setRating(item.getItem_ratings());

    }

    @Override
    public int getItemCount() {
        return mFavoriteItems.size();
    }
}
