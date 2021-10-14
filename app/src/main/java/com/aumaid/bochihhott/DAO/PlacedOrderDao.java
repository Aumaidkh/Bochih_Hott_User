package com.aumaid.bochihhott.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.aumaid.bochihhott.Models.Order;
import com.aumaid.bochihhott.Models.OrderModel;
import com.aumaid.bochihhott.Models.PlacedOrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PlacedOrderDao {

    private static final String TAG = "PlacedOrderDao";

    private String order_id;
    private String order_status;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private Context mContext;

    public PlacedOrderDao(Context mContext) {
        this.mContext = mContext;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        order_id = myRef.child("orders").push().getKey();
        order_status = "processing";
    }

    public String placeOrder(Order order){

        order.setPlaced_order_id(order_id);
        myRef.child("pending_orders")
                .child(order_id)
                .setValue(order);

        Log.d(TAG, "placeOrder: ArrayList Length: after placing order "+order.getItems().size());

        for(int i=0; i<order.getRes_ids().size();i++){
            //save order in every restaurants pending orders section
            myRef.child("restaurants")
                    .child(order.getRes_ids().get(i))
                    .child("pending_orders")
                    .child(order_id)
                    .setValue(order);

            Log.d(TAG, "placeOrder: Saved "+i+"th order in database");
        }

        Log.d(TAG, "placeOrder: Saving order in User Data");
        myRef.child("user_data")
                .child(FirebaseAuth.getInstance().getUid())
                .child("orders")
                .child(order_id)
                .setValue(order);

        /*myRef.child("restaurants")
                .child(order.getRestaurant_id())
                .child("pending_orders")
                .child(order_id)
                .setValue(order);*/

        //TODO: Add code for the delivered orders here



       // Log.d(TAG, "placeOrder: Please wait while your order gets confirmed");

        return order_id;

    }

    public void addToPlacedOrders(PlacedOrderModel order){
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

    public void clearPendingOrder(PlacedOrderModel order){
        myRef = mFirebaseDatabase.getReference("pending_orders");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Deleting Nodes");
                for(DataSnapshot ds :dataSnapshot.getChildren()){

                    ds.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

    public ArrayList<String> resIdFcmTokensDairy(Order order){
       //Grab Restaurant ID's of all the food items
        ArrayList<String> res_ids = new ArrayList<>();

        for(int i=0; i<order.getItems().size(); i++){

            if(res_ids.contains(order.getItems().get(i).getRestaurant_id())){
                res_ids.add(order.getItems().get(i).getRestaurant_id());
            }
        }

        return res_ids;

    }


}
