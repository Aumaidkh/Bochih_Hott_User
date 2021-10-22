package com.aumaid.bochihhott.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.RecyclerViewListener;
import com.aumaid.bochihhott.Models.Order;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.StringManipulation;
import com.aumaid.bochihhott.ViewHolders.OrderViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    private static final String TAG = "OrderAdapter";

    private ArrayList<Order> orders;
    private Context mContext;
    private RecyclerViewListener mListener;

    public OrderAdapter(ArrayList<Order> orders, Context mContext,RecyclerViewListener mListener) {
        this.orders = orders;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Creating Order ViewHolder");
        View view = LayoutInflater.from(mContext).inflate(R.layout.todo_sample_order_layout, parent, false);
        OrderViewHolder viewHolder = new OrderViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Binding View Holder Widgets");
        Order order = orders.get(position);

        try {
            if (order != null) {
                Glide.with(mContext)
                        .load(order.getItems().get(0).getItem_photo())
                        .into(holder.getmOrderImage());
                //Setting Restaurant Name
                String restaurant_name = "";
                for(int i=0; i<order.getItems().size(); i++){
                    if(i<order.getItems().size()-1){
                       // items += order.getItems().get(i).getQuantity()+" x "+order.getItems().get(i).getItem_name().concat(",");
                        restaurant_name += order.getItems().get(i).getRestaurant_name().concat(",");
                        // restaurant_address += order.getItems().get(i).getRestaurant_address().concat(",");
                    }else {
                      //  items += order.getItems().get(i).getQuantity()+" x "+order.getItems().get(i).getItem_name().concat(".");
                        restaurant_name = order.getItems().get(i).getRestaurant_name().concat(".");
                        //  restaurant_address = order.getItems().get(i).getRestaurant_address().concat(".");
                    }
                }
                holder.getmRestaurantName().setText(restaurant_name);

                //Setting Time Stamp
                holder.getmTimeStamp().setText(StringManipulation.extractDate(order.getTimestamp()));

                //Setting Order Price
                holder.getmPrice().setText("₹ "+order.getPrice());

                //Setting Quantity
                holder.getmQuantity().setText(order.getItems().size()+" items");

                //Setting Order Status
                //TODO: Currently this is static
                holder.getmOrderStatus().setText("Order Delivered");
            }
        } catch (NullPointerException e) {
            Log.d(TAG, "onBindViewHolder: Exception at order Number: " + position);
        }


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

}
