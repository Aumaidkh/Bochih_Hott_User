package com.aumaid.bochihhott.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.CategoriesOptionListener;
import com.aumaid.bochihhott.Models.Category;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ViewHolders.CategoriesViewHolder;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {

    private ArrayList<Category> mCategories;
    private CategoriesOptionListener categoriesOptionListener;

    public CategoriesAdapter(ArrayList<Category> mCategories, CategoriesOptionListener categoriesOptionListener) {
        this.mCategories = mCategories;
        this.categoriesOptionListener = categoriesOptionListener;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_category_item_layout,parent,false);
        CategoriesViewHolder viewHolder = new CategoriesViewHolder(view, categoriesOptionListener);
        return viewHolder;
    }

    /**
     * This method is used to set icon and text for the view holder*/
    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        Category category = mCategories.get(position);

        holder.mCategoryIcon.setImageResource(category.getCategoryIcon());
        holder.mCategoryName.setText(category.getCategoryName());

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
