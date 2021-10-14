package com.aumaid.bochihhott.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Adapters.BottomSheetAdapter;
import com.aumaid.bochihhott.CheckOut.CheckOutActivity;
import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.Models.Order;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.StringManipulation;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private static final String TAG = "BottomSheetFragment";
    private View view;
    private ArrayList<FoodItem> items;
    private BottomSheetAdapter adapter;
    private RecyclerView recyclerView;
    private TextView mTotalText;
    private Float total;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_bottom_sheet_layout,container,false);
        initSheet();
        addItems();
        return view;
    }

    private void addItems(){

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("user_data")
                .child(FirebaseAuth.getInstance().getUid())
                .child("cart");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                total = 0f;
                for(DataSnapshot ds: snapshot.getChildren()){
                    FoodItem item = ds.getValue(FoodItem.class);
                    items.add(item);
                    total+=Float.parseFloat(item.getPrice())*item.getQuantity();
                }

                adapter.notifyDataSetChanged();
                mTotalText.setText("₹ "+Float.toString(total));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initSheet(){
        items = new ArrayList<>();
        recyclerView = view.findViewById(R.id.bottomSheetRv);
        adapter = new BottomSheetAdapter(items,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        mTotalText = view.findViewById(R.id.totalTv);

        Button mPlaceOrderButton = view.findViewById(R.id.placeOrderBtn);

        mPlaceOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckOutActivity.class);
                //Create a new Order and pass it to checkout activity
                Order order = new Order();
                order.setCustomer_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
                order.setItems(items);
                //Grab all Restaurant Ids and store them in arraylist
                ArrayList<String> resIDs = StringManipulation.getRestaurantIds(items);
                order.setRes_ids(resIDs);
                order.setPrice(total);

                intent.putExtra("ORDER",order);
                startActivity(intent);

            }
        });
    }
}
