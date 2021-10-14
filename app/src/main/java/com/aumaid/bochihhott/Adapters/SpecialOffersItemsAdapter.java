package com.aumaid.bochihhott.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Models.SpecialOfferedItem;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ViewHolders.SpecialOffersItemViewHolder;

import java.util.ArrayList;

public class SpecialOffersItemsAdapter extends RecyclerView.Adapter<SpecialOffersItemViewHolder> {

    private ArrayList<SpecialOfferedItem> mSpecialOfferedItems;

    public SpecialOffersItemsAdapter(ArrayList<SpecialOfferedItem> mSpecialOfferedItems) {
        this.mSpecialOfferedItems = mSpecialOfferedItems;
    }

    @NonNull
    @Override
    public SpecialOffersItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_special_offers_item_layout,parent,false);
        SpecialOffersItemViewHolder viewHolder = new SpecialOffersItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialOffersItemViewHolder holder, int position) {

        SpecialOfferedItem item = mSpecialOfferedItems.get(position);

        holder.getmSOImage().setImageResource(item.getmSIImage());
        holder.getmSOOffer().setText(item.getmSIOffer());

    }

    @Override
    public int getItemCount() {
        return mSpecialOfferedItems.size();
    }

    /**
     * This method is used to add special Offered item in the list
     * @param item*/
    public void addSOItem(SpecialOfferedItem item){
        mSpecialOfferedItems.add(item);
    }
}
