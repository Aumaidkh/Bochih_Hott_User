/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.Restaurant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.FinalAdapters.FeaturedFoodItemAdapter;
import com.aumaid.bochihhott.FinalAdapters.FinalMenuAdapter;
import com.aumaid.bochihhott.FinalAdapters.FoodItemAdapter;
import com.aumaid.bochihhott.FinalAdapters.OffersAdapter;
import com.aumaid.bochihhott.FinalAdapters.SliderAdapter;
import com.aumaid.bochihhott.Interfaces.CategoriesOptionListener;
import com.aumaid.bochihhott.Interfaces.FoodItemListener;
import com.aumaid.bochihhott.Interfaces.RecyclerViewListener;
import com.aumaid.bochihhott.Models.CarouselData;
import com.aumaid.bochihhott.Models.Category;
import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.Models.MenuItem;
import com.aumaid.bochihhott.Models.Partner;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.CarouselHelper;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RestaurantFragment extends Fragment implements FoodItemListener, RecyclerViewListener, CategoriesOptionListener {



    private static final String TAG = "RestaurantFragment";

    /**
     * Declaring Widgets*/
    private ImageView mCoverImage;
    private ImageView mProfilePic;
    private ImageView mBackButton;

    private TextView mName;
    private TextView mAddress;
    private TextView mDistance;
    private TextView mTime;
    private TextView mRatings;
    private TextView mReviewsCount;
    private TextView mSeeReviews;

    private FoodItemAdapter foodItemAdapter;
    private FoodItemAdapter foodAdapter;

    private View view;

    /**
     * Other Vars*/
    private ArrayList<FoodItem> mFeaturedFoodItems;
    private ArrayList<FoodItem> mFoodItems;
    private ArrayList<MenuItem> menu;
    private ArrayList<FoodItem> mSelectedCategoryFoodItem;
    private ArrayList<CarouselData> mOffers;
    private Partner partner;
    private String distance;
    private String time;
    private int reviewsNum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Setting up Status bar
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.background_status_bar_filter));
        view = inflater.inflate(R.layout.fragment_restaurant_profile,container,false);

        setUpFragment();
        bindWidgets();
       // fetchFeaturedFoodItems();
        fetchOffers();
      //  fetchMenu();
        initWidgets();
       // loadRecyclerViews();

        return view;
    }



    private void fetchOffers(){
        mOffers.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("offers");

      //  reference.keepSynced(true);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    for(DataSnapshot ing: childDataSnapshot.getChildren()){

                        Log.d(TAG, "onDataChange: Showing Offers"+ing.toString());
                        mOffers.add(ing.getValue(CarouselData.class));

                    }
                }

                fetchMenu();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchMenu(){
        //Log.d(TAG, "fetchMenu: Fetching Restaurant Menu");
        Log.d(TAG, "fetchMenu: Total "+mOffers.size()+" offers added");
        menu.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("menus/"+partner.getRestaurant_id());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    //Log.d(TAG, "onDataChange: "+ds.toString());
                    MenuItem menuItem = ds.getValue(MenuItem.class);
                    menu.add(menuItem);

                }
                fetchFeaturedFoodItems();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void fetchFeaturedFoodItems(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("food_items").orderByChild("restaurant_id").equalTo(partner.getRestaurant_id());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    FoodItem item = ds.getValue(FoodItem.class);
                    if(item.getItem_ratings()>4.4&&mFeaturedFoodItems.size()<30){
                        mFeaturedFoodItems.add(item);
                    }
                    mFoodItems.add(item);
                }
                loadRecyclerViews();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setUpFragment(){
        if(getArguments()!=null){
            partner = (Partner) getArguments().getSerializable("RESTAURANT");
            //Log.d(TAG, "setUpFragment: Restaurant: "+partner.toString());
            calculate();
        }
        mFeaturedFoodItems = new ArrayList<>();
        menu = new ArrayList<>();
        mFoodItems = new ArrayList<>();
        mSelectedCategoryFoodItem = new ArrayList<>();
        mOffers = new ArrayList<>();

        RecyclerView mFoodItemsRv = view.findViewById(R.id.restaurantMenuRv);
        foodItemAdapter = new FoodItemAdapter(mFoodItems,getActivity(),this);
        mFoodItemsRv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        foodItemAdapter.notifyDataSetChanged();

    }
    private void bindWidgets(){
        mCoverImage = view.findViewById(R.id.restaurantCoverPhoto);
        mProfilePic = view.findViewById(R.id.restaurantPhoto);
        mBackButton = view.findViewById(R.id.backArrow);
        mName = view.findViewById(R.id.restaurantNameTv);
        mAddress = view.findViewById(R.id.restaurantAddressTv);
        mDistance = view.findViewById(R.id.distanceTv);
        mTime = view.findViewById(R.id.deliveryTimeTv);
        mRatings = view.findViewById(R.id.ratingsTv);
        mReviewsCount = view.findViewById(R.id.reviewsCount);
        mSeeReviews = view.findViewById(R.id.seeAllReviews);


    }
    private void initWidgets(){
        //Setting Images
        Glide.with(getActivity())
                .load(partner.getPhoto())
                .placeholder(R.drawable.burger1)
                .into(mCoverImage);

        Glide.with(getActivity())
                .load(partner.getPhoto())
                .placeholder(R.drawable.burger1)
                .into(mProfilePic);

        mName.setText(partner.getName());

        mAddress.setText(partner.getAddress()+","+partner.getCity());

        mRatings.setText(partner.getRatings()+"");

        if(reviewsNum<31){
           mReviewsCount.setText("30+");
        }else{
            mReviewsCount.setText(reviewsNum);
        }






    }
    private void initOffersAdapter(){
        //Automatic Sliding Duration
        final int time = 4000;
        RecyclerView offersRecyclerView = view.findViewById(R.id.offersRv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        OffersAdapter offersAdapter = new OffersAdapter(mOffers,getActivity());
        offersRecyclerView.setLayoutManager(layoutManager);
        offersRecyclerView.setAdapter(offersAdapter);

        //Automatic Sliding of adapter
        CarouselHelper.slide(offersRecyclerView,layoutManager,offersAdapter);

    }
    /**
     * This method is used to populate the food items Recycler view
     * Note: in future it must show the items from the category user clicks on */
    private void setUpFoodItemsRView(String category, View view){
        Log.d(TAG, "setUpFoodItemsRView: Category: "+category+" View :"+view);
        TextView mFoodCategoryHeadingText = view.findViewById(R.id.menuItemHeading);
        mFoodCategoryHeadingText.setText(category+"'s");
        // shimmerFrameLayout.startShimmer();
        RecyclerView mFoodItemsRecycler = view.findViewById(R.id.foodItemsRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mFoodItemsRecycler.setHasFixedSize(true);
        mFoodItemsRecycler.setLayoutManager(linearLayoutManager);

        // adapter = new FoodItemAdapter(mFoodItems,mContext,this);
        foodAdapter = new FoodItemAdapter(mSelectedCategoryFoodItem,getActivity(),this);
        mFoodItemsRecycler.setAdapter(foodAdapter);

        //Getting the data from firebase
        loadRecyclerViewData(category);


    }

    private void loadRecyclerViewData(String category){
        Log.d(TAG, "loadRecyclerViewData: Loading Data Starting shimmer");
//Tweak this method so that is shows the food of the selected category
        //Firebase
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Clearing the ArrayList first
                mSelectedCategoryFoodItem.clear();

                //Declaring restaurant
                // Restaurant restaurant = new Restaurant();



                //Receiving Data
                for (DataSnapshot ds : snapshot.child("categories").child(category).getChildren()) {
                    // Log.d(TAG, "onDataChange: "+ds);
//                    FoodItemRV itemRV = new FoodItemRV();
//                    Partner restaurant = new Partner();
                    FoodItem foodItem = ds.getValue(FoodItem.class);
                    mSelectedCategoryFoodItem.add(foodItem);
                    //Storing The key of the restaurant id into a variable
                    //  String restaurantId = foodItem.getRestaurant_id();

                    // Log.d(TAG, "onDataChange: Food Restaurant Id: "+restaurantId);

                    //Getting the Restaurant with the restaurantId
//                    for(DataSnapshot dataSnapshot: snapshot.child("restaurants").getChildren()){
//
//                        //Problematic snippet here
//                        if(dataSnapshot.getKey().equals(restaurantId)){
//                            restaurant = dataSnapshot.getValue(Partner.class);
//
//                            itemRV = new FoodItemRV(
//                                    foodItem.getItem_photo(),
//                                    restaurant.getName(),
//                                    restaurant.getAddress(),
//                                    foodItem.getCategory_id(),
//                                    foodItem.getItem_ratings(),
//                                    Float.parseFloat(foodItem.getPrice()));
//                            mFoodItems.add(itemRV);
//                        }
//
//                    }

                }


                foodAdapter.notifyDataSetChanged();
                Log.d(TAG, "onDataChange: Data Received Data hiding shimmer");
                //TODO: Hide Shimmer Here
                //hideShimmer();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    private void loadRecyclerViews(){
        initOffersAdapter();
        //For Featured Items add all those items with rating more than 4.5
        // and length of array list should be 10 only;
        RecyclerView mFeaturedFoodItemsRv = view.findViewById(R.id.restaurantFeaturedItemsRv);
        FeaturedFoodItemAdapter featuredFoodItemAdapter
                = new FeaturedFoodItemAdapter(mFeaturedFoodItems, getActivity(),this,this );
        LinearLayoutManager featuredLinearLayoutManger = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mFeaturedFoodItemsRv.setLayoutManager(featuredLinearLayoutManger);
        mFeaturedFoodItemsRv.setAdapter(featuredFoodItemAdapter);

        //Automatic Sliding Effect
        CarouselHelper.slide(mFeaturedFoodItemsRv,featuredLinearLayoutManger,featuredFoodItemAdapter);

        RecyclerView mRestaurantMenuRv = view.findViewById(R.id.restaurantMenuRv);
        FinalMenuAdapter FinalMenuAdapter
                = new FinalMenuAdapter(getActivity(),menu,this);
        mRestaurantMenuRv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mRestaurantMenuRv.setAdapter(FinalMenuAdapter);

        //Offers Carousel
      //  SliderView sliderView = view.findViewById(R.id.slider);
        // after adding data to our array list we are passing
        // that array list inside our adapter class.
      //  SliderAdapter sliderAdapter = new SliderAdapter(getActivity(), mOffers);

        // belows line is for setting adapter
        // to our slider view
      //  sliderView.setSliderAdapter(sliderAdapter);

        // below line is for setting animation to our slider.
      //  sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

        // below line is for setting auto cycle duration.
      //  sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);

        // below line is for setting
        // scroll time animation
     //   sliderView.setScrollTimeInSec(3);

        // below line is for setting auto
        // cycle animation to our slider
     //   sliderView.setAutoCycle(true);

        // below line is use to start
        // the animation of our slider view.
      //  sliderView.startAutoCycle();


        //For Menu Recycle the same home page categories however names of these categories
        //Should be coming from Server with the image as well

    }
    private void calculate(){

    }

    @Override
    public void onFoodClicked(int position) {

    }

    @Override
    public void onViewClicked(View v, int position) {


        //notify adapter here
    }

    @Override
    public void onCategoryClicked(int position) {

        mSelectedCategoryFoodItem.clear();
        MenuItem selectedCategory = menu.get(position);
        selectedCategory.setSelected(true);
//        Log.d(TAG, "onCategoryClicked: Category "+selectedCategory.getCategory_name()+" clicked");
//        for(int i=0;i<mFoodItems.size();i++){
//            FoodItem item = mFoodItems.get(i);
//            if(item.getCategory_id().matches(selectedCategory.getCategory_name())){
//                mSelectedCategoryFoodItem.add(item);
//            }
//        }
//
      //  setUpFoodItemsRView(selectedCategory.getCategory_name(),view);

        foodItemAdapter.notifyDataSetChanged();
    }
}