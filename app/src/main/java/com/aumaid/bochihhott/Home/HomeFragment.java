package com.aumaid.bochihhott.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Adapters.CategoriesAdapter;
import com.aumaid.bochihhott.FinalAdapters.FoodItemAdapter;
import com.aumaid.bochihhott.Adapters.PopularFoodsAdapter;
import com.aumaid.bochihhott.Adapters.SpecialOffersItemsAdapter;
import com.aumaid.bochihhott.Adapters.UniversalImageLoader;
import com.aumaid.bochihhott.DAO.User;
import com.aumaid.bochihhott.Interfaces.RecyclerViewItemClickListener;
import com.aumaid.bochihhott.Interfaces.FoodItemListener;
import com.aumaid.bochihhott.Interfaces.PopularFoodsListener;
import com.aumaid.bochihhott.Models.Category;
import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.Models.SpecialOfferedItem;
import com.aumaid.bochihhott.Profile.ActivityProfile;
import com.aumaid.bochihhott.Profile.EditProfileActivity;
import com.aumaid.bochihhott.Profile.ProfileActivity;
import com.aumaid.bochihhott.R;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment  implements RecyclerViewItemClickListener, PopularFoodsListener, FoodItemListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeFragment";

    private String type = "Food Items";

    private BottomNavigationView bottomNavigationView;

    private Context mContext;
    private ArrayList<Category> mCategories;
    private ArrayList<FoodItem> mPFItems;
    private ArrayList<SpecialOfferedItem> mSOItems;

    /**
     * This section is for all Food items Recycler View*/
    // private ArrayList<FoodItemRV> mFoodItems;
    private ArrayList<FoodItem> mFoodItems;
    private RecyclerView mFoodItemsRecycler;
    private DatabaseReference mFoodItemsRef;
    // private FoodItemAdapter adapter;
    private FoodItemAdapter adapter;

    private PopularFoodsAdapter pAdapter;

    /**
     * Shimmer Layout*/
    private ShimmerFrameLayout shimmerFrameLayout;
    private RelativeLayout mainRelativeLayout;
    private View view;

    private RecyclerView mCategoriesRecycler;
    private RecyclerView mPopularFoodsRecycler;
    private RecyclerView mSpecialOffersRecycler;
    private ImageView profileBtn;

    /**
     * Variables Section*/
    /**
     * Change this variable later just to make sure it shows all types of foods in the beginning*/
    private String category = "pizzas";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: HomeFragment Created");
        //Status bar color
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));


        view = inflater.inflate(R.layout.fragment_home,container,false);

        bottomNavigationView = view.findViewById(R.id.bottomNavViewBar);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
       // bottomNavigationView.setSelectedItemId(R.id.home);
        moveDownBottomNavigationBar();

        mContext = getActivity();
        mCategories = new ArrayList<>();

        shimmerFrameLayout = view.findViewById(R.id.shimmerLayout);

        mainRelativeLayout = view.findViewById(R.id.mainRelLayout);

        mCategoriesRecycler = view.findViewById(R.id.rvCategories);

        setUpCategoriesRView(view);
        setUpPopularFoodsRView(view);
        setUpFoodItemsRView(category,view);
        setUpSpecialOffersRView(view);
        initImageLoader();
        setButtonListeners();

        return view;
    }

    private void setButtonListeners(){
        //Search Button
        ImageView searchBtn = view.findViewById(R.id.search_button);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Profile Tapped", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });

        ImageView hamburgerMenu = view.findViewById(R.id.hamBurgerMenuBtn);
        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Profile Tapped", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), ActivityProfile.class));
            }
        });

        //Setting Profile Image for the profile button
        profileBtn = view.findViewById(R.id.profileMenu);
        setProfilePic();
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
                 Toast.makeText(mContext,"Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        //View all button for popular Foods
        Button viewAllPopularFoods = view.findViewById(R.id.viewAllPopularFoodsBtn);

        viewAllPopularFoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "Popular Foods";
                TextView mFoodCategoryHeadingText = view.findViewById(R.id.foodCategoryHeadingText);
                mFoodCategoryHeadingText.setText("Showing Popular Foods");
                adapter = new FoodItemAdapter(mPFItems,getActivity(),HomeFragment.this::onFoodClicked);
                mFoodItemsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                mFoodItemsRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * This method is used to populate the food items Recycler view
     * Note: in future it must show the items from the category user clicks on */
    private void setUpFoodItemsRView(String category, View view){
        Log.d(TAG, "setUpFoodItemsRView: Category: "+category+" View :"+view);
        TextView mFoodCategoryHeadingText = view.findViewById(R.id.foodCategoryHeadingText);
        mFoodCategoryHeadingText.setText("Showing All Foods");
        // shimmerFrameLayout.startShimmer();
        mFoodItemsRecycler = view.findViewById(R.id.foodItemsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mFoodItemsRecycler.setHasFixedSize(true);
        mFoodItemsRecycler.setLayoutManager(linearLayoutManager);

        mFoodItems = new ArrayList<>();

        // adapter = new FoodItemAdapter(mFoodItems,mContext,this);
        adapter = new FoodItemAdapter(mFoodItems,mContext,this);
        mFoodItemsRecycler.setAdapter(adapter);

        //Getting the data from firebase
        loadRecyclerViewData(category);




    }

    private void loadRecyclerViewData(String category){
        Log.d(TAG, "loadRecyclerViewData: Loading Data Starting shimmer");
//Tweak this method so that is shows the food of the selected category
        //Firebase
        mFoodItemsRef = FirebaseDatabase.getInstance().getReference();

        mFoodItemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Clearing the ArrayList first
                mFoodItems.clear();

                //Declaring restaurant
                // Restaurant restaurant = new Restaurant();



                //Receiving Data
                for (DataSnapshot ds : snapshot.child("categories").child(category).getChildren()) {
                    // Log.d(TAG, "onDataChange: "+ds);
//                    FoodItemRV itemRV = new FoodItemRV();
//                    Partner restaurant = new Partner();
                    FoodItem foodItem = ds.getValue(FoodItem.class);
                    mFoodItems.add(foodItem);
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


                adapter.notifyDataSetChanged();
                Log.d(TAG, "onDataChange: Data Received Data hiding shimmer");
                hideShimmer();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    /**
     * This method is used to populate the Special Offer Items Recycler View
     * */
    private void setUpSpecialOffersRView(View view){
        mSOItems = new ArrayList<>();

        mSpecialOffersRecycler = view.findViewById(R.id.specialOffersRecyclerView);

        mSOItems.add(new SpecialOfferedItem(R.drawable.sponsored_one,"Buy Two Get Two Free"));
        mSOItems.add(new SpecialOfferedItem(R.drawable.sponsored_two,"Buy Two Get one Free"));
        mSOItems.add(new SpecialOfferedItem(R.drawable.sponsored_three,"Buy Three Get Two Free"));
        mSOItems.add(new SpecialOfferedItem(R.drawable.sponsored_one,"Flat 50 Rs Cashback"));

        SpecialOffersItemsAdapter adapter = new SpecialOffersItemsAdapter(mSOItems);
        mSpecialOffersRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mSpecialOffersRecycler.setAdapter(adapter);
    }

    /**
     * This Method is used to populate the Popular Foods Recycler View
     * images are supposed to be explicitly made of width 120px and height 100px*/
    private void setUpPopularFoodsRView(View view){
        mPFItems = new ArrayList<>();
        mPopularFoodsRecycler = view.findViewById(R.id.popularFoodsRecyclerView);

        //Find Food Items with ratings greater than 4.5

        //Add Those items into the mPFItems arrayList


        //Adding food items to the arrayList
//        mPFItems.add(new PopularFood("Burger",
//                "The food is supposed to be tasty but i haven't tried it yet",
//                "120.00",
//                4,R.drawable.burger));
//
//        mPFItems.add(new PopularFood("Burger",
//                "The food is supposed to be tasty but i haven't tried it yet",
//                "120.00",
//                4,R.drawable.salad));
//
//        mPFItems.add(new PopularFood("Burger",
//                "The food is supposed to be tasty but i haven't tried it yet",
//                "120.00",
//                4,R.drawable.burger));
//
//        mPFItems.add(new PopularFood("Burger",
//                "The food is supposed to be tasty but i haven't tried it yet",
//                "120.00",
//                4,R.drawabl

        pAdapter = new PopularFoodsAdapter(mPFItems, getActivity(),this);
        mPopularFoodsRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mPopularFoodsRecycler.setAdapter(pAdapter);
        loadPopularFoods();
    }

    private void loadPopularFoods(){
        //Read All Food Items From The Database
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("food_items");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mPFItems.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    FoodItem item = ds.getValue(FoodItem.class);
                    if(item.getItem_ratings()>=4.5f){
                        mPFItems.add(item);
                    }
                }

              pAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * This Method is used to populate the categories Recycler view*/
    private void setUpCategoriesRView(View view){
        mCategories.add(new Category("Pizzas",R.drawable.ic_cart));
        mCategories.add(new Category("Burgers",R.drawable.ic_cart));
        mCategories.add(new Category("Rolls",R.drawable.ic_cart));
        mCategories.add(new Category("Soups",R.drawable.ic_cart));
        mCategories.add(new Category("Chicken",R.drawable.ic_cart));
        mCategories.add(new Category("Noodles",R.drawable.ic_cart));
        mCategories.add(new Category("Biryani",R.drawable.ic_cart));
        mCategories.add(new Category("Fries",R.drawable.ic_cart));
        mCategories.add(new Category("Dals",R.drawable.ic_cart));
        mCategories.add(new Category("Waazwaan",R.drawable.ic_cart));

        CategoriesAdapter adapter = new CategoriesAdapter(mCategories,this);
        mCategoriesRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mCategoriesRecycler.setAdapter(adapter);

    }


    /**
     * This method is used to initialize the image loader which is then used
     * to display profile photo*/
    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    /**
     * This method assigns functionality to the categories recycler view in home screen*/
    @Override
    public void onViewClicked(int position) {
        shimmerFrameLayout.startShimmer();
        type = "Food Items";
        switch (position){
            case 0:
                category = "Pizzas";
                break;
            case 1:
                category = "Burgers";
                break;
            case 2:
                category = "Rolls";
                break;
            case 3:
                category = "Soups";
                break;
            case 4:
                category = "Chicken";
                break;
            case 5:
                category = "Noodles";
                break;
            case 6:
                category = "Biryani";
                break;
            case 7:
                category = "Fries";
                break;
            case 8:
                category = "Dals";
                break;
            case 9:
                category = "Wazwaan";
                break;
        }
        category = category.toLowerCase();
        setUpFoodItemsRView(category,view);
  //      Log.d(TAG, "onCategoryClicked: Category: "+category+" View :"+view);
    }

    /**
     * This method is used to show the shimmer screen and hiding the main screen*/
    private void showShimmer(){
        mainRelativeLayout.setVisibility(View.GONE);
        shimmerFrameLayout.startShimmer();
    }

    /**
     * This method is used to hide the shimmer*/
    private void hideShimmer(){
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        mainRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFoodClicked(int position) {
        FoodItem item;
        if(type.matches("Food Items")){
            Log.d(TAG, "onFoodClicked: Position: "+position);
            item = mFoodItems.get(position);
            Log.d(TAG, "onFoodClicked: "+item.getItem_name()+" clicked");
        }else{
            Log.d(TAG, "onFoodClicked: Position: "+position);
            item = mPFItems.get(position);
            Log.d(TAG, "onFoodClicked: "+item.getItem_name()+" clicked");
        }

        productDescriptionFragment(item);
    }


    private void productDescriptionFragment(FoodItem item){
        Log.d(TAG, "init: Inflating Profile Fragment");

        Bundle bundle = new Bundle();
        bundle.putSerializable("FOOD_ITEM", item);

       /* ProductDescriptionNewFragment fragment = new ProductDescriptionNewFragment();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack("Product Description Fragment");
        transaction.commit();*/

    }


    @Override
    public void onPopularFoodClicked(int position) {
        FoodItem item = mPFItems.get(position);
        // Apply activity transition
        Bundle bundle = new Bundle();
        ProductDescriptionFragment fragment = new ProductDescriptionFragment();
        bundle.putString("transitionName", "transition" + position);
        bundle.putSerializable("FOOD_ITEM", item);
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();


    }

    private void setProfilePic(){
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("users/"+ FirebaseAuth.getInstance().getUid());

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Glide.with(mContext)
                        .load(user.getProfile_photo())
                        .into(profileBtn);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                HomeFragment homeFragment = new HomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                Log.d(TAG, "onNavigationItemSelected: Home Fragment Tapped");
                return true;
            case R.id.cart:
                CartFragment cartFragment = new CartFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, cartFragment).commit();
                Log.d(TAG, "onNavigationItemSelected: Cart Fragment Tapped");

                return true;
            case R.id.favorites:
                FavoritesFragment favoritesFragment = new FavoritesFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,favoritesFragment).commit();
                return true;
            case R.id.options:
                OptionsFragment optionsFragment = new OptionsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, optionsFragment).commit();
                Log.d(TAG, "onNavigationItemSelected: Options Fragment Tapped");
                return true;
        }
        return false;
    }

    private void moveDownBottomNavigationBar(){
        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // some code depending on keyboard visiblity status
                        if(isOpen){
                            bottomNavigationView.setVisibility(View.GONE);
                        }else{
                            bottomNavigationView.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
