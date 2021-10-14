package com.aumaid.bochihhott.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionInflater;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aumaid.bochihhott.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class HomepageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "HomepageActivity";
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Log.d(TAG, "onCreate: Home Activity Created");

        init();

       /* bottomNavigationView = findViewById(R.id.bottomNavViewBar);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
        moveDownBottomNavigationBar();*/
    }

    private void init(){
        Log.d(TAG, "init: Loading Home Fragment");
        FinalHomeFragment homeFragment = new FinalHomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
      //  Log.d(TAG, "onNavigationItemSelected: Home Fragment Tapped");
    }

    private void moveDownBottomNavigationBar(){
        KeyboardVisibilityEvent.setEventListener(
                HomepageActivity.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // some code depending on keyboard visiblity status
                        if(isOpen){
                            bottomNavigationView.setVisibility(View.GONE);
                        }else{
                            bottomNavigationView.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }



    /**
     * function to go back to previous fragment
     */
    private void oneStepBack() {
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() >= 2) {
            fragmentManager.popBackStackImmediate();
            fts.commit();
        } else {
            doubleClickToExit();
        }
    }

    // double back pressed function
    private static long back_pressed;

    private void doubleClickToExit() {
        if ((back_pressed + 2000) > System.currentTimeMillis())
            finish();
        else
            Toast.makeText(HomepageActivity.this, "Click again to exit", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

    @Override
    public void onBackPressed() {
        oneStepBack();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                HomeFragment homeFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                Log.d(TAG, "onNavigationItemSelected: Home Fragment Tapped");
                return true;
            case R.id.cart:
                CartFragment cartFragment = new CartFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, cartFragment).commit();
                Log.d(TAG, "onNavigationItemSelected: Cart Fragment Tapped");

                return true;
            case R.id.favorites:
                FavoritesFragment favoritesFragment = new FavoritesFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,favoritesFragment).commit();
                return true;
            case R.id.options:
                OptionsFragment optionsFragment = new OptionsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, optionsFragment).commit();
                Log.d(TAG, "onNavigationItemSelected: Options Fragment Tapped");
                return true;
        }
        return false;
    }
}