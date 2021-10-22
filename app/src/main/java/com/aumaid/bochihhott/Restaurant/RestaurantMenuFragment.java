/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.Restaurant;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.DAO.ReviewDao;
import com.aumaid.bochihhott.DAO.User;
import com.aumaid.bochihhott.FinalAdapters.FinalMenuAdapter;
import com.aumaid.bochihhott.FinalAdapters.MenuAdapter;
import com.aumaid.bochihhott.Interfaces.RecyclerViewItemClickListener;
import com.aumaid.bochihhott.Interfaces.RecyclerViewListener;
import com.aumaid.bochihhott.Models.MenuItem;
import com.aumaid.bochihhott.Models.Partner;
import com.aumaid.bochihhott.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogEngine;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

public class RestaurantMenuFragment extends SupportBlurDialogFragment implements RecyclerViewItemClickListener {

    private static final String TAG = "OrderReviewAndRatingFra";

    private BlurDialogEngine mBlurEngine;

    private View view;
    private RecyclerView mMenuRecycler;

    private ArrayList<MenuItem> menuItems;
    private Partner partner;
    //Needs a restaurant id as well for making a review to it

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_restaurant_menu_layout, container, false);
        //Setting up fragment snippet
        //  Order order = (Order) getArguments().getSerializable("ORDER");
        // Log.d(TAG, "onCreateView: SHowing Order: "+order.toString());
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpFragment();
        fetchMenu();


    }

    private void fetchMenu(){
        ////Log.d(TAG, "fetchMenu: Fetching Restaurant Menu");
        //Log.d(TAG, "fetchMenu: Total "+mOffers.size()+" offers added");
        if(getArguments()==null){
            return;
        }
        partner = (Partner) getArguments().getSerializable("RESTAURANT");
        menuItems.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("menus/"+partner.getRestaurant_id());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    ////Log.d(TAG, "onDataChange: "+ds.toString());
                    MenuItem menuItem = ds.getValue(MenuItem.class);
                    Log.d(TAG, "onDataChange: Menu Icon: "+ds.toString());
                    menuItems.add(menuItem);
                    //  Log.d(TAG, "onDataChange: Menu Icon: "+menuItem.getCategory_icon());

                }
                initWidgets();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /**
     * This method resizes the fragment to fit into the
     * parent fragment */
    private void setUpFragment(){

        mBlurEngine = new BlurDialogEngine(getActivity());
        mBlurEngine.setBlurRadius(8);
        mBlurEngine.setDownScaleFactor(3f);
        mBlurEngine.debug(true);
        mBlurEngine.setBlurActionBar(true);
        mBlurEngine.setUseRenderScript(true);

        menuItems = new ArrayList<>();
        Log.d(TAG, "setUpFragment: Setting up fraGMENT...");
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics metrics = getResources().getDisplayMetrics();

            int width = metrics.widthPixels;
            int height = metrics.heightPixels-650;
                    Log.d(TAG, "setUpFragment: height:"+height);
            dialog.getWindow().setLayout((6 * width)/7, (6 * height)/6);

        }else{
            Log.d(TAG, "setUpFragment: DIalog is null...");
        }

    }

    @Override
    protected int getBlurRadius() {
        // Allow to customize the blur radius factor.
        return 2;
    }

    @Override
    protected float getDownScaleFactor() {
        // Allow to customize the down scale factor.
        return 1.9f;
    }



    private void initWidgets(){

        RecyclerView menuRecyclerView = view.findViewById(R.id.menuRecyclerView);
        MenuAdapter menuAdapter = new MenuAdapter(menuItems,getActivity(),this);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        menuRecyclerView.setAdapter(menuAdapter);

    }


    @Override
    public void onViewClicked(int position) {
        MenuItem item = menuItems.get(position);
        String category_name = item.getCategory_name();

        Intent intent = new Intent(getActivity(),ResultsActivity.class);
        intent.putExtra("CATEGORY",category_name);
        intent.putExtra("RESTAURANT_ID",partner.getRestaurant_id());
        startActivity(intent);
    }
}
