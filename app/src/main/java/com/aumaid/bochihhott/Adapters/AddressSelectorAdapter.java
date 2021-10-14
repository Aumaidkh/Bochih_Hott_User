package com.aumaid.bochihhott.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.AddressClickListener;
import com.aumaid.bochihhott.Models.Address;
import com.aumaid.bochihhott.Models.Addresss;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.AddressHelper;
import com.aumaid.bochihhott.ViewHolders.AddressViewHolder;

import java.util.ArrayList;

public class AddressSelectorAdapter extends RecyclerView.Adapter<AddressViewHolder> {

    private static final String TAG = "AddressSelectorAdapter";

    private ArrayList<Addresss> addresses;
    private AddressClickListener addressClickListener;

    public AddressSelectorAdapter(ArrayList<Addresss> addresses, AddressClickListener addressClickListener) {
        this.addresses = addresses;
        this.addressClickListener = addressClickListener;
    }

    public AddressSelectorAdapter(ArrayList<Addresss> addresses){
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_address_layout,parent,false);
        AddressViewHolder holder = new AddressViewHolder(view,addressClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Addresss address = addresses.get(position);
        holder.getmFullName().setText(address.getFull_name());
        holder.getmPhoneNumber().setText("+91"+address.getPhone_number());
        String full_address = AddressHelper.generateFullAddress(address);
      //  holder.getmAddressLine1().setText(address.getAddress());
        holder.getmLandMark().setText(full_address);
        Log.d(TAG, "onBindViewHolder: FUll Address: "+full_address);
      //  holder.getmCityAndVillage().setText(address.getCity());
      //  holder.getmPinCode().setText(address.getPin_code());

    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }
}
