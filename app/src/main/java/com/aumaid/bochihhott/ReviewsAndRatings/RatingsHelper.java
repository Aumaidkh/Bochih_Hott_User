/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.ReviewsAndRatings;

import com.aumaid.bochihhott.ReviewsAndRatings.Models.Rating;
import com.google.firebase.database.DataSnapshot;

public class RatingsHelper {

    public static float totalRatings(DataSnapshot snapshot){
        float ratings = 0;
        float total_ratings;
        for(DataSnapshot ds: snapshot.getChildren()){
            Rating rating = ds.getValue(Rating.class);
            ratings += rating.getRating();

        }
        total_ratings = ratings/snapshot.getChildrenCount();

        return total_ratings;

    }
}
