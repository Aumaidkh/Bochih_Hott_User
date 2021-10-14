package com.aumaid.bochihhott.FinalAdapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Home.HomeFragment;
import com.aumaid.bochihhott.Interfaces.FoodItemListener;
import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.Models.PopularFood;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ViewHolders.FoodItemViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemViewHolder> {

    private ArrayList<FoodItem> mFoodItems;
    private Context mContext;
    private FoodItemListener foodItemListener;
  //  private HomeFragment fragment;

    public FoodItemAdapter(ArrayList<FoodItem> mFoodItems, Context mContext, FoodItemListener foodItemListener) {
        this.mFoodItems = mFoodItems;
        this.mContext = mContext;
        this.foodItemListener = foodItemListener;

    }


    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_homefragment_food_items_rv,parent,false);
        FoodItemViewHolder viewHolder = new FoodItemViewHolder(view,foodItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {

        FoodItem item = mFoodItems.get(position);

        Glide.with(mContext)
                .load(item.getItem_photo())
                .into(holder.getmFoodImage());
        holder.getmFoodItemName().setText(item.getRestaurant_name());
        holder.getmFoodRestaurantAddress().setText(item.getRestaurant_address());
       // holder.getmFoodCategory().setText(item.getCategory_id());
        holder.getmFoodRating().setText(Float.toString(item.getItem_ratings()));
        holder.getmFoodPrice().setText("₹ "+item.getPrice());

    }

    @Override
    public int getItemCount() {
        return mFoodItems.size();
    }

    /**
     * This method is used to add food items in the recycler view
     * @param item*/
    public void addFoodItems(FoodItem item){
        mFoodItems.add(item);
    }
}
