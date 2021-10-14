package com.aumaid.bochihhott.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.CategoriesOptionListener;
import com.aumaid.bochihhott.R;

import org.w3c.dom.Text;

public class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView mCategoryIcon;
    public TextView mCategoryName;
    private CategoriesOptionListener categoriesOptionListener;

    public CategoriesViewHolder(@NonNull View itemView, CategoriesOptionListener categoriesOptionListener) {
        super(itemView);

        /**
         * Creating Hooks*/
        mCategoryIcon = itemView.findViewById(R.id.categoryIcon);
        mCategoryName = itemView.findViewById(R.id.categoryName);
        this.categoriesOptionListener = categoriesOptionListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        categoriesOptionListener.onCategoryClicked(getAdapterPosition());
    }
}
