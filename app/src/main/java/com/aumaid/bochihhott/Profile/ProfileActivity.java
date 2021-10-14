
package com.aumaid.bochihhott.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.NullChecker;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    /**
     * Declaring Widgets*/
    private RelativeLayout profileBtn;
    private RelativeLayout addressesBtn;
    private RelativeLayout ordersBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_options_layout);

        Log.d(TAG, "onCreate: Profile Activity Launched");


        ArrayList<View> views = new ArrayList<>();
        views.add(profileBtn);
        views.add(addressesBtn);
        views.add(ordersBtn);

        Log.d(TAG, "onCreate: Null Views: "+NullChecker.checkViews(views));



    }

    private void setButtonListeners(){
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));

            }
        });

        addressesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: addressed button tapped");
            }
        });
    }

    private void bindWidgets(){
        profileBtn = findViewById(R.id.profileLayout);
        addressesBtn = findViewById(R.id.addressesLayout);
        ordersBtn = findViewById(R.id.ordersLayout);
    }
}