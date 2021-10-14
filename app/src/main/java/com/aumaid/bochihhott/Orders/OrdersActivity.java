package com.aumaid.bochihhott.Orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.aumaid.bochihhott.Adapters.OrderAdapter;
import com.aumaid.bochihhott.Models.Order;
import com.aumaid.bochihhott.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    private static final String TAG = "OrdersActivity";

    private ArrayList<Order> orders;

    private RecyclerView mOrdersRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        setContentView(R.layout.activity_orders_layout);
        bindWidgets();
        config();

    }

    private void config(){
        //Assigning back button functionality
        ImageView mBackButton = findViewById(R.id.back_btn);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        orders = new ArrayList<Order>();
        Log.d(TAG, "config: Getting Orders list....");
        String user_id = FirebaseAuth.getInstance().getUid();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("user_data/"+user_id+"/orders");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orders.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    orders.add(ds.getValue(Order.class));
                }

                Log.d(TAG, "onDataChange: Total number of orders: "+orders.size());
                loadOrders();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadOrders(){
        OrderAdapter adapter = new OrderAdapter(orders,getApplicationContext());
        mOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOrdersRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void bindWidgets(){
        mOrdersRecyclerView = findViewById(R.id.myOrdersRv);
    }
}