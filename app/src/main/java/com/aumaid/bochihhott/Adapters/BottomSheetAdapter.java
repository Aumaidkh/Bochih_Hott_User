package com.aumaid.bochihhott.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ViewHolders.BottomSheetViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;



public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetViewHolder>{

    private static final String TAG = "BottomSheetAdapter";

    private ArrayList<FoodItem> items;
    private Context mContext;

    public BottomSheetAdapter(ArrayList<FoodItem> items, Context mContext) {
        Log.d(TAG, "BottomSheetAdapter: Creating Adapter");
        this.items = items;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BottomSheetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sample_bottom_sheet_layout,parent,false);
        BottomSheetViewHolder viewHolder = new BottomSheetViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BottomSheetViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Binding Widgets");
        FoodItem item = items.get(position);

        Log.d(TAG, "onBindViewHolder: Item: "+item.toString());

        //Setting Image
        Glide.with(mContext)
                .load(item.getItem_photo())
                .into(holder.getmItemPhoto());

        //Setting other data
        holder.getmItemName().setText(item.getItem_name());
        holder.getmItemQuantity().setText("x"+Integer.toString(item.getQuantity()));
        holder.getmItemPrice().setText("₹ "+item.getPrice());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
