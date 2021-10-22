/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.Orders;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aumaid.bochihhott.DAO.ReviewDao;
import com.aumaid.bochihhott.DAO.User;
import com.aumaid.bochihhott.Models.Partner;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ReviewsAndRatings.Models.ReviewModel;
import com.aumaid.bochihhott.Utils.TimeHelper;
import com.bumptech.glide.Glide;

import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

public class OrderReviewAndRatingFragment extends SupportBlurDialogFragment {

    private static final String TAG = "OrderReviewAndRatingFra";

    private View view;
    private ImageView image;
    private TextView reviewMessage;
    private RatingBar ratingBar;
    private EditText review;
    private Button submitButton;

    private Partner partner;
    private User user;
    //Needs a restaurant id as well for making a review to it

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.review_input_layout, container, false);
        //Setting up fragment snippet
        //  Order order = (Order) getArguments().getSerializable("ORDER");
        // Log.d(TAG, "onCreateView: SHowing Order: "+order.toString());
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpFragment();
        bindWidgets();
        initWidgets();
        initButtonListeners();
    }

    /**
     * This method resizes the fragment to fit into the
     * parent fragment */
    private void setUpFragment(){
        Log.d(TAG, "setUpFragment: Setting up fraGMENT...");
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics metrics = getResources().getDisplayMetrics();

            int width = metrics.widthPixels;
            int height = metrics.heightPixels-650;
                    Log.d(TAG, "setUpFragment: height:"+height);
            dialog.getWindow().setLayout((6 * width)/6, (6 * height)/5);

        }else{
            Log.d(TAG, "setUpFragment: DIalog is null...");
        }

    }

    private void bindWidgets(){
        image = view.findViewById(R.id.image);
        reviewMessage = view.findViewById(R.id.reviewMessage);
        ratingBar = view.findViewById(R.id.ratingBar);
        review = view.findViewById(R.id.reviewInput);
        submitButton = view.findViewById(R.id.submitButton);
    }

    private void initWidgets(){

        partner = (Partner) getArguments().getSerializable("PARTNER");
        if(partner!=null){
            //Setting Image;
            Glide.with(getActivity())
                    .load(partner.getPhoto())
                    .into(image);

            Log.d(TAG, "initWidgets: Image Set Successfully");
            //Setting Ratings
            ratingBar.setRating(partner.getRatings());
            setReviewMessage(partner.getRatings());
        }

        user = (User) getArguments().getSerializable("USER");
    }

    private void initButtonListeners(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Make Validation of the Review here
                String reviewBody = review.getText().toString().trim();
                if(isValid(reviewBody)){
                    float ratings = ratingBar.getRating();
                    ReviewDao reviewDao = new ReviewDao(getActivity());
                    reviewDao.saveNewReview(user,partner.getRestaurant_id(),reviewBody,ratings);
                    getDialog().dismiss();
                }
            }
        });

    }

    /**
     * Perform Validation Here*/
    private boolean isValid(String review){
        return true;
    }

    private void setReviewMessage(float ratings){
        String message = "";
        if(ratings>5.0){
            return;
        }
        if(ratings>4.0){
            message = "Good";
        }else if(ratings<4.0&&ratings>3.0){
            message = "Satisfactory";
        }else if(ratings<3.0&&ratings>2.0){
            message = "Somewhat Satisfied";
        }else if(ratings<2.0){
            message = "Bad";
        }
        reviewMessage.setText(message);
    }


}
