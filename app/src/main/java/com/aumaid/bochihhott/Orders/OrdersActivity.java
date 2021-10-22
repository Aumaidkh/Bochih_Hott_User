package com.aumaid.bochihhott.Orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aumaid.bochihhott.Adapters.OrderAdapter;
import com.aumaid.bochihhott.Addresses.AddNewAddressFragment;
import com.aumaid.bochihhott.DAO.User;
import com.aumaid.bochihhott.Interfaces.RecyclerViewListener;
import com.aumaid.bochihhott.Models.Order;
import com.aumaid.bochihhott.Models.Partner;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ReviewsAndRatings.Models.Rating;
import com.aumaid.bochihhott.ReviewsAndRatings.RatingsHelper;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity implements RecyclerViewListener {

    private static final String TAG = "OrdersActivity";

    private ArrayList<Order> orders;

    private RecyclerView mOrdersRecyclerView;
    private User user;
    private Partner partner;
    private Float ratings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        setContentView(R.layout.fragment_my_orders_layout);
        receiveDataFromPreviousIntent();
        bindWidgets();
        config();

    }

    private void fetchData(String res_id){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("restaurants_data");
        myRef.child(res_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    partner = snapshot.getValue(Partner.class);
                    fetchRatings();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void fetchRatings(){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("ratings/"+partner.getRestaurant_id());
       // myRef.child(partner.getRestaurant_id());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ratings = RatingsHelper.totalRatings(snapshot);
                partner.setRatings(ratings);
                orderRatingsFragment();
                Log.d(TAG, "onDataChange: Total Ratings :"+ratings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void orderRatingsFragment(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("USER",user);
        bundle.putSerializable("PARTNER",partner);

        OrderReviewAndRatingFragment fragment = new OrderReviewAndRatingFragment();
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(),"Dialog Fragment");
    }

    private void receiveDataFromPreviousIntent(){
        user =(User) getIntent().getSerializableExtra("USER");
        //Setting This profile pic now
        ImageView mProfilePhotoButton = findViewById(R.id.profilePic);
        Glide.with(this)
                .load(user.getProfile_photo())
                .placeholder(R.drawable.ic_outline_person_24)
                .into(mProfilePhotoButton);
        Log.d(TAG, "receiveDataFromPreviousIntent: Profile Pic Set");
    }

    private void config(){
        //Assigning back button functionality
        CardView mBackButton = findViewById(R.id.back_btn);
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
                if(orders.size()>0){
                    loadOrders();
                }else{
                    //Show no orders illustration here
                    RelativeLayout noOrdersLayout = findViewById(R.id.noOrders);
                    noOrdersLayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadOrders(){
       // OrderAdapter adapter = new OrderAdapter(orders,this);
        OrderAdapter adapter = new OrderAdapter(orders,this,this);
        mOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOrdersRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void bindWidgets(){
        mOrdersRecyclerView = findViewById(R.id.myOrdersRv);
    }

    @Override
    public void onViewClicked(View v, int position) {
        if(v.getId()==R.id.rateButton){
            Log.d(TAG, "onViewClicked: Inflating Rate Fragment");

            //Attach other data here...
            /////
            if(user!=null){
                String res_id = orders.get(position).getRes_ids().get(0);
                fetchData(res_id);
                Log.d(TAG, "onClick: Getting order from the bundle...");
                Log.d(TAG, "onClick: User Has to add new address");
            }
        }
        if(v.getId()==R.id.reorderButton){
            Log.d(TAG, "onViewClicked: Attempting to re-order the item...");
        }
    }

    //pass the user when rate button is clicked
    //getIntent.getSeri..("USER")
}