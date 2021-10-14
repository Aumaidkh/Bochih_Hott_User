package com.aumaid.bochihhott.Utils;

import com.aumaid.bochihhott.Models.FoodItem;

import java.util.ArrayList;

public class StringManipulation {

    public static String expandUsername(String username){
        return username.replace("."," ");
    }

    public static String condenseUsername(String username){
        return username.replace(" ",".");
    }

    public static String capitalizeFirstLetter(String word){


        // create two substrings from name
        // first substring contains first letter of name
        // second substring contains remaining letters
        String firstLetter = word.substring(0, 1);
        String remainingLetters = word.substring(1, word.length());

        // change the first letter to uppercase
        firstLetter = firstLetter.toUpperCase();

        // join the two substrings
        word = firstLetter + remainingLetters;
       return word;

    }

    public static String extractTime(String timestamp){
        String time = timestamp.substring(11,16);
        return time;
    }

    public static ArrayList<String> getRestaurantIds(ArrayList<FoodItem> foodItems){

        ArrayList<String> resIds = new ArrayList<>();

        for(int i=0; i<foodItems.size(); i++){
            String res_id = foodItems.get(i).getRestaurant_id();

            resIds.add(res_id);

        }

        return resIds;

    }
}
