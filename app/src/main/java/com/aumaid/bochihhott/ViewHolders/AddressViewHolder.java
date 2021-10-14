package com.aumaid.bochihhott.ViewHolders;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.AddressClickListener;
import com.aumaid.bochihhott.R;

public class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mFullName;
    private TextView mPhoneNumber;
    private TextView mLandMark;
    private ImageView mActiveAddress;

    private AddressClickListener addressClickListener;

    public TextView getmFullName() {
        return mFullName;
    }

    public TextView getmPhoneNumber() {
        return mPhoneNumber;
    }

    public AddressViewHolder(@NonNull View itemView, AddressClickListener addressClickListener) {
        super(itemView);

        mFullName = itemView.findViewById(R.id.fullNameTv);
        mPhoneNumber = itemView.findViewById(R.id.phoneNumberTv);
        mLandMark = itemView.findViewById(R.id.landMarkTv);
     //   mActiveAddress = itemView.findViewById(R.id.activeAddressIndicator);

        this.addressClickListener = addressClickListener;

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        addressClickListener.onAddressClicked(getAdapterPosition());
    }

    public TextView getmLandMark() {
        return mLandMark;
    }

    public ImageView getmActiveAddress() {
        return mActiveAddress;
    }

    public AddressClickListener getAddressClickListener() {
        return addressClickListener;
    }
}
