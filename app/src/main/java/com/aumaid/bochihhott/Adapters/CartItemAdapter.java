package com.aumaid.bochihhott.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.RecyclerViewListener;
import com.aumaid.bochihhott.Models.CartItem;
import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ViewHolders.CartViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private static final String TAG = "CartItemAdapter";


    private ArrayList<FoodItem> cartItems;
    private Context mContext;
    private RecyclerViewListener mListener;

    public CartItemAdapter(ArrayList<FoodItem> cartItems, Context mContext, RecyclerViewListener mListener) {
        this.cartItems = cartItems;
        this.mContext = mContext;
        this.mListener = mListener;

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sample_new_cart_layout,parent,false);
        CartViewHolder viewHolder = new CartViewHolder(view,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        FoodItem item = cartItems.get(position);
       // Log.d(TAG, "onBindViewHolder: Cart_item: "+item.getItem().toString());
        holder.getmFoodName().setText(item.getItem_name());
        //String item_info = Integer.toString(item.getQuantity())+" X "+item.getItem_name();
        String price = "₹ "+Integer.toString(Integer.parseInt(item.getPrice()) * item.getQuantity());
        String restaurant_info = item.getRestaurant_name().concat(", ").concat(item.getRestaurant_address());
        holder.getmPrice().setText(price);
        holder.getmRestaurantInfo().setText(restaurant_info);
        //Setting image
        Glide.with(mContext)
                .load(item.getItem_photo())
                .into(holder.getmItemPhoto());

        holder.getmItemRating().setText(Float.toString(item.getItem_ratings()));
        if(item.getQuantity()<10){
            holder.getmQuantity().setText("0"+Integer.toString(item.getQuantity()));
        }else {
            holder.getmQuantity().setText(Integer.toString(item.getQuantity()));
        }

        //Check favorites code her to toggle heart visibility

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void removeItem(int position) {
        cartItems.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(FoodItem item, int position) {
        cartItems.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<FoodItem> getData() {
        return cartItems;
    }


}
