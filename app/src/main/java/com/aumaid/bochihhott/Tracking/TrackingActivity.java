package com.aumaid.bochihhott.Tracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.aumaid.bochihhott.R;

public class TrackingActivity extends AppCompatActivity {

    private static final String TAG = "TrackingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        init();
    }

    private void init(){
        Log.d(TAG, "init: Inflating Profile Fragment");

        TrackingFragment fragment = new TrackingFragment();
        FragmentTransaction transaction = TrackingActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container,fragment);
        transaction.commit();

    }
}