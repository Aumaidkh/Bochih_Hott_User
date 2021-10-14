package com.aumaid.bochihhott.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aumaid.bochihhott.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetProfile extends BottomSheetDialogFragment {

    private static final String TAG = "BottomSheetProfile";

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.bottom_sheet_profile_options,container,false);

        return view;
    }
}
