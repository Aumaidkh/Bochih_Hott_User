/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.FinalAdapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Models.MenuItem;
import com.aumaid.bochihhott.ViewHolders.CategoriesViewHolder;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {

    private static final String TAG = "MenuAdapter";

    private ArrayList<MenuItem> items;
    private Context mContext;

    //Re write the code for this adapter from scratch


    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
