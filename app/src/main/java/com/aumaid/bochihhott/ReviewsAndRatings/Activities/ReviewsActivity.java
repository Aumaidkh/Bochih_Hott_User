/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.ReviewsAndRatings.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aumaid.bochihhott.DAO.ReviewDao;
import com.aumaid.bochihhott.DAO.User;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ReviewsAndRatings.Adapters.ReviewsAdapter;
import com.aumaid.bochihhott.ReviewsAndRatings.Models.ReviewModel;
import com.aumaid.bochihhott.Utils.TextFieldHelperClass;
import com.aumaid.bochihhott.Utils.TimeHelper;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {

    private static final String TAG = "ReviewsActivity";
    private ArrayList<ReviewModel> reviews;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Setting Status Bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        initButtonListeners();
        fetchUser();
        fetchReviews();



    }

    private void fetchUser() {
        String user_id = FirebaseAuth.getInstance().getUid();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users/" + user_id);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    user = snapshot.getValue(User.class);
                    //  Log.d(TAG, "onDataChange: snapshot: "+snapshot.toString());
                    //  Log.d(TAG, "onDataChange: User: "+user.toString());
                    updateUi();
                    //  fetchReviews();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateUi() {
        //Setting Profile Pic
        ImageView mProfilePic = findViewById(R.id.profilePic);
        Glide.with(getApplicationContext())
                .load(user.getProfile_photo())
                .into(mProfilePic);
    }

    private void fetchReviews() {
        reviews = new ArrayList<>();
        //Making Database Connection
        String restaurant_id = getIntent().getStringExtra("RESTAURANT_ID");
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("reviews");

        Query query = myRef.orderByChild("res_id").equalTo(restaurant_id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reviews.clear();
                if (snapshot.exists()) {
                    Log.d(TAG, "onDataChange: reviews_snapshot" + snapshot.toString());
                    for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                        ReviewModel reviewModel = singleSnapshot.getValue(ReviewModel.class);
                        reviews.add(reviewModel);
                    }
                }

                showReviews();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


      /*  myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    //  Log.d(TAG, "onDataChange: Reviews "+ds.toString());
                    ReviewModel review = ds.getValue(ReviewModel.class);
                  //  Log.d(TAG, "onDataChange: DataSnapshot : "+ds.toString());
                 //   Log.d(TAG, "onDataChange: Review : "+review.toString());
                    reviews.add(review);
                }
                Log.d(TAG, "onDataChange: Added All Reviews");
                showReviews();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });*/
    }

    private void showReviews() {
        RecyclerView mReviewsRecycler = findViewById(R.id.reviewsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ReviewsAdapter adapter = new ReviewsAdapter(reviews, getApplicationContext());
        mReviewsRecycler.setLayoutManager(linearLayoutManager);
        mReviewsRecycler.setAdapter(adapter);
    }

    private void initButtonListeners(){
        CardView mBackButton = findViewById(R.id.back_btn);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        EditText mReview = findViewById(R.id.reviewInput);
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            Log.d(TAG, "onKeyDown: Enter Pressed");
            String review = mReview.getText().toString().trim();
            if (review != null) {
                if (review.length() < 100) {
                    ReviewModel reviewModel = new ReviewModel(
                            user.getProfile_photo(),
                            getIntent().getStringExtra("RESTAURANT_ID"),
                            TimeHelper.getTimeStamp(),
                            review,
                            user.getUsername()
                    );

                    //Saving Review To the Database
                    ReviewDao reviewDao = new ReviewDao(getApplicationContext());
                    reviewDao.saveNewReview(reviewModel);

                    //Clearing the Edit Text field
                    mReview.setText("");
                    //Clearing Focus from Edit text
                    mReview.clearFocus();
                    //Hiding Keyboard
                    TextFieldHelperClass.hideKeyboard(ReviewsActivity.this);

                }
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "Review body can't be greater than 100 characters.", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);

    }
}