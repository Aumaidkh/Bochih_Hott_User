package com.aumaid.bochihhott.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.Models.Order;
import com.aumaid.bochihhott.Models.OrderModel;
import com.aumaid.bochihhott.Models.PlacedOrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderDao {

    private static final String TAG = "PlacedOrderDao";

    private String order_id;
    private String order_status;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private Context mContext;

    public OrderDao(Context mContext) {
        this.mContext = mContext;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        order_id = myRef.child("orders").push().getKey();
        order_status = "processing";
    }

    public HashMap<String, String> placeOrder(ArrayList<FoodItem> foodItems) {

        HashMap<String, String> map = new HashMap<>();

        //Filter all the orders from the same restaurant
        for (int i = 0; i < foodItems.size(); i++) {
            FoodItem item = foodItems.get(i);
            String item_id = item.getItem_id();
            String res_id = item.getRestaurant_id();
            map.put(item_id,res_id);
        }

        return map;
    }


    public void addToPlacedOrders(PlacedOrderModel order) {
        order.setStatus("confirm");
        order.setPlaced_order_id(order_id);
        Log.d(TAG, "addToPlacedOrders: Order Placed Successfully");
        myRef.child("placed_orders")
                .child(order_id)
                .setValue(order);

        //Saving Users and their Orders in UserData
        myRef.child("user_data")
                .child(mAuth.getCurrentUser().getUid())
                .child("orders")
                .child(order_id)
                .setValue(order);

        Log.d(TAG, "placeOrder: Please wait while your order gets confirmed");

    }

    public void clearPendingOrder(PlacedOrderModel order) {
        myRef = mFirebaseDatabase.getReference("pending_orders");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Deleting Nodes");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    ds.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }


}
