package com.aumaid.bochihhott.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aumaid.bochihhott.Adapters.UniversalImageLoader;
import com.aumaid.bochihhott.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;

public class EditProfile extends AppCompatActivity {

    private static final String TAG = "EditProfile";

    private Context mContext;

    private TextView mFullName;
    private TextView mDateOfBirth;
    private TextView mPhone;
    private TextView mEmail;
    private ImageView mProfilePhoto;

    private String url = "https://firebasestorage.googleapis.com/v0/b/bochih-hott.appspot.com/o/photos%2Ffood_item_photos%2F-Mg_UmAC2aZ8rVtA8h99%2F1628421694334.jpg?alt=media&token=67b6000a-85c6-42fe-ad23-7da6c93b9562";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        bindViews();

        Glide.with(EditProfile.this)
                .load(url)
                .placeholder(R.drawable.ic_email)
                .dontAnimate()
                .into(mProfilePhoto);
      //  setProfileImage();

    }

    private void bindViews(){

        mFullName = findViewById(R.id.input_full_name);
        mDateOfBirth = findViewById(R.id.input_date_of_birth);
        mPhone = findViewById(R.id.input_phone);
        mEmail = findViewById(R.id.input_email);
        mProfilePhoto = findViewById(R.id.profilePhoto);

        mContext = EditProfile.this;

        findViewById(R.id.backArrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    /*
     * Setting Profile Image*/
    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile image.");
        String imgURL = "www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf";
        UniversalImageLoader.setImage(imgURL, mProfilePhoto, null, "https://");
    }

    /**
     * This method is attached as onclick listener to saveChanges btn
     * It gets all the values from the text fields, passes them to other method and finishes the activity*/
    public void saveChanges(View view){
        String fullName = mFullName.getText().toString().trim();
        String dateOfBirth = mDateOfBirth.getText().toString().trim();
        String phone = mPhone.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
//
//        commitChanges();
        Toast.makeText(this, "Changes Saved"+fullName+" "+dateOfBirth+" "+phone+" "+email, Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * This function will be used to commit changes to the database*/
    private void commitChanges(){

    }


}