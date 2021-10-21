package com.aumaid.bochihhott.Restaurant;

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
import com.aumaid.bochihhott.FinalAdapters.MenuAdapter;
import com.aumaid.bochihhott.Interfaces.RecyclerViewItemClickListener;
import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.Models.MenuItem;
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

public class BottomMenuFragment extends BottomSheetDialogFragment implements RecyclerViewItemClickListener {

    private static final String TAG = "BottomSheetFragment";
    private View view;
    private ArrayList<MenuItem> items;
    private MenuAdapter adapter;
    private RecyclerView recyclerView;
    private TextView mTotalText;
    private Float total;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_menu_layout,container,false);
        initSheet();
        addItems();
        return view;
    }

    private void addItems(){


        if(getArguments()==null){
            return;
        }
        String _res_id = getArguments().getString("RESTAURANT_ID");

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("menus")
                .child(_res_id);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    MenuItem item = ds.getValue(MenuItem.class);
                    items.add(item);

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initSheet(){
        items = new ArrayList<>();
        recyclerView = view.findViewById(R.id.menuRecyclerView);
        adapter = new MenuAdapter(items,getActivity(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onViewClicked(int position) {
        MenuItem item = items.get(position);
        String category_name = item.getCategory_name();

        Intent intent = new Intent(getActivity(),ResultsActivity.class);
        intent.putExtra("CATEGORY",category_name);
        intent.putExtra("RESTAURANT_ID",getArguments().getString("RESTAURANT_ID"));
        startActivity(intent);

    }
}
