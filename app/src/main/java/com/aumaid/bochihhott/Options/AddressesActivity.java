package com.aumaid.bochihhott.Options;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aumaid.bochihhott.Adapters.AddressSelectorAdapter;
import com.aumaid.bochihhott.CheckOut.AddressFragment;
import com.aumaid.bochihhott.CheckOut.PaymentFragment;
import com.aumaid.bochihhott.Interfaces.AddressClickListener;
import com.aumaid.bochihhott.Models.Address;
import com.aumaid.bochihhott.Models.Addresss;
import com.aumaid.bochihhott.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddressesActivity extends AppCompatActivity implements AddressClickListener {

    private static final String TAG = "AddressesActivity";

    /*Declaring Firebase Stuff*/
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;

    /*Declaring Views*/
    private ImageView mBackBtn;
    private ImageView mAddAddressBtn;
    private TextView mAddAddressTxtBtn;
    private RelativeLayout mScreen;

    /*Address Recycler View section*/
    private ArrayList<Addresss> addresses;
    private RecyclerView mAddressRv;
    private AddressSelectorAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);
        bindViews();

        init();
    }

    private void init(){
        //back button functionality
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Navigating the user to Address Fragment using mAddAddressBtn and mAddAddressTxtBtn
        mAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddressFragment();
            }
        });

        mAddAddressTxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddressFragment();
            }
        });

        showAddresses();
    }

    private void bindViews(){

        mBackBtn = findViewById(R.id.back_btn);
        mAddAddressBtn = findViewById(R.id.addAddressBtn);
        mAddAddressTxtBtn = findViewById(R.id.addAddressTv);
        mBackBtn = findViewById(R.id.back_btn);
        mAddressRv = findViewById(R.id.addressPickerRecyclerView);
        mScreen = findViewById(R.id.addressSelectorScreen);

    }

    private void showAddresses(){

        addresses = new ArrayList<>();

        adapter = new AddressSelectorAdapter(addresses, this);
        mAddressRv.setLayoutManager(new LinearLayoutManager(this));
        mAddressRv.setAdapter(adapter);

        //Load Addresses
        loadAddresses();

    }

    private void loadAddresses() {

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("user_data/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/addresses");

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addresses.clear();
                if (snapshot.exists()) {
                    Addresss address = new Addresss();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: Datasnapshot: " + ds.toString());
                        address = ds.getValue(Addresss.class);
                        addresses.add(address);
                    }

                    adapter.notifyDataSetChanged();
//                    mProgressBar.setVisibility(View.GONE);
//                    mCartButtons.setVisibility(View.VISIBLE);
//                    mTotalSection.setVisibility(View.VISIBLE);

                } else {
                    Log.d(TAG, "onDataChange: No items inside the cart");
                    //Present an illustration showing there are no items inside the cart
//                    mProgressBar.setVisibility(View.GONE);
//                    noItemsInsideCart();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addAddressFragment(){
        mScreen.setVisibility(View.GONE);
        Bundle bundle = new Bundle();
      //  bundle.putString("GRAND_TOTAL",grand_total);
        AddressFragment fragment = new AddressFragment();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack("Address Fragment");
        transaction.commit();

    }

    @Override
    public void onAddressClicked(int position) {
        //DO nothing on address clicked
    }
}