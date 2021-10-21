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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.RecyclerViewItemClickListener;
import com.aumaid.bochihhott.Interfaces.RecyclerViewListener;
import com.aumaid.bochihhott.Models.MenuItem;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.StringManipulation;
import com.aumaid.bochihhott.ViewHolders.CategoriesViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private static final String TAG = "MenuAdapter";
    private ArrayList<MenuItem> menuItems;
    private Context mContext;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public MenuAdapter(ArrayList<MenuItem> menuItems, Context mContext, RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.menuItems = menuItems;
        this.mContext = mContext;
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sample_menu_layout,parent,false);
        return new MenuViewHolder(view,recyclerViewItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem menuItem = menuItems.get(position);

        try{
            holder.mMenuItemName.setText(StringManipulation.capitalizeFirstLetter(menuItem.getCategory_name()).concat("'s"));
            Glide.with(mContext)
                    .load(menuItem.getCategory_icon())
                    .into(holder.mMenuItemImage);
        }catch(NullPointerException e){
            Log.d(TAG, "onBindViewHolder: "+e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mMenuItemImage;
        private TextView mMenuItemName;
        private RecyclerViewItemClickListener recyclerViewItemClickListener;

        public MenuViewHolder(@NonNull View itemView, RecyclerViewItemClickListener recyclerViewItemClickListener) {
            super(itemView);

            mMenuItemImage = itemView.findViewById(R.id.menuItemImage);
            mMenuItemName = itemView.findViewById(R.id.menuItemName);
            this.recyclerViewItemClickListener = recyclerViewItemClickListener;

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            recyclerViewItemClickListener.onViewClicked(getAdapterPosition());
        }
    }
}
