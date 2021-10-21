package com.aumaid.bochihhott.FinalAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Animations.BounceInterpolator;
import com.aumaid.bochihhott.Interfaces.RecyclerViewItemClickListener;
import com.aumaid.bochihhott.Models.Category;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ViewHolders.FinalCategoriesViewHolder;

import java.util.ArrayList;

public class FinalCategoriesAdapter extends RecyclerView.Adapter<FinalCategoriesViewHolder> {

    private static final String TAG = "FinalCategoriesAdapter";

    private ArrayList<Category> mCategories;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;
    private Context mContext;


    public FinalCategoriesAdapter(Context mContext, ArrayList<Category> mCategories, RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.mContext = mContext;
        this.mCategories = mCategories;
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    @NonNull
    @Override
    public FinalCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_categories_recyclerview,parent,false);
        FinalCategoriesViewHolder viewHolder = new FinalCategoriesViewHolder(view, recyclerViewItemClickListener);
        return viewHolder;
    }

    /**
     * This method is used to set icon and text for the view holder*/
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull FinalCategoriesViewHolder holder, int position) {

        Category category = mCategories.get(position);
        boolean isSelected = category.isSelected();

       // holder.mCategoryIcon.setImageResource(category.getCategoryIcon());
        holder.mCategoryName.setText(category.getCategoryName());
        holder.mCategoryIcon.setImageResource(category.getCategoryIcon());
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
    private void addCategory(Category category){
        mCategories.add(category);
    }
}
