package com.aumaid.bochihhott.CheckOut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aumaid.bochihhott.Models.Order;
import com.aumaid.bochihhott.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CheckOutActivity extends AppCompatActivity {

    private static final String TAG = "CheckOutActivity";
    private static final int ACTIVITY_NUMBER = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        //Status bar color
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        init();

       // setupBottomNavigationView();

    }

    /**
     * This method is used to load address fragment in the cart activity*/
    private void init(){
        Log.d(TAG, "init: Inflating Profile Fragment");


        String grand_total = getIntent().getStringExtra("GRAND_TOTAL");
        Log.d(TAG, "init: Grand Total in CheckOutActivity: "+grand_total);
        Order order = (Order) getIntent().getSerializableExtra("ORDER");


        Bundle bundle = new Bundle();
        bundle.putString("GRAND_TOTAL",grand_total);
        bundle.putSerializable("ORDER",order);


        AddressSelectorFragment1 fragment = new AddressSelectorFragment1();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = CheckOutActivity  .this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack("Address Selector Fragment");
        transaction.commit();

    }

//    /**
//     * This method is used for the functioning of
//     * Bottom Navigation bar*/
//    private void setupBottomNavigationView(){
//        Log.d(TAG, "setupBottomNavigationView: setting up bottomNavigationView");
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavViewBar);
//        /*
//         * disabling animations in navbar
//         * */
//        BottomNavigationViewHelper.setUpBottomNavigationView(bottomNavigationView);
//        /*
//         * setting on create listener for
//         * every icon
//         * */
//        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationView);
//        /*
//         * highlighting the proper menu icon
//         * while switching acivities
//         * */
//        Menu menu = bottomNavigationView.getMenu();
//        MenuItem menuItem = menu.getItem(ACTIVITY_NUMBER);
//        menuItem.setChecked(true);
//    }
}