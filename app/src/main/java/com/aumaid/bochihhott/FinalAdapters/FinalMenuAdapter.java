package com.aumaid.bochihhott.FinalAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Animations.BounceInterpolator;
import com.aumaid.bochihhott.Interfaces.CategoriesOptionListener;
import com.aumaid.bochihhott.Models.Category;
import com.aumaid.bochihhott.Models.MenuItem;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ViewHolders.FinalCategoriesViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FinalMenuAdapter extends RecyclerView.Adapter<FinalMenuAdapter.FinalMenuViewHolder> {

    private static final String TAG = "FinalMenuAdapter";

    private ArrayList<MenuItem> mCategories;
    private CategoriesOptionListener categoriesOptionListener;
    private Context mContext;


    public FinalMenuAdapter(Context mContext, ArrayList<MenuItem> mCategories, CategoriesOptionListener categoriesOptionListener) {
        this.mContext = mContext;
        this.mCategories = mCategories;
        this.categoriesOptionListener = categoriesOptionListener;
    }

    @NonNull
    @Override
    public FinalMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_categories_recyclerview,parent,false);
        FinalMenuViewHolder viewHolder = new FinalMenuViewHolder(view, categoriesOptionListener);
        return viewHolder;
    }

    /**
     * This method is used to set icon and text for the view holder*/
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull FinalMenuViewHolder holder, int position) {

        MenuItem category = mCategories.get(position);
        boolean isSelected = category.isSelected();
       // Log.d(TAG, "onBindViewHolder: Item "+isSelected);

       // holder.mCategoryIcon.setImageResource(category.getCategoryIcon());
        holder.mCategoryName.setText(category.getCategory_name());
        //Setting Category Icon
//        Glide.with(mContext)
//                .load(category.getCategory_icon())
//                .into(holder.mCategoryIcon);
        if(isSelected){
            final Animation myAnim = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
            // Use bounce interpolator with amplitude 0.2 and frequency 20
            BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
            myAnim.setInterpolator(interpolator);
            holder.mButtonBackground.startAnimation(myAnim);

            holder.mButtonBackground.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.orange));
            holder.mCategoryName.setTextColor(ContextCompat.getColor(mContext,R.color.white));
            category.setSelected(false);
        }else{

            holder.mButtonBackground.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
            holder.mCategoryName.setTextColor(ContextCompat.getColor(mContext,R.color.dark));
           // category.setSelected(true);
        }



    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    /**
     * This method will be used to add categories to the
     * mCategories ArrayList
     * @param category*/
    private void addCategory(MenuItem category){
        mCategories.add(category);
    }


    public class FinalMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mCategoryIcon;
        public TextView mCategoryName;
        public CardView mButtonBackground;
        private int selected_position = -1;
        private CategoriesOptionListener categoriesOptionListener;


        public FinalMenuViewHolder(@NonNull View itemView, CategoriesOptionListener categoriesOptionListener) {
            super(itemView);

            /**
             * Creating Hooks*/
            mCategoryIcon = itemView.findViewById(R.id.categoryIcon);
            mCategoryName = itemView.findViewById(R.id.categoryName);
            mButtonBackground = itemView.findViewById(R.id.categoryBgCard);
            this.categoriesOptionListener = categoriesOptionListener;


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            //  selected_position = getAdapterPosition();
            categoriesOptionListener.onCategoryClicked(getAdapterPosition());


        }

        public int getSelected_position() {
            return selected_position;
        }

        public void setSelected_position(int selected_position) {
            this.selected_position = selected_position;
        }
    }
}
