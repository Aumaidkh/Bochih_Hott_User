package com.aumaid.bochihhott.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.ProfileOptionListener;
import com.aumaid.bochihhott.R;

public class ProfileOptionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mOptionName;
    private ImageView mButtonIcon;
    private ProfileOptionListener profileOptionListener;

    public ProfileOptionViewHolder(@NonNull View itemView, ProfileOptionListener profileOptionListener) {
        super(itemView);

        mOptionName = itemView.findViewById(R.id.optionName);
        mButtonIcon = itemView.findViewById(R.id.enterBtn);
        this.profileOptionListener = profileOptionListener;

        itemView.setOnClickListener(this);
    }

    public TextView getmOptionName() {
        return mOptionName;
    }

    public ImageView getmButtonIcon() {
        return mButtonIcon;
    }

    @Override
    public void onClick(View v) {

        profileOptionListener.onOptionClicked(getAdapterPosition());
    }
}
