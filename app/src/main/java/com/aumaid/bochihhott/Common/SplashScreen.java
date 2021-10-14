package com.aumaid.bochihhott.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.aumaid.bochihhott.Home.HomepageActivity;
import com.aumaid.bochihhott.LogIn.LoginActivity;
import com.aumaid.bochihhott.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ImageView mLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Changing Statusbar color
        //Status bar color
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));

        setContentView(R.layout.activity_splash_screen);
        mLogo = findViewById(R.id.logo);
      //  animate();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(checkUser()){

                    startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                }else{
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                finish();
            }
        },2000);
    }

    /**
     * If user is logged in the below method returns true
     * else it returns false*/
    private boolean checkUser(){
        mAuth = FirebaseAuth.getInstance();
        return mAuth.getCurrentUser()!=null;
    }

    private void animate(){
        mLogo.animate().scaleXBy(0.5f).scaleYBy(0.5f).setDuration(1000);
        //mLogo.animate().scaleXBy(-0.1f).scaleYBy(-0.1f).setDuration(1000);
       // mLogo.animate().scaleXBy(0.5f).scaleYBy(0.5f).setDuration(1000);
     //   mLogo.animate().scaleXBy(1.5f).scaleYBy(1.5f).setDuration(500);
       // mLogo.animate().scaleXBy(1.0f).scaleYBy(1.0f).setDuration(500);

    }
}