package com.aumaid.bochihhott.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.CardBackgroundColorChanger;
import com.aumaid.bochihhott.Interfaces.CategoriesOptionListener;
import com.aumaid.bochihhott.R;

public class FinalCategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView mCategoryIcon;
    public TextView mCategoryName;
    public CardView mButtonBackground;
    private int selected_position = -1;
    private CategoriesOptionListener categoriesOptionListener;


    public FinalCategoriesViewHolder(@NonNull View itemView, CategoriesOptionListener categoriesOptionListener) {
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
