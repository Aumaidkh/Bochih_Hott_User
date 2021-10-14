package com.aumaid.bochihhott.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aumaid.bochihhott.Profile.BottomSheetProfile;
import com.aumaid.bochihhott.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NewOptionsFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "NewOptionsFragment";


    private BottomNavigationView bottomNavigationView;

    private View view;

    private RelativeLayout mProfileBtn;
    private RelativeLayout mAddressesBtn;
    private RelativeLayout mOrdersBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Options Fragment Created");
        view = inflater.inflate(R.layout.fragment_options_layout, container,false);
       // bottomNavigationView = view.findViewById(R.id.bottomNavViewBar);
      //  bottomNavigationView.setSelectedItemId(R.id.options);
      //  bottomNavigationView.setOnNavigationItemSelectedListener(this);

        initFragment();
        return view;
    }

    private void initFragment(){
        BottomSheetProfile fragment = new BottomSheetProfile();
        fragment.show(getActivity().getSupportFragmentManager(),"Profile Bottom Sheet");
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,favoritesFragment).commit();
                return true;
            case R.id.options:
                NewOptionsFragment optionsFragment = new NewOptionsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, optionsFragment).commit();
                Log.d(TAG, "onNavigationItemSelected: Options Fragment Tapped");
                return true;
        }
        return false;
    }
}
