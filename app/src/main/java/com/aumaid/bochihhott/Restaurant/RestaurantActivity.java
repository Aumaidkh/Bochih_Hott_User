/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.Restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.aumaid.bochihhott.Models.Partner;
import com.aumaid.bochihhott.R;

public class RestaurantActivity extends AppCompatActivity {

    private static final String TAG = "RestaurantActivity";
    private String category;
    private Partner partner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        receiveDataFromPreviousActivity();
        loadRestaurantProfileFragment();
    }

    private void receiveDataFromPreviousActivity(){
        category = getIntent().getStringExtra("CATEGORY");
        partner = (Partner) getIntent().getSerializableExtra("RESTAURANT");
    }

    private void loadRestaurantProfileFragment(){
        Bundle bundle = new Bundle();
        bundle.putString("CATEGORY",category);
        bundle.putSerializable("RESTAURANT",partner);
        RestaurantFragment restaurantFragment = new RestaurantFragment();
        restaurantFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.restaurantContainer,restaurantFragment);
        transaction.commit();
    }



}