/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.DAO;

import android.content.Context;
import android.util.Log;

import com.aumaid.bochihhott.ReviewsAndRatings.Models.Rating;
import com.aumaid.bochihhott.ReviewsAndRatings.Models.ReviewModel;
import com.aumaid.bochihhott.Utils.TimeHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewDao {

    private static final String TAG = "ReviewDao";

    private Context mContext;
    private DatabaseReference reviewsRef;
    private String user_id;

    public ReviewDao(Context mContext){
        this.mContext = mContext;
        this.user_id = FirebaseAuth.getInstance().getUid();
        this.reviewsRef = FirebaseDatabase.getInstance().getReference();
    }

    public void saveNewReview(ReviewModel reviewModel){
        Log.d(TAG, "saveNewReview: Attempting to save a review...");
        String review_id = reviewsRef.child("reviews"+user_id).push().getKey();
        reviewModel.setReview_id(review_id);
        Log.d(TAG, "saveNewReview: Review Saved...");

        reviewsRef.child("reviews").child(review_id).setValue(reviewModel);
    }

    public void saveNewReview(User user, String res_id, String body, float ratings){
        Log.d(TAG, "saveNewReview: Attempting to save a review...");
        String review_id = reviewsRef.child("reviews"+user_id).push().getKey();
        ReviewModel reviewModel
                = new ReviewModel(
                user.getProfile_photo(),
                res_id,
                TimeHelper.getTimeStamp(),
                body,
                user.getUsername()
        );
        //Saving Ratings
        DatabaseReference ratingsRef = FirebaseDatabase.getInstance().getReference("ratings");
        ratingsRef.child(res_id).child(FirebaseAuth.getInstance().getUid()).setValue(new Rating(FirebaseAuth.getInstance().getUid(),ratings));
        Log.d(TAG, "saveNewReview: Review Saved...");

        reviewsRef.child("reviews").child(review_id).setValue(reviewModel);
    }

}
