package com.aumaid.bochihhott.DialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aumaid.bochihhott.Adapters.PopularFoodsAdapter;
import com.aumaid.bochihhott.Models.FoodItem;
import com.aumaid.bochihhott.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

public class AllPopularFoodsFragment extends SupportBlurDialogFragment {

    private static final String TAG = "AllPopularFoodsFragment";

    private ArrayList<FoodItem> mPFItems;
    private RecyclerView mPopularFoodsRecycler;

    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_all_popular_foods_fragment,container,false);
        //Setting up fragment snippet
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        setUpFragment();
    }

    /**
     * This method resizes the fragment to fit into the
     * parent fragment */
    private void setUpFragment(){
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            dialog.getWindow().setLayout((6 * width)/6, (4 * height)/5);

        }

    }
}
