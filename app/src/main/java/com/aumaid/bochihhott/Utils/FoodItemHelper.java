package com.aumaid.bochihhott.Utils;

import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.Models.PopularFood;

import java.util.ArrayList;

public class FoodItemHelper {
    
    public ArrayList<FoodItem> popularFoodItemsToFoodItems(ArrayList<PopularFood> foodItems){
        ArrayList<FoodItem> items = new ArrayList<>();

       /* for(int i = 0; i < foodItems.size(); i++){
            items.add(new FoodItem(
                    foodItems.get(i).getItem_id(),
                    foodItems.get(i).isActive(),
                    foodItems.get(i).getCategory_id(),
                    foodItems.get(i).getDescription(),
                    foodItems.get(i).getItem_name(),
                    foodItems.get(i).getItem_photo(),
                    foodItems.get(i).getItem_ratings(),
                    foodItems.get(i).getPrice(),
                    foodItems.get(i).getRestaurant_name(),
                    foodItems.get(i).getRestaurant_address(),
                    foodItems.get(i).getRestaurant_id()
            ));
        }*/

        return null;
    }
    
}
