package com.aumaid.bochihhott.Home;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aumaid.bochihhott.Addresses.AddressesParentActivity;
import com.aumaid.bochihhott.DAO.User;
import com.aumaid.bochihhott.DAO.UserDao;
import com.aumaid.bochihhott.LogIn.LoginActivity;
import com.aumaid.bochihhott.Orders.OrdersActivity;
import com.aumaid.bochihhott.Profile.EditProfileActivity;
import com.aumaid.bochihhott.R;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class OptionsFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "OptionsFragment";


    private BottomNavigationView bottomNavigationView;

    private View view;

    private RelativeLayout mProfileBtn;
    private RelativeLayout mAddressesBtn;
    private RelativeLayout mOrdersBtn;
    private RelativeLayout mLogoutBtn;

    private ImageView mProfilePhoto;

    /*This variable will be passed to the next activity so as to
    * prevent other database read operation*/
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Options Fragment Created");
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.background_status_bar_filter));
        view = inflater.inflate(R.layout.fragment_options_layout, container,false);
        bottomNavigationView = view.findViewById(R.id.bottomNavViewBar);
        bottomNavigationView.setSelectedItemId(R.id.options);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bindWidgets();

        initWidgets();

        setButtonListeners();
        return view;
    }

    private void initWidgets(){
        String user_id = FirebaseAuth.getInstance().getUid();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users/"+user_id);
       myRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               Log.d(TAG, "onDataChange: Setting profile photo");
               user = snapshot.getValue(User.class);
               Glide.with(getActivity())
                       .load(user.getProfile_photo())
                       .into(mProfilePhoto);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });


    }

    private void setButtonListeners(){
        mProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));

            }
        });

        mAddressesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: addressed button tapped");
                startActivity(new Intent(getActivity(), AddressesParentActivity.class));
            }
        });

        mOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: launching orders activity...");
                Intent intent = new Intent(getActivity(),OrdersActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }

    private void bindWidgets(){
        mProfileBtn = view.findViewById(R.id.profileLayout);
        mAddressesBtn = view.findViewById(R.id.addressesLayout);
        mOrdersBtn = view.findViewById(R.id.ordersLayout);
        mProfilePhoto = view.findViewById(R.id.profilePic);
        mLogoutBtn = view.findViewById(R.id.signOutModeLayout);
        bottomNavigationView = view.findViewById(R.id.bottomNavViewBar);
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
}
