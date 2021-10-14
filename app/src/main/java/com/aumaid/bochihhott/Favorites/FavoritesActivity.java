package com.aumaid.bochihhott.Favorites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.aumaid.bochihhott.R;

public class FavoritesActivity extends AppCompatActivity {

    private static final String TAG = "FavoritesActivity";
    private static final int ACTIVITY_NUMBER = 2;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favorites_layout);

        mContext = FavoritesActivity.this;
   //     setupBottomNavigationView();

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
  //  }
}