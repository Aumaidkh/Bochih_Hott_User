package com.aumaid.bochihhott.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.RecyclerViewItemClickListener;
import com.aumaid.bochihhott.R;

public class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView mCategoryIcon;
    public TextView mCategoryName;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public CategoriesViewHolder(@NonNull View itemView, RecyclerViewItemClickListener recyclerViewItemClickListener) {
        super(itemView);

        /**
         * Creating Hooks*/
        mCategoryIcon = itemView.findViewById(R.id.categoryIcon);
        mCategoryName = itemView.findViewById(R.id.categoryName);
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        recyclerViewItemClickListener.onViewClicked(getAdapterPosition());
    }
}
