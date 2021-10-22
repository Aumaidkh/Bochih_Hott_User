package com.aumaid.bochihhott.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.RecyclerViewListener;
import com.aumaid.bochihhott.R;

import org.w3c.dom.Text;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    /**
     * Declaring Widgets*/
    private ImageView mOrderImage;
    private TextView mRestaurantName;
    private TextView mOrderStatus;
    private TextView mPrice;
    private TextView mQuantity;
    private TextView mTimeStamp;
    private Button mRateButton;
    private Button mReorderButton;
    private RecyclerViewListener mListener;



    public OrderViewHolder(@NonNull View itemView,RecyclerViewListener mListener) {
        super(itemView);

        //Binding Widgets
        mOrderImage = itemView.findViewById(R.id.orderImage);
        mRestaurantName = itemView.findViewById(R.id.restaurantNameTv);//
        mOrderStatus = itemView.findViewById(R.id.orderStatus);//
        mPrice = itemView.findViewById(R.id.priceTv);//
        mQuantity = itemView.findViewById(R.id.quantityTv);//
        mTimeStamp = itemView.findViewById(R.id.timeStamp);//
        mRateButton = itemView.findViewById(R.id.rateButton);
        mReorderButton = itemView.findViewById(R.id.reorderButton);
        this.mListener = mListener;

        mRateButton.setOnClickListener(this);
        mReorderButton.setOnClickListener(this);
    }

    public ImageView getmOrderImage() {
        return mOrderImage;
    }

    public TextView getmRestaurantName() {
        return mRestaurantName;
    }

    public TextView getmOrderStatus() {
        return mOrderStatus;
    }

    public TextView getmPrice() {
        return mPrice;
    }

    public TextView getmQuantity() {
        return mQuantity;
    }

    public TextView getmTimeStamp() {
        return mTimeStamp;
    }

    @Override
    public void onClick(View v) {
        mListener.onViewClicked(v,getAdapterPosition());
    }
}
