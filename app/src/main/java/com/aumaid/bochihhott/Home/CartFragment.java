package com.aumaid.bochihhott.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Adapters.CartItemAdapter;
import com.aumaid.bochihhott.Adapters.SwipeToDeleteCallBack;
import com.aumaid.bochihhott.CheckOut.CheckOutActivity;
import com.aumaid.bochihhott.DAO.OrderDao;
import com.aumaid.bochihhott.Interfaces.RecyclerViewListener;
import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.Models.Order;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.StringManipulation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener, RecyclerViewListener {

    private static final String TAG = "CartFragment";
    private View view;

    private BottomNavigationView bottomNavigationView;

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
    private TextView mSubtotalText;
    private TextView mSubtotal;
    private TextView mTaxAndFeeText;
    private TextView mTaxAndFeeTv;
    private CardView mCardPromo;

    private Button mPlaceOrderBtn;
    private ProgressBar mProgressBar;
    private RelativeLayout mEmptyCart;
    private RelativeLayout mCartButtons;
    private RelativeLayout mTotalSection;

    private int delivery = 0;
    private int total_price = 0;
    private int grand_total = 0;
    private int subtotal = 0;
    private int taxAndFees = 0;


    /**
     * Declaring Views or Widgets
     */
    private RecyclerView mCartRecyclerview;
    private CartItemAdapter adapter;
    private RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Cart Fragment Created");
        //Status bar color
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        view = inflater.inflate(R.layout.activity_cart,container,false);
        bottomNavigationView = view.findViewById(R.id.bottomNavViewBar);
        bottomNavigationView.setSelectedItemId(R.id.cart);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        /**Binding Widgets*/
        bindViews();
        initButtons();
        showOrdersList();
       // enableSwipeToDeleteAndUndo();

        return view;
    }

    private void bindViews() {
        mAuth = FirebaseAuth.getInstance();
        mCartRecyclerview = view.findViewById(R.id.cartRecyclerView);
        mContext = getActivity();
        mDelivery = view.findViewById(R.id.deliveryFeeTv);
        mTotal = view.findViewById(R.id.totalTv);
        mPlaceOrderBtn = view.findViewById(R.id.placeOrderBtn);
        mProgressBar = view.findViewById(R.id.progressBar);
        mDeliveryText = view.findViewById(R.id.deliveryText);
        mTotalText = view.findViewById(R.id.totalText);
        mSubtotalText = view.findViewById(R.id.subtotalText);
        mSubtotal = view.findViewById(R.id.subtotalTv);
        mTaxAndFeeText = view.findViewById(R.id.textAndFeeText);
        mTaxAndFeeTv = view.findViewById(R.id.taxAndFeesTv);
        mTotalText = view.findViewById(R.id.totalText);
        mEmptyCart = view.findViewById(R.id.emptyCartRel);
        mCartButtons = view.findViewById(R.id.cartButtons);
        mTotalSection = view.findViewById(R.id.totalSection);

        mCardPromo = view.findViewById(R.id.promoCard);

        mProgressBar.setVisibility(View.VISIBLE);
        mCartButtons.setVisibility(View.GONE);
        mTotalSection.setVisibility(View.GONE);

        relativeLayout = view.findViewById(R.id.relLayout1);
        bottomNavigationView = view.findViewById(R.id.bottomNavViewBar);
    }

    private void showOrdersList() {
        Log.d(TAG, "showOrdersList: Showing Orders List");
        items = new ArrayList<>();

        adapter = new CartItemAdapter(items, mContext,this);
        mCartRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCartRecyclerview.setAdapter(adapter);

        //Load Cart items
        //loadCartItems();
        Log.d(TAG, "loadCartItems: Loading Cart Items");

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("user_data/" + mAuth.getCurrentUser().getUid() + "/cart");

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
                    //Calculating Total
                    for (int i = 0; i < items.size(); i++) {
                        FoodItem item_ = items.get(i);
                        subtotal += Integer.parseInt(item_.getPrice()) * item_.getQuantity();
                        // Log.d(TAG, "onDataChange: Total Price: " + total_price + " Quantity: " + item_.getQuantity());
                    }
                    grand_total = (subtotal + delivery+ taxAndFees);
//                    Log.d(TAG, "onDataChange: Quantity : " + item.getQuantity());
//                    Log.d(TAG, "onDataChange: Delivery : " + delivery);
//                    Log.d(TAG, "onDataChange: Grand Total: " + grand_total);
                    initTextViews();
                    mProgressBar.setVisibility(View.GONE);
                    mCartButtons.setVisibility(View.VISIBLE);
                    mTotalSection.setVisibility(View.VISIBLE);

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


    private void initTextViews() {
        Log.d(TAG, "initTextViews: Grand_Total: " + grand_total);
        if (delivery == 0) {
            mDelivery.setText("Free");
        } else {
            mDelivery.setText("₹ " +Integer.toString(delivery)+".00");
        }
        mSubtotal.setText("₹ " +subtotal+".00");
        mTaxAndFeeTv.setText("₹ " +taxAndFees+".00");
        mTotal.setText("₹ " + Integer.toString(grand_total)+".00");
    }

    private void initButtons() {

        mPlaceOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Attempting to place an order");
                Intent intent = new Intent(mContext, CheckOutActivity.class);
                intent.putExtra("GRAND_TOTAL", Integer.toString(grand_total));
                //Make an Array Order Model
                Order order = new Order();
                order.setCustomer_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
                order.setItems(items);
                //Grab all Restaurant Ids and store them in array list
                ArrayList<String> resIDs = StringManipulation.getRestaurantIds(items);
                order.setRes_ids(resIDs);
                order.setPrice(total_price);
                items.clear();
                intent.putExtra("ORDER",order);
                startActivity(intent);

            }
        });





    }

    private void noItemsInsideCart() {

        //Hiding content on the screen
        mTotalText.setVisibility(View.GONE);
        mTotal.setVisibility(View.GONE);
        mDelivery.setVisibility(View.GONE);
        mDeliveryText.setVisibility(View.GONE);
        mSubtotalText.setVisibility(View.GONE);
        mSubtotal.setVisibility(View.GONE);
        mTaxAndFeeText.setVisibility(View.GONE);
        mTaxAndFeeTv.setVisibility(View.GONE);
        mPlaceOrderBtn.setVisibility(View.GONE);
        mCardPromo.setVisibility(View.GONE);

        mEmptyCart.setVisibility(View.VISIBLE);

    }

    private void removeCartItem(FoodItem item) {
        Log.d(TAG, "removeCartItem: Removing the cart item from the database");
        String item_id = item.getItem_id();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("user_data")
                .child(mAuth.getCurrentUser().getUid())
                .child("cart");

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Deleting Nodes");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (ds.getValue(FoodItem.class).getItem_id().matches(item_id)) {
                        ds.getRef().removeValue();
                        items.remove(item);
                        adapter.notifyDataSetChanged();
                        if (items.isEmpty()) {
                            noItemsInsideCart();
                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallBack swipeToDeleteCallback = new SwipeToDeleteCallBack(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final FoodItem item = adapter.getData().get(position);

                adapter.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "Item was removed from the cart.", Snackbar.LENGTH_LONG);
                //Removing the item from the database
                removeCartItem(item);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adapter.restoreItem(item, position);
                        mCartRecyclerview.scrollToPosition(position);

                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(mCartRecyclerview);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
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

    @Override
    public void onViewClicked(View v, int position) {
        int quantity = items.get(position).getQuantity();
        if(v.getId()==R.id.addQuantityBtn){
            //Add Quantity Here
            ++quantity;
            //update quantity for the food item
            items.get(position).setQuantity(quantity);
            //notify adapter
            adapter.notifyDataSetChanged();
            Log.d(TAG, "onViewClicked: Add Quantity Tapped: "+quantity);
        }
        if(v.getId()==R.id.removeQuantityBtn){
            if(quantity<=1){
                removeCartItem(items.get(position));
                return;
            }
            //Remove Quantity Here
            --quantity;
            //update quantity for the food item
            items.get(position).setQuantity(quantity);
            //notify adapter
            adapter.notifyDataSetChanged();
            Log.d(TAG, "onViewClicked: Remove Quantity Tapped: "+quantity);
        }

        //updating price tv
        int total = Integer.parseInt(items.get(position).getPrice())*items.get(position).getQuantity();
        mTotal.setText(total+delivery+taxAndFees+".00");
        mSubtotal.setText("₹ "+Integer.toString(total)+".00");
       // String.format(items.get(position).getPrice(),Integer.class);
    }
}
