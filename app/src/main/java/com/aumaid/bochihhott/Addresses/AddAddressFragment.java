package com.aumaid.bochihhott.Addresses;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.aumaid.bochihhott.R;

import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

public class AddAddressFragment extends DialogFragment {
    private static final String TAG = "AddAddressFragment";

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_new_address_layout,container,false);

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        return view;
    }
}
