package com.aumaid.bochihhott.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aumaid.bochihhott.DAO.User;
import com.aumaid.bochihhott.DAO.UserDao;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.TextFieldHelperClass;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity";

    /**
     * Firebase*/
    private DatabaseReference mDatabaseRef;

    /**
     * Declaring Widgets*/
    private ImageView backBtn;
    private FloatingActionButton saveChangesBtn;
    private RelativeLayout profilePhotoBtn;
    private ImageView mProfilePhoto;
    private EditText mUserName;
    private EditText mAbout;
    private TextView mEmail;
    private TextView mPhone;
    private ProgressBar mProgressBar;

    /**
     * Other Variables*/
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Status bar color
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(getResources().getColor(R.color.background_status_bar_filter));

        setContentView(R.layout.activity_edit_profile);

        bindWidgets();
        getUser(FirebaseAuth.getInstance().getUid());
        setButtonListeners();
    }

    private void setButtonListeners(){
        //Back Button Functionality
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //TODO: Save changes button


        //Select Profile Photo Button
        profilePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileChooser();
            }
        });

        //Saving Changes
        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateBasicDetailsFields(mUserName.getText().toString().trim(), mAbout.getText().toString().trim()))
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("users/"+FirebaseAuth.getInstance().getUid());
                UserDao dao = new UserDao(getApplicationContext());
                dao.updateProfile(mUserName.getText().toString().trim(), mAbout.getText().toString().trim(),imageUri, mProgressBar, EditProfileActivity.this);

            }
        });
    }

    /**
     * This method takes all the inputs from the edit text views and returns false if
     * any of the views is empty else it returns true*/
    private boolean validateBasicDetailsFields(String username, String about){

        if(TextFieldHelperClass.emptyField(username)){
            mUserName.setError("Full Name can't be empty");
            return false;
        }else if(TextFieldHelperClass.emptyField(about)){
            mAbout.setError("Phone Number can't be empty");
            return false;
        }else{
            return true;
        }

    }

    private void bindWidgets(){
        backBtn = findViewById(R.id.backArrow);
        saveChangesBtn = findViewById(R.id.saveChangesButton);
        mProfilePhoto = findViewById(R.id.profilePhoto);
        profilePhotoBtn = findViewById(R.id.profilePhotoSection);
        mUserName = findViewById(R.id.usernameTv);
        mEmail = findViewById(R.id.emailinput);
        mPhone = findViewById(R.id.phoneInput);
        mAbout = findViewById(R.id.aboutInput);
        mProgressBar = findViewById(R.id.progressBar);
        //7006271695
        //9018371539

    }

    private void getUser(String user_id){
       mDatabaseRef = FirebaseDatabase.getInstance().getReference("users/"+user_id);
       mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               User user = snapshot.getValue(User.class);
               initWidgets(user);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }

    private void initWidgets(User user){
        //Showing Progress Bar
        mProgressBar.setVisibility(View.VISIBLE);
        //Profile Pic
        String profile_photo = user.getProfile_photo();
        if(profile_photo!=null){
            Glide.with(getApplicationContext())
                    .load(profile_photo)
                    .into(mProfilePhoto);
        }
        //Username
        mUserName.setText(user.getUsername());
        //Email
        mEmail.setText(user.getEmail());
        //Phone
        mPhone.setText(user.getPhone_number());
        //Bio
        String description = user.getDescription();
        if(description!=null){
            //Log.d(TAG, "initWidgets: Description: "+description);
            mAbout.setText(description);
        }

        //Hiding Progress Bar
        mProgressBar.setVisibility(View.GONE);

    }

    private void fileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData() !=null){
            imageUri = data.getData();
            mProfilePhoto.setImageURI(imageUri);
        }
    }
}