package com.aumaid.bochihhott.FinalAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.FoodItemListener;
import com.aumaid.bochihhott.Interfaces.RecyclerViewListener;
import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ViewHolders.FoodItemViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FeaturedFoodItemAdapter extends RecyclerView.Adapter<FoodItemViewHolder> {

    private ArrayList<FoodItem> mFoodItems;
    private Context mContext;
    private FoodItemListener foodItemListener;
    private RecyclerViewListener mListener;
  //  private HomeFragment fragment;

    public FeaturedFoodItemAdapter(ArrayList<FoodItem> mFoodItems, Context mContext, FoodItemListener foodItemListener,RecyclerViewListener mListener) {
        this.mFoodItems = mFoodItems;
        this.mContext = mContext;
        this.foodItemListener = foodItemListener;
        this.mListener = mListener;

    }


    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_restaurantsfragment_featured_items_rv,parent,false);
        FoodItemViewHolder viewHolder = new FoodItemViewHolder(view,foodItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {

        FoodItem item = mFoodItems.get(position);

        Glide.with(mContext)
                .load(item.getItem_photo())
                .into(holder.getmFoodImage());
        holder.getmFoodItemName().setText(item.getRestaurant_name());
        holder.getmFoodRestaurantAddress().setText(item.getRestaurant_address());
       // holder.getmFoodCategory().setText(item.getCategory_id());
        holder.getmFoodRating().setText(Float.toString(item.getItem_ratings()));
        holder.getmFoodPrice().setText("₹ "+item.getPrice());

    }

    @Override
    public int getItemCount() {
        return mFoodItems.size();
    }

    /**
     * This method is used to add food items in the recycler view
     * @param item*/
    public void addFoodItems(FoodItem item){
        mFoodItems.add(item);
    }

    private class FeaturedFoodsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mFoodImage;
        private TextView mFoodItemName;
        private TextView mFoodRestaurantAddress;
        // private TextView mFoodCategory;
        private TextView mFoodRating;
        private TextView mFoodPrice;
        private CardView mLikeFoodItem;
        private FoodItemListener foodItemListener;
        private RecyclerViewListener mListener;


        public FeaturedFoodsViewHolder(@NonNull View itemView, FoodItemListener listener,RecyclerViewListener mListener) {
            super(itemView);

            mFoodImage = itemView.findViewById(R.id.foodImage);
            mFoodItemName = itemView.findViewById(R.id.itemNameTv);
            mFoodRestaurantAddress = itemView.findViewById(R.id.restaurantAddressTv);
            //  mFoodCategory = itemView.findViewById(R.id.foodItemCategory);
            mFoodRating = itemView.findViewById(R.id.ratingTv);
            mLikeFoodItem = itemView.findViewById(R.id.likeBtn);
            mFoodPrice = itemView.findViewById(R.id.priceTv);
            this.mListener = mListener;
            this.foodItemListener = listener;

            itemView.setOnClickListener(this);
            mLikeFoodItem.setOnClickListener(this::onClick);


        }

        public ImageView getmFoodImage() {
            return mFoodImage;
        }

        public TextView getmFoodItemName() {
            return mFoodItemName;
        }

        public TextView getmFoodRestaurantAddress() {
            return mFoodRestaurantAddress;
        }

        // public TextView getmFoodCategory() {
        //     return mFoodCategory;
        //  }

        public TextView getmFoodRating() {
            return mFoodRating;
        }

        public TextView getmFoodPrice() {
            return mFoodPrice;
        }

        @Override
        public void onClick(View v) {
            mListener.onViewClicked(v,getAdapterPosition());
            foodItemListener.onFoodClicked(getAdapterPosition());
            //fragment.openProductDescriptionFragment(getAdapterPosition(),v.findViewById(R.id.foodImage));


        }
    }
}
