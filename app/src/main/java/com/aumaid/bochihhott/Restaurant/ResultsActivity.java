/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.Restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aumaid.bochihhott.FinalAdapters.FeaturedFoodItemAdapter;
import com.aumaid.bochihhott.FinalAdapters.VerticalFoodItemAdapter;
import com.aumaid.bochihhott.Home.FinalProductDescriptionFragment;
import com.aumaid.bochihhott.Interfaces.FoodItemListener;
import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.StringManipulation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity implements FoodItemListener {

    private static final String TAG = "ResultsActivity";

    private TextView mMenuItemName;
    private TextView mNumberOfItems;
    private TextView mShowingAllText;
    private ImageView mMenuImage;
    private ProgressBar mProgressbar;

    private ArrayList<FoodItem> foodItems;
    private String category;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(getResources().getColor(R.color.background_status_bar_filter));

        setContentView(R.layout.activity_results);
        //Making Sure something is attached to the intent
        if(!(getIntent().hasExtra("CATEGORY")&&getIntent().hasExtra("RESTAURANT_ID"))){
            return;
        }

        category = getIntent().getStringExtra("CATEGORY");
        //Now fetch the data from the server to
        initButtonListeners();
        bindWidgets();
        fetchData();


    }

    private void fetchData(){
        foodItems = new ArrayList<>();
        String res_id = getIntent().getStringExtra("RESTAURANT_ID");
        //Getting Food Items
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("food_items");
        Query query = myRef.orderByChild("restaurant_id").equalTo(res_id);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.exists()){
                   for(DataSnapshot singleSnapshot: snapshot.getChildren()){
                       FoodItem item = singleSnapshot.getValue(FoodItem.class);
                       if(item.getCategory_id().matches(category)){
                           ++count;
                           foodItems.add(item);
                       }
                   }

                   initWidgets();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void bindWidgets(){
        mMenuItemName = findViewById(R.id.menuItemName);
        mNumberOfItems = findViewById(R.id.numberOfMenuItems);
        mShowingAllText = findViewById(R.id.showingAllText);
        mMenuImage = findViewById(R.id.categoryImage);
        mProgressbar = findViewById(R.id.progressBar);

        //Showing Progress bar
        mProgressbar.setVisibility(View.VISIBLE);

    }

    private void initWidgets(){


        RecyclerView mFoodItemsRecyclerView = findViewById(R.id.foodItemsRecyclerView);
        VerticalFoodItemAdapter foodItemAdapter = new VerticalFoodItemAdapter(foodItems,getApplicationContext(),this);
        mFoodItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mFoodItemsRecyclerView.setAdapter(foodItemAdapter);

        mProgressbar.setVisibility(View.GONE);
        mMenuItemName.setText(StringManipulation.capitalizeFirstLetter(category).concat("'s"));
        mNumberOfItems.setText(count+" items in "+StringManipulation.capitalizeFirstLetter(category).concat("'s"));
        mShowingAllText.setText("Showing all "+StringManipulation.capitalizeFirstLetter(category).concat("'s"));
    }

    private void initButtonListeners(){
        CardView mBackBtn = findViewById(R.id.backBtnCard);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onFoodClicked(int position) {
        FoodItem foodItem = foodItems.get(position);
        Log.d(TAG, "init: Inflating Food Description Fragment");

        Bundle bundle = new Bundle();
        bundle.putSerializable("FOOD_ITEM", foodItem);

        FinalProductDescriptionFragment fragment = new FinalProductDescriptionFragment();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack("Product Description Fragment");
        transaction.commit();
    }
}