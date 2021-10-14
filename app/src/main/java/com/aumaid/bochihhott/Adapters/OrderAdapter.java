package com.aumaid.bochihhott.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Models.Order;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ViewHolders.OrderViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    private static final String TAG = "OrderAdapter";

    private ArrayList<Order> orders;
    private Context mContext;

    public OrderAdapter(ArrayList<Order> orders, Context mContext) {
        this.orders = orders;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Creating Order ViewHolder");
        View view = LayoutInflater.from(mContext).inflate(R.layout.sample_order_layout,parent,false);
        OrderViewHolder viewHolder = new OrderViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Binding View Holder Widgets");
        Order order = orders.get(position);

        //Setting Order Image --> Photo will be set of the first item inside the order
        Glide.with(mContext)
                .load(order.getItems().get(0).getItem_photo())
                .into(holder.getmOrderImage());

        //Setting other text values
        //Setting Restaurant names separated by ,
        String restaurant_name = "";
        String restaurant_address = "";
        String items = "";
        for(int i=0; i<order.getItems().size(); i++){
            if(i<order.getItems().size()-1){
                items += order.getItems().get(i).getQuantity()+" x "+order.getItems().get(i).getItem_name().concat(",");
                restaurant_name += order.getItems().get(i).getRestaurant_name().concat(",");
                restaurant_address += order.getItems().get(i).getRestaurant_address().concat(",");
            }else {
                items += order.getItems().get(i).getQuantity()+" x "+order.getItems().get(i).getItem_name().concat(".");
                restaurant_name = order.getItems().get(i).getRestaurant_name().concat(".");
                restaurant_address = order.getItems().get(i).getRestaurant_address().concat(".");
            }
        }

        Log.d(TAG, "onBindViewHolder: Previewing restaurant name: "+restaurant_name);
        Log.d(TAG, "onBindViewHolder: Previewing restaurant address: "+restaurant_address);
        Log.d(TAG, "onBindViewHolder: Previewing Items: "+items);
        holder.getmRestaurantName().setText(restaurant_name);
        holder.getmRestaurantAddress().setText(restaurant_address);
        holder.getmPrice().setText("₹ "+Float.toString(order.getPrice()));
        holder.getmItems().setText(items);
        holder.getmTimeStamp().setText(order.getTimestamp());

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
