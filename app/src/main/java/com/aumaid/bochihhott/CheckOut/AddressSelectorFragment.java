package com.aumaid.bochihhott.CheckOut;

import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Adapters.AddressSelectorAdapter;
import com.aumaid.bochihhott.Adapters.CartItemAdapter;
import com.aumaid.bochihhott.Addresses.AddNewAddressFragment;
import com.aumaid.bochihhott.Interfaces.AddressClickListener;
import com.aumaid.bochihhott.Models.Address;
import com.aumaid.bochihhott.Models.Addresss;
import com.aumaid.bochihhott.Models.CartItem_;
import com.aumaid.bochihhott.Models.Order;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.AddressHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

public class AddressSelectorFragment extends Fragment implements AddressClickListener {

    private static final String TAG = "AddressSelectorFragment";
    private final String fragment = "AddressSelectorFragment";

    /*Declaring Firebase Stuff*/
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;

    /*Declaring Views*/
    private View view;
    private ImageView mBackBtn;
    private ImageView mAddAddressBtn;
    private TextView mAddAddressTxtBtn;

    /*Address Recycler View section*/
    private ArrayList<Addresss> addresses;
    private RecyclerView mAddressRv;
    private AddressSelectorAdapter adapter;

    /**
     * Declaring variables
     */
    private String addressId;
    private String addressLine1;
    private String landmark;
    private String village;
    private String city;
    private String pincode;
    private String grand_total;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_address_picker_layout,container,false);
        if(getArguments().get("GRAND_TOTAL")!=null){
            grand_total = getArguments().getString("GRAND_TOTAL");
        }
        

        bindViews();

        init();



        return view;
    }

    private void init(){
        //back button functionality
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
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

        mBackBtn = view.findViewById(R.id.back_btn);
        mAddAddressBtn = view.findViewById(R.id.addAddressBtn);
        mAddAddressTxtBtn = view.findViewById(R.id.addAddressTv);
        mBackBtn = view.findViewById(R.id.back_btn);
        mAddressRv = view.findViewById(R.id.addressPickerRecyclerView);

    }

    private void showAddresses(){

        addresses = new ArrayList<>();

        adapter = new AddressSelectorAdapter(addresses, this);
        mAddressRv.setLayoutManager(new LinearLayoutManager(getContext()));
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
                        //Log.d(TAG, "onDataChange: Datasnapshot: " + ds.toString());
                        address = ds.getValue(Addresss.class);
                        addresses.add(address);
                    }

                    adapter.notifyDataSetChanged();
//                    mProgressBar.setVisibility(View.GONE);
//                    mCartButtons.setVisibility(View.VISIBLE);
//                    mTotalSection.setVisibility(View.VISIBLE);

                } else {
                    //Log.d(TAG, "onDataChange: No items inside the cart");
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
        Bundle bundle = new Bundle();

        if (grand_total==null){
            Log.d(TAG, "addAddressFragment: Grand Total is Empty");
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            AddNewAddressFragment addNewAddressFragment = new AddNewAddressFragment();
            addNewAddressFragment.show(transaction, "tag");


//            RelativeLayout mainScreen = view.findViewById(R.id.mainScreen);
//            AddNewAddressFragment fragment = new AddNewAddressFragment();
//            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.add_address_container,fragment);
//            transaction.addToBackStack("Address Fragment");
//            transaction.commit();
//            // Dim the background
//// Where mainFrameLayout is as FrameLayout
//            mainScreen.getForeground().setAlpha(220);
        }else{
            Log.d(TAG, "addAddressFragment: Grand Total is not empty");
            bundle.putString("GRAND_TOTAL",grand_total);
            AddressFragment fragment = new AddressFragment();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container,fragment);
            transaction.addToBackStack("Address Fragment");
            transaction.commit();
        }



    }

    @Override
    public void onAddressClicked(int position) {
        Addresss address = addresses.get(position);
        //Log.d(TAG, "onAddressClicked: Address:"+address.toString());
        Log.d(TAG, "onAddressClicked: Grand Total: "+grand_total);
        if(grand_total!=null){
            //Switch to payment Fragment and pass this data to it
            //Binding data with the bundle
            Bundle bundle = new Bundle();
            bundle.putSerializable("ADDRESS",address);
            bundle.putString("GRAND_TOTAL",grand_total);

            //Order Stuff
            Order order =(Order) bundle.getSerializable("ORDER");

            bundle.putSerializable("ORDER",order);
            String full_address = AddressHelper.generateFullAddress(address);
            Log.d(TAG, "onAddressClicked: Full Address: "+full_address);
            PaymentFragment fragment = new PaymentFragment();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack("Payment Fragment");
            transaction.commit();
        }else {
            //Edit the selected address
            Bundle bundle = new Bundle();
            bundle.putSerializable("ADDRESS",address);
            Order order =(Order) bundle.getSerializable("ORDER");

            bundle.putSerializable("ORDER",order);
            String full_address = AddressHelper.generateFullAddress(address);
            Log.d(TAG, "onAddressClicked: Full Address: "+full_address);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            AddNewAddressFragment addNewAddressFragment = new AddNewAddressFragment();
            addNewAddressFragment.setArguments(bundle);
            addNewAddressFragment.show(transaction, "tag");
        }

    }
}
