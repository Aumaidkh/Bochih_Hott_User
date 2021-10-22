/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.Restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aumaid.bochihhott.Interfaces.RecyclerViewItemClickListener;
import com.aumaid.bochihhott.Models.MenuItem;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.StringManipulation;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RestaurantMenuAdapter extends ArrayAdapter<MenuItem> {
    private static final String TAG = "RestaurantMenuAdapter";

    private Context context;
    private ArrayList<MenuItem> menuItems;
 //   private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public RestaurantMenuAdapter(Context context, ArrayList<MenuItem> menuItems){
        super(context,0,menuItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent)
    {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sample_menu_layout, parent, false);
        }

        TextView menuItemName = convertView.findViewById(R.id.menuItemName);
        ImageView menuItemImage = convertView.findViewById(R.id.menuItemImage);
        MenuItem currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            menuItemName.setText(StringManipulation.capitalizeFirstLetter(currentItem.getCategory_name()).concat("'s"));
            Glide.with(getContext())
                    .load(currentItem.getCategory_icon())
                    .into(menuItemImage);
        }
        return convertView;
    }


}
