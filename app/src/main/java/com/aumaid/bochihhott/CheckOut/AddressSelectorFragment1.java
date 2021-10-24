package com.aumaid.bochihhott.CheckOut;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Adapters.AddressSelectorAdapter;
import com.aumaid.bochihhott.Addresses.AddAddressFragment;
import com.aumaid.bochihhott.Addresses.AddNewAddressFragment;
import com.aumaid.bochihhott.Interfaces.AddressClickListener;
import com.aumaid.bochihhott.Models.Addresss;
import com.aumaid.bochihhott.Models.Order;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.AddressHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

public class AddressSelectorFragment1 extends Fragment implements AddressClickListener {

    private static final String TAG = "AddressSelectorFragment";

    /*Declaring Firebase Stuff*/
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;

    /*Declaring Views*/
    private View view;
    private CardView mBackBtn;
    private Button mAddAddressBtn;
  //  private TextView mAddAddressTxtBtn;

    /*Address Recycler View section*/
    private ArrayList<Addresss> addresses;
    private RecyclerView mAddressRv;
    private AddressSelectorAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        view = inflater.inflate(R.layout.fragment_address_picker_layout,container,false);

        bindWidgets();
        initButtons();
        loadAddresses();
        //Check if the bundle is attached for making a decision
        //whether the user came from addresses activity of product description fragment
        if (getArguments() != null) {
            //User Came from Product Description Fragment
            Log.d(TAG, "onCreateView: User Came From Product Description Fragment");

        } else {
            //User Came from My Addresses Activity
            Log.d(TAG, "onCreateView: User Came From My Addresses Activity");

        }

        return view;
    }

    private void bindWidgets() {

        mBackBtn = view.findViewById(R.id.back_btn);
        mAddAddressBtn = view.findViewById(R.id.addAddressBtn);
     //   mAddAddressTxtBtn = view.findViewById(R.id.addAddressTv);
        mBackBtn = view.findViewById(R.id.back_btn);
         mAddressRv = view.findViewById(R.id.addressPickerRecyclerView);

    }

    private void initButtons() {
        //back button functionality
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        //Navigating the user to Address Fragment using mAddAddressBtn
        mAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Showing Add Address Fragment...");
                Bundle bundle = getArguments();
                if(bundle!=null){
                    Log.d(TAG, "onClick: Getting order from the bundle...");
                    Order order = (Order) bundle.getSerializable("ORDER");
                    bundle.putSerializable("ORDER",order);
                    Log.d(TAG, "onClick: User Has to add new address");
                }

                AddNewAddressFragment fragment = new AddNewAddressFragment();
                fragment.setArguments(bundle);
                fragment.show(getActivity().getSupportFragmentManager(),"Dialog Fragment");

            }
        });

    }

    private void loadAddresses() {
        
        addresses = new ArrayList<>();

        adapter = new AddressSelectorAdapter(addresses, this);
        mAddressRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAddressRv.setAdapter(adapter);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("user_data/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/addresses");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
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

    @Override
    public void onAddressClicked(int position) {
        Log.d(TAG, "onAddressClicked: Redirecting to next fragment");
        Bundle bundle = getArguments();
        if(bundle!=null){
            Log.d(TAG, "onAddressClicked: Redirecting to Payment Fragment");
            //Getting Address
            Addresss address = addresses.get(position);
            //Retrieving Incomplete Order attributes
            Order order =(Order) bundle.getSerializable("ORDER");
            //Generating Full Address And Attaching it to the Bundle
            String full_address = AddressHelper.generateFullAddress(address);
            order.setCustomer_name(address.getFull_name());
            order.setContact_number(address.getPhone_number());
            order.setDelivery_address(full_address);
            order.setDelivery_address_id(address.getAddress_id());
            bundle.putSerializable("ORDER", order);

            //Navigating the User to the Payment Fragment
            PaymentFragment fragment = new PaymentFragment();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack("Payment Fragment");
            transaction.commit();

        }else{
            Log.d(TAG, "onAddressClicked: Showing Full Address Details");
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("ADDRESS",addresses.get(position));
            AddNewAddressFragment fragment = new AddNewAddressFragment();
            fragment.setArguments(bundle1);
            fragment.show(getActivity().getSupportFragmentManager(),"Address Details Dialog Fragment");

          //  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        }
    }
}
