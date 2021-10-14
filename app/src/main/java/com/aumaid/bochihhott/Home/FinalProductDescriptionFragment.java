package com.aumaid.bochihhott.Home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.DAO.CartDao;
import com.aumaid.bochihhott.FinalAdapters.FoodItemAdapter;
import com.aumaid.bochihhott.Interfaces.FoodItemListener;
import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.StringManipulation;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FinalProductDescriptionFragment extends Fragment implements FoodItemListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "FinalProductDescriptionFragment";

    private BottomNavigationView bottomNavigationView;

    /**
     * Declaring widgets
     */
    private TextView mProductNameTv;
    private TextView mProductDescriptionTv;
    private TextView mQuantityTv;
    private TextView mRatingTv;
    private RatingBar mRatingBar;
    private TextView mPriceTv;

    private ImageView mProductImage;
    private ImageView mBackButton; //
    private ImageView mAddToFavoritesButton;


    private RelativeLayout mAddButton;
    private RelativeLayout mDeleteButton;
    private RelativeLayout mAddToCartButton;

    private ArrayList<FoodItem> similarItems;





    /**
     * Other vars
     */
    private View view;
    private int count = 01;
    private Context mContext;
    private String name;
    private String rname;
    private String raddress;
    private String photo;
    private String description;
    private String rating;
    private String price;
    private String item_id;
    private boolean isFavorite = false;

    private FoodItem item;

    private int amount = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.background_status_bar_filter));
        view = inflater.inflate(R.layout.final_product_description_fragment_layout, container, false);
        bottomNavigationView = view.findViewById(R.id.bottomNavViewBar);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bindWidgets();
        //Retrieving data from previous fragment
        Bundle bundle = this.getArguments();
        /*item_id = bundle.getString("PRODUCT_ID");
        name = bundle.getString("PRODUCT_NAME");
        photo = bundle.getString("PRODUCT_PHOTO");
        description = bundle.getString("PRODUCT_DESCRIPTION");
        rating = bundle.getString("PRODUCT_RATING");
        price = bundle.getString("PRODUCT_PRICE");
        rname = bundle.getString("RESTAURANT_NAME");
        raddress = bundle.getString("RESTAURANT_ADDRESS");*/

        item = (FoodItem) bundle.getSerializable("FOOD_ITEM");
        Log.d(TAG, "onCreateView: Food Item: " + item.getItem_photo());
        Glide.with(getActivity())
                .load(item.getItem_photo())
                .into(mProductImage);


        checkIfFavorite();
        //Animate Product Image
        //mProductImage.animate().rotationBy(-340f).translationXBy(-800).setDuration(800);
        attachButtonListeners();

        fetchSimilarItems();

        return view;

    }

    private void attachButtonListeners() {
        //Back Button Functionality
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "init: Inflating Home Fragment");

                HomeFragment fragment = new HomeFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack("Home Fragment");
                transaction.commit();
            }
        });

        //Adding Quantity
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Add Pressed: " + count);
                ++count;
                Log.d(TAG, "onClick: Count: " + count);
                //calculate the amount
                amount = count * Integer.parseInt(item.getPrice());
                mQuantityTv.setText(Integer.toString(count));
                mPriceTv.setText("₹" + Integer.toString(amount));


            }
        });

        //Decreasing Quantity
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 1) {
                    --count;
                    Log.d(TAG, "onClick: Count: " + count);
                    //calculate the amount
                    amount = count * Integer.parseInt(item.getPrice());
                    mQuantityTv.setText(Integer.toString(count));
                    mPriceTv.setText("₹" + Integer.toString(amount));
                }
            }
        });

        //Adding an item to the cart
        mAddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.d(TAG, "onClick: attempting to add item in cart");
                //  Log.d(TAG, "onClick: Item Id:"+item.getItem_id());
                CartDao cartDao = new CartDao(mContext);
                //Adding Quantity to the Food Item
                item.setQuantity(count);
                cartDao.addItemToCart(item);
                // cartDao.addItemToCart(item.getItem_id(),Integer.parseInt(mQuantityTv.getText().toString()));
                // addItemToCart(item_id,Integer.parseInt(mQuantityTv.getText().toString()));
                Toast.makeText(mContext, "Item added to your cart", Toast.LENGTH_SHORT).show();
                BottomSheetFragment fragment = new BottomSheetFragment();
                fragment.show(getActivity().getSupportFragmentManager(),"Bottom Sheet");
             //   mContinueShoppingButton.setVisibility(View.VISIBLE);
              //  mAddToCartButton.setVisibility(View.GONE);
             //   mGoToCartButton.setVisibility(View.VISIBLE);
                //Add Food Item To the Cart

            }
        });

        //Continue shopping btn


        //Add to favorites
        mAddToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartDao cartDao = new CartDao(getActivity());
                if(isFavorite){
                    
                    cartDao.removeItemFromFavorites(item);
                    mAddToFavoritesButton.setImageResource(R.drawable.heart);
                    Log.d(TAG, "onClick: Removed from favorites");
                    isFavorite=false;

                }else{
                    cartDao.addItemToFavorites(item);
                    mAddToFavoritesButton.setImageResource(R.drawable.ic_filled_heart);
                    isFavorite=true;
                    Log.d(TAG, "onClick: Added to favorites");
                }

            }
        });
    }

    private void checkIfFavorite() {
        //Traverse the whole favorites section of user and if a datasnapshot with itemId exits it
        //item is favorite
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(
                "user_data/" + FirebaseAuth.getInstance().getUid() + "favorites/");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String key = ds.getKey();
                    if(key.matches(item.getItem_id())){
                        isFavorite = true;
                    }
                }
                //  Log.d(TAG, "onDataChange: Item_Id: "+item.getItem_id());
                //  Log.d(TAG, "onDataChange: Snapshot Key: "+snapshot.getKey());

               // isFavorite = snapshot.getKey().matches(item.getItem_id());
                if (isFavorite) {
                    Log.d(TAG, "onDataChange: Favorite Item Spotted");
                }
                init();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void init() {

        //Setting Floating Add to Cart Button
        setAddToCartButton();

        TextView moreInTv = view.findViewById(R.id.moreInText);
        moreInTv.setText("More In "+ StringManipulation.capitalizeFirstLetter(item.getCategory_id()));
        //Initializing Widgets

        //Handle null description here
        if (item.getCategory_id().matches("rolls")) {
            description = getActivity().getString(R.string.default_food_description);
        }
        mProductNameTv.setText(item.getItem_name());
        mProductDescriptionTv.setText(description);
        mRatingTv.setText(Float.toString(item.getItem_ratings())+" Star Ratings");
        mRatingBar.setRating(item.getItem_ratings());
     //   mResNameAndAddress.setText(item.getRestaurant_name().concat(",").concat(" " + item.getRestaurant_address()));
        mPriceTv.setText("₹" + item.getPrice());
        //Loading image
//        if (item.getItem_photo() != null) {
//            Glide.with((Fragment) this)
//                    .load(item.getItem_photo())
//                    .into(mProductImage);
//
//            Glide.with((Fragment) this)
//                    .load(item.getItem_photo())
//                    .into(mBackground);
//        } else {
//            Log.d(TAG, "init: Product Image is null " + mProductImage);
//        }
        //Setting Quantity on TextView
        mQuantityTv.setText(Integer.toString(count));
        if (isFavorite) {
            mAddToFavoritesButton.setImageResource(R.drawable.ic_filled_heart);
            Log.d(TAG, "init: Favorite Item");
        } else {
            mAddToFavoritesButton.setImageResource(R.drawable.ic_add_to_fav);
        }

      //  fetchSimilarItems();


    }

    private void setAddToCartButton() {

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

        Log.d(TAG, "setAddToCartButton: Width: "+width);
        Log.d(TAG, "setAddToCartButton: Height: "+height);

            mAddToCartButton.setY(height/2);

    }

    private void fetchSimilarItems(){
        similarItems = new ArrayList<>();

        //Database Connection
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child("food_items")
                .orderByChild("category_id")
                .equalTo(item.getCategory_id());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds:snapshot.getChildren()){
                        Log.d(TAG, "onDataChange: Same Category Items found "+ds.toString());
                        similarItems.add(ds.getValue(FoodItem.class));
                    }
                    
                    initRecyclerViews();
                }else{
                    Log.d(TAG, "onDataChange: No items in same category");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void bindWidgets() {
        mContext = getActivity();

        mProductNameTv = view.findViewById(R.id.productNameTv);
        mProductDescriptionTv = view.findViewById(R.id.descriptionTv);
        mQuantityTv = view.findViewById(R.id.quantity);
        mRatingTv = view.findViewById(R.id.ratingsTv);
        mPriceTv = view.findViewById(R.id.priceTv);
        mRatingBar = view.findViewById(R.id.ratingBar);

        mProductImage = view.findViewById(R.id.productImage);

        mBackButton = view.findViewById(R.id.back_btn);
        mAddButton = view.findViewById(R.id.addQuantityBtn);
        mAddToFavoritesButton = view.findViewById(R.id.addToFavouritesBtn);
        mDeleteButton = view.findViewById(R.id.removeQuantityBtn);
        mAddToCartButton = view.findViewById(R.id.buttonHolder);
        bottomNavigationView = view.findViewById(R.id.bottomNavViewBar);


    }

    private void initRecyclerViews(){
        //Fetching all same category items from Database
        Log.d(TAG, "initRecyclerViews: Initializing Similar Fooods Rv: "+similarItems.size());
        RecyclerView recyclerView = view.findViewById(R.id.similarItemsRv);
        FoodItemAdapter adapter = new FoodItemAdapter(similarItems,getActivity(),this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //Adding all the items to the List
        //Creating and setting adapter to the arraylist
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, favoritesFragment).commit();
                return true;
            case R.id.options:
                OptionsFragment optionsFragment = new OptionsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, optionsFragment).commit();
                Log.d(TAG, "onNavigationItemSelected: Options Fragment Tapped");
                return true;
        }
        return false;
    }

    @Override
    public void onFoodClicked(int position) {
        FoodItem item = similarItems.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("FOOD_ITEM", item);

        //Removing the Previous Fragment from backstack
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();

        FinalProductDescriptionFragment fragment = new FinalProductDescriptionFragment();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack("Product Description");
        transaction.replace(R.id.container,fragment);
        transaction.commit();
    }
}
