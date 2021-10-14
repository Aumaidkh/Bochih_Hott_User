package com.aumaid.bochihhott.Home;

import android.content.Context;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.DAO.CartDao;
import com.aumaid.bochihhott.R;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductDescriptionFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "ProductDescriptionFragment";

    private BottomNavigationView bottomNavigationView;

    /**
     * Declaring widgets
     */
    private TextView mProductNameTv;
    private TextView mProductDescriptionTv;
    private TextView mQuantityTv;
    private TextView mRatingTv;
    private TextView mResNameAndAddress;
    private TextView mPriceTv;

    private ImageView mProductImage;
    private ImageView mBackButton;
    private ImageView mAddToFavoritesButton;
    private ImageView mBackground;

    private RatingBar mRatingBar;

    private Button mAddButton;
    private Button mDeleteButton;
    private Button mAddToCartButton;
    private Button mGoToCartButton;
    private Button mContinueShoppingButton;

    private RelativeLayout mPlate;


    /**
     * Other vars
     */
    private View view;
    private int count = 1;
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
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.background_status_bar_filter));
        view = inflater.inflate(R.layout.fragment_product_description, container, false);
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
        Log.d(TAG, "onCreateView: Food Item: " + item);
        Glide.with(getActivity())
                .load(item.getItem_photo())
                .into(mProductImage);

        Glide.with(getActivity())
                .load(item.getItem_photo())
                .into(mBackground);

        checkIfFavorite();
        //Animate Product Image
        mProductImage.animate().rotationBy(-340f).translationXBy(-800).setDuration(800);
        mPlate.animate().translationXBy(-1000).setDuration(800);
        attachButtonListeners();

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
                mContinueShoppingButton.setVisibility(View.VISIBLE);
                mAddToCartButton.setVisibility(View.GONE);
                mGoToCartButton.setVisibility(View.VISIBLE);
                //Add Food Item To the Cart

            }
        });

        //Continue shopping btn
        mContinueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment fragment = new HomeFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
        });

        //Go To cart btn
        mGoToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fix the problem here
                CartFragment fragment = new CartFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack("Profile Fragment");
                transaction.commit();
            }
        });

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

        //Initializing Widgets
        if (item.getDescription() == null || item.getDescription().isEmpty()) {
            description = "Made with ---- at " + rname + " " + raddress + " delivered with love by Bochih Hott";
        }
        mProductNameTv.setText(item.getItem_name());
        mProductDescriptionTv.setText(description);
        mRatingTv.setText(Float.toString(item.getItem_ratings()));
        mRatingBar.setRating(item.getItem_ratings());
        mResNameAndAddress.setText(item.getRestaurant_name().concat(",").concat(" " + item.getRestaurant_address()));
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
            mAddToFavoritesButton.setImageResource(R.drawable.heart);
        }


    }

    private void bindWidgets() {
        mContext = getActivity();

        mProductNameTv = view.findViewById(R.id.productNameTv);
        mProductDescriptionTv = view.findViewById(R.id.productDescriptionTv);
        mQuantityTv = view.findViewById(R.id.quantity);
        mRatingTv = view.findViewById(R.id.ratingTv);
        mPriceTv = view.findViewById(R.id.priceTv);
        mResNameAndAddress = view.findViewById(R.id.restaurantNameTv);
        mPlate = view.findViewById(R.id.plate);

        mBackground = view.findViewById(R.id.frame);
        mProductImage = view.findViewById(R.id.productImage);

        mBackButton = view.findViewById(R.id.back_btn);
        mAddButton = view.findViewById(R.id.addBtn);
        mDeleteButton = view.findViewById(R.id.deleteBtn);
        mAddToCartButton = view.findViewById(R.id.addToCartBtn);
        mAddToFavoritesButton = view.findViewById(R.id.addToFavouritesBtn);
        mContinueShoppingButton = view.findViewById(R.id.continueShoppingBtn);
        mGoToCartButton = view.findViewById(R.id.goToCartBtn);

        mRatingBar = view.findViewById(R.id.foodItemRating);
        bottomNavigationView = view.findViewById(R.id.bottomNavViewBar);

        //Plotting product image
        mProductImage.setTranslationX(800);
        mPlate.setTranslationX(750);

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
}
