package com.aumaid.bochihhott.DAO;

import android.content.Context;
import android.util.Log;

import com.aumaid.bochihhott.Models.Address;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddressDao {

    private static final String TAG = "AddressDao";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private Context mContext;

    public AddressDao(Context mContext) {
        this.mContext = mContext;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

    }

    public void saveAddress(Address address){

        Log.d(TAG, "saveAddress: Attempting to save address to the database");

        String key = myRef.child("addresses")
                .child(mAuth.getCurrentUser().getUid()).push().getKey();

        address.setAddress_id(key);

        myRef.child("user_data")
                .child(mAuth.getCurrentUser().getUid())
                .child("addresses")
                .child(key)
                .setValue(address);

        myRef.child("addresses")
                .child(mAuth.getCurrentUser().getUid())
                .child(key)
                .setValue(address);

        Log.d(TAG, "saveAddress: Address saved to the database with key :"+key);

    }




}
