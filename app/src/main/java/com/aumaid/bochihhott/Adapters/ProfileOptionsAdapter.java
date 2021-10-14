package com.aumaid.bochihhott.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Interfaces.ProfileOptionListener;
import com.aumaid.bochihhott.Models.ProfileOption;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.ViewHolders.ProfileOptionViewHolder;

import java.util.ArrayList;

public class ProfileOptionsAdapter extends RecyclerView.Adapter<ProfileOptionViewHolder>{

    private ArrayList<ProfileOption> mOptions;
    private ProfileOptionListener profileOptionListener;

    public ProfileOptionsAdapter(ArrayList<ProfileOption> mOptions, ProfileOptionListener profileOptionListener) {
        this.mOptions = mOptions;
        this.profileOptionListener = profileOptionListener;
    }

    @NonNull
    @Override
    public ProfileOptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_profile_option_item_layout,parent,false);
        ProfileOptionViewHolder viewHolder = new ProfileOptionViewHolder(view, profileOptionListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileOptionViewHolder holder, int position) {
        ProfileOption mPOption = mOptions.get(position);

        holder.getmOptionName().setText(mPOption.getmOptionName());
        holder.getmButtonIcon().setImageResource(R.drawable.ic_next);
    }

    @Override
    public int getItemCount() {
        return mOptions.size();
    }

    /**
     * This method will be used to add options to the profile*/
    public void addOption(ProfileOption option){
        mOptions.add(option);
    }


}
