package com.aumaid.bochihhott.Home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Adapters.CartItemAdapter;
import com.aumaid.bochihhott.Adapters.FavoritesAdapter;
import com.aumaid.bochihhott.DAO.FoodItemDao;
import com.aumaid.bochihhott.Interfaces.RecyclerViewListener;
import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment implements RecyclerViewListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "FavoritesFragment";
    
    private View view;

    /**
     * Declaring Variables*/
    /**
     * Firebase Stuff
     */
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;

    private Context mContext;
    private ArrayList<FoodItem> items;

    private TextView mTotal;
    private TextView mDelivery;
    private TextView mDeliveryText;
    private TextView mTotalText;


    private Button mPlaceOrderBtn;
    private Button mContinueShoppingBtn;
    private ProgressBar mProgressBar;
    private RelativeLayout mEmptyCart;
    private RelativeLayout mCartButtons;
    private RelativeLayout mTotalSection;

    private int delivery = 0;
    private int total_price = 0;
    private int grand_total = 0;

    /**
     * Declaring Views or Widgets
     */
    private RecyclerView mFavoriteRecyclerview;
    private FavoritesAdapter adapter;
    private RelativeLayout relativeLayout;
    private BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Favorites Fragment Created");

        view = inflater.inflate(R.layout.fragment_favorites_layout,container,false);
        bottomNavigationView = view.findViewById(R.id.bottomNavViewBar);
        bottomNavigationView.setSelectedItemId(R.id.favorites);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bindViews();
        showOrdersList();
        return view;
    }


    private void showOrdersList() {
        Log.d(TAG, "showOrdersList: Showing Orders List");
        items = new ArrayList<>();

        adapter = new FavoritesAdapter(items, mContext, this);
        mFavoriteRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFavoriteRecyclerview.setAdapter(adapter);

        //Load Cart items
        //loadCartItems();
        Log.d(TAG, "loadCartItems: Loading Cart Items");

        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("user_data/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/favorites");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                if (snapshot.exists()) {
                    FoodItem item = new FoodItem();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        // Log.d(TAG, "onDataChange: Datasnapshot: " + ds.toString());
                        item = ds.getValue(FoodItem.class);
                        //   Log.d(TAG, "onDataChange: Item name: " + item.getItem().getItem_name());
                        items.add(item);
                    }

                    adapter.notifyDataSetChanged();
                    mProgressBar.setVisibility(View.GONE);
//                    mCartButtons.setVisibility(View.VISIBLE);
   //                 mTotalSection.setVisibility(View.VISIBLE);

                } else {
                    Log.d(TAG, "onDataChange: No items inside the cart");
                    //Present an illustration showing there are no items inside the cart
                    mProgressBar.setVisibility(View.GONE);
                    noItemsInsideCart();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private void bindViews() {
        mAuth = FirebaseAuth.getInstance();
        mFavoriteRecyclerview = view.findViewById(R.id.favRecyclerView);
        mContext = getActivity();
        mDelivery = view.findViewById(R.id.deliveryFeeTv);
        mTotal = view.findViewById(R.id.totalTv);
        mPlaceOrderBtn = view.findViewById(R.id.placeOrderBtn);
     //   mContinueShoppingBtn = view.findViewById(R.id.continueShoppingBtn);
        mProgressBar = view.findViewById(R.id.progressBar);
        mDeliveryText = view.findViewById(R.id.deliveryText);
        mTotalText = view.findViewById(R.id.totalText);
        mEmptyCart = view.findViewById(R.id.emptyCartRel);
     //   mCartButtons = view.findViewById(R.id.cartButtons);
     //   mTotalSection = view.findViewById(R.id.totalSection);

        mProgressBar.setVisibility(View.VISIBLE);
//        mCartButtons.setVisibility(View.GONE);
   //     mTotalSection.setVisibility(View.GONE);

        relativeLayout = view.findViewById(R.id.relLayout1);
        bottomNavigationView = view.findViewById(R.id.bottomNavViewBar);
    }


    private void noItemsInsideCart() {

        //Hiding content on the screen
//        mTotalText.setVisibility(View.GONE);
   //     mTotal.setVisibility(View.GONE);
    //    mDelivery.setVisibility(View.GONE);
    //    mDeliveryText.setVisibility(View.GONE);
        mPlaceOrderBtn.setVisibility(View.GONE);
     //   mContinueShoppingBtn.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();

        mEmptyCart.setVisibility(View.VISIBLE);

    }


    @Override
    public void onViewClicked(View v, int position) {
        String item_id = items.get(position).getItem_id();
        if(v.getId()==R.id.removeBtn){
            //Removing Items from the Favorites
            FoodItemDao foodItemDao = new FoodItemDao(getActivity());
            foodItemDao.removeFoodItem(item_id);
            Toast.makeText(getActivity(), "Removing Item",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                FinalHomeFragment homeFragment = new FinalHomeFragment();
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


    /**/
}
