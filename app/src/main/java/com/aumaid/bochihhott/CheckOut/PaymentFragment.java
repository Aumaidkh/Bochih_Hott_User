package com.aumaid.bochihhott.CheckOut;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aumaid.bochihhott.DAO.AddressDao;
import com.aumaid.bochihhott.DAO.PlacedOrder;
import com.aumaid.bochihhott.DAO.PlacedOrderDao;
import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.Models.Order;
import com.aumaid.bochihhott.Models.PlacedOrderModel;
import com.aumaid.bochihhott.Models.Address;
import com.aumaid.bochihhott.Models.CartItem_;
import com.aumaid.bochihhott.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PaymentFragment extends Fragment {

    private static final String TAG = "PaymentFragment";


    /**
     * Declaring widgets
     */
    private View view;
    private Button mConfirmBtn;
    private ImageView mBackBtn;
    private TextView mTotalPriceTv;

    /**
     * Declaring variables
     */
    private Float total=0f;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_payment_layout, container, false);
        // Log.d(TAG, "onCreateView: Inflating payment fragment layout");
        receiveDataFromPreviousIntent();
        init();
        //  Log.d(TAG, "init: Array size: "+items.size());
        return view;
    }

    private void init() {
        //Showing Price on the TextView
        TextView mPriceTv = view.findViewById(R.id.totalTv);
        mPriceTv.setText("₹ "+total);

        //  Log.d(TAG, "init: Initializing Fragment");
        mConfirmBtn = view.findViewById(R.id.confirmBtn);
        mBackBtn = view.findViewById(R.id.back_btn);

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Updating Changes to the Order");
                Order order = (Order) getArguments().getSerializable("ORDER");
                order.setPrice(total);

                Log.d(TAG, "onClick: Attaching order to the bundle");
                Bundle bundle = new Bundle();
                bundle.putSerializable("ORDER", order);

                Log.d(TAG, "onClick: Attaching bundle to the fragment...");
                SuccessFragment fragment = new SuccessFragment();
                if(order==null){
                    Log.d(TAG, "onClick: Order is null");
                }
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack("Success Fragment Fragment");
                transaction.commit();
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }

    private void receiveDataFromPreviousIntent() {
        Bundle bundle = this.getArguments();
        Order order = (Order) bundle.getSerializable("ORDER");
        Log.d(TAG, "receiveDataFromPreviousIntent: Calculating Total");
        for(int i=0; i<order.getItems().size(); i++){
            Log.d(TAG, "receiveDataFromPreviousIntent: Items: "+order.getItems().get(i).toString()+"/n");
            FoodItem item = order.getItems().get(i);
            total = total + Float.parseFloat(item.getPrice()) * item.getQuantity();
        }
        Log.d(TAG, "receiveDataFromPreviousIntent: Total Amount: "+total);
        Log.d(TAG, "onCreateView: Price :"+total);
       // Log.d(TAG, "receiveDataFromPreviousIntent: calling fragment name "+fragment);
    }
}
