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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.CategoriesOptionListener;
import com.aumaid.bochihhott.Models.MenuItem;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ViewHolders.CategoriesViewHolder;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {

    private static final String TAG = "MenuAdapter";

    private ArrayList<MenuItem> items;
    private Context mContext;
    private CategoriesOptionListener categoriesListener;

    //Re write the code for this adapter from scratch


    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sample_categories_recyclerview,parent,false);
        return new CategoriesViewHolder(view,categoriesListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        MenuItem item = items.get(position);
        holder.mCategoryName.setText(item.getCategory_name());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
