package com.aumaid.bochihhott.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aumaid.bochihhott.Models.ProfileOption;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.FirebaseMethods;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ActivityProfile extends AppCompatActivity {

    private static final String TAG = "ActivityProfile";

    /**
     * Location Provider*/
    private FusedLocationProviderClient mFusedLocationClient;
    private final int PERMISSION_ID = 101;
    private double longitude;
    private double latitude;

   /**
     * Firebase Stuff*/
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;

    private Context mContext;

    RecyclerView mProfileOptionsRecyclerView;
    private ArrayList<ProfileOption> mPOptions;
    private TextView mFullName;
    private ImageView mProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  );
        getWindow().setStatusBarColor(getResources().getColor(R.color.background_status_bar_filter));
        setContentView(R.layout.final_product_description_fragment_layout);

        //This is async function
        //getUserLocation();

    }

    @SuppressLint("MissingPermission")
    private void getUserLocation(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //Checking permissions
        if(checkPermissions()){

            //Checking if location is enabled
            if(isLocationEnabled()){

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.d(TAG, "onCreate: Longitude: "+longitude+" ");
                            Log.d(TAG, "onCreate: Latitude: "+latitude+" ");

                        }
                    }
                });

            }
            //Sending the user to Enable Location Intent
            else{
                Toast.makeText(getApplicationContext(),"Please turn on location",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }

        }else{
            requestPermissions();
        }
    }

    /**
     * This method is used to get values of Longitude and latitude*/
    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){
        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            longitude = mLastLocation.getLongitude();
            latitude = mLastLocation.getLatitude();
            Log.d(TAG, "onCreate: Longitude: "+longitude+" ");
            Log.d(TAG, "onCreate: Latitude: "+latitude+" ");
        }
    };

    /**
     * This method is used to check if the location or GPS service is enabled*/
    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    /**
     * This method is used to check permissions */
    private boolean checkPermissions(){
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * This method is used to request permissions from the user*/
    private void requestPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // If everything is alright then
    /**
     * If permissions are already enabled*/
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getUserLocation();
        }
    }


}