package com.aumaid.bochihhott.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.PopularFoodsListener;
import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.Models.PopularFood;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ViewHolders.PopularFoodsViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PopularFoodsAdapter extends RecyclerView.Adapter<PopularFoodsViewHolder> {

    private static final String TAG = "PopularFoodsAdapter";

    /**
     * ArrayList Containing Food Items*/
    private ArrayList<FoodItem> mPFItems;
    private Context mContext;
    private PopularFoodsListener popularFoodsListener;
    private String layout;

    public PopularFoodsAdapter(ArrayList<FoodItem> mPFItems, Context context, PopularFoodsListener popularFoodsListener) {
        this.mPFItems = mPFItems;
        this.mContext = context;
        this.popularFoodsListener = popularFoodsListener;

    }

    @NonNull
    @Override
    public PopularFoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: showing popular foods");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_popular_item_layout,parent,false);
        PopularFoodsViewHolder viewHolder = new PopularFoodsViewHolder(view,popularFoodsListener);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull PopularFoodsViewHolder holder, int position) {

        FoodItem food = mPFItems.get(position);

        Glide.with(mContext)
                .load(food.getItem_photo())
                .into(holder.getmPFImage());

        holder.getmPFName().setText(food.getItem_name());
        holder.getmPFDesc().setText(food.getDescription());
        holder.getmPFPrice().setText("₹ "+food.getPrice());
        holder.getmPFRating().setRating(food.getItem_ratings());
    }

    @Override
    public int getItemCount() {
        return mPFItems.size();
    }

    /**
     * This Method is used to add food items to popular foods RecyclerView
     * @param item*/

    public void addPFItem(FoodItem item){
        mPFItems.add(item);
    }
}
