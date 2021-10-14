package com.aumaid.bochihhott.Addresses;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aumaid.bochihhott.Models.Address;
import com.aumaid.bochihhott.Models.Addresss;
import com.aumaid.bochihhott.Models.Order;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.NullChecker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

public class AddNewAddressFragment extends SupportBlurDialogFragment {

    private static final String TAG = "AddNewAddressFragment";
    private View view;

    //Declaring Widgets
    private TextInputEditText mFullName;
    private TextInputEditText mPhoneNumber;
    private TextInputEditText mAddress;
    private TextInputEditText mLandMark;
    private TextInputEditText mCity;
    private TextInputEditText mPinCode;
    private ProgressBar mProgressBar;

    private Button mSaveAddress;

    public AddNewAddressFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        view = inflater.inflate(R.layout.fragment_add_new_address_layout, container, false);
        //Setting up fragment snippet
      //  Order order = (Order) getArguments().getSerializable("ORDER");
       // Log.d(TAG, "onCreateView: SHowing Order: "+order.toString());
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

      //  bindWidgets();
      //  initWidgets();
     //   initButtonListeners();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpFragment();
        bindWidgets();
        initWidgets();
        initButtonListeners();


    }


    private void initWidgets(){
        Log.d(TAG, "initWidgets: Initializing Widgets...");
        if(getArguments()!=null){
            Bundle bundle = getArguments();
            Order order = (Order) bundle.get("ORDER");
            Addresss address =(Addresss) bundle.get("ADDRESS");

            if(order!=null){
                Log.d(TAG, "initWidgets: Found 1 Order");
            }
            else if(address!=null){
                Log.d(TAG, "initWidgets: Found 1 Address");
            // Addresss address = (Addresss) bundle.getSerializable("ADDRESS");
            //CHecking if address is attached
            //    Log.d(TAG, "initWidgets: Address: "+address.toString());
            //Initialize the widgets
            mFullName.setText(address.getFull_name());
            mPhoneNumber.setText(address.getPhone_number());
            mLandMark.setText(address.getLandmark());
            mAddress.setText(address.getAddress());
            mCity.setText(address.getCity());
            mPinCode.setText(address.getPin_code());

            }
        }else{
            //Do nothing
            Log.d(TAG, "initWidgets: Bundle is nothing So doing nothing");
        }

    }

    private void initButtonListeners() {
        Log.d(TAG, "initButtonListeners: Initializing Button Listeners");
        //Save Address Button
        mSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Selected Address will get Saved Twice
                mProgressBar.setVisibility(View.VISIBLE);
                //TODO: Perform Validation Here
                Addresss address = new Addresss(
                        mFullName.getText().toString().trim(),
                        mPhoneNumber.getText().toString().trim(),
                        mLandMark.getText().toString().trim(),
                        mAddress.getText().toString().trim(),
                        mCity.getText().toString().trim(),
                        mPinCode.getText().toString().trim()
                );

                //     Log.d(TAG, "onClick: Address: "+address.toString());


                //Hiding the dialog box after Saving the Address
                if(saveAddress(address)){
                    getDialog().cancel();
                }
            }
        });
    }

    /**
     * This methods is used bind all the widgets to the fragment*/
    private void bindWidgets() {

        Log.d(TAG, "bindWidgets: Binding Widgets....");

        mFullName = view.findViewById(R.id.fullnameinput);
        mPhoneNumber = view.findViewById(R.id.mobileNumberInput);
        mLandMark = view.findViewById(R.id.landMarkInput);
        mAddress = view.findViewById(R.id.address);
        mCity = view.findViewById(R.id.cityInput);
        mPinCode = view.findViewById(R.id.pinCodeInput);
        mProgressBar = view.findViewById(R.id.progressBar);

        mSaveAddress = view.findViewById(R.id.saveBtn);
    }

    /**
     * This method resizes the fragment to fit into the
     * parent fragment */
    private void setUpFragment(){
        Log.d(TAG, "setUpFragment: Setting up fraGMENT...");
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            dialog.getWindow().setLayout((6 * width)/6, (4 * height)/5);

        }else{
            Log.d(TAG, "setUpFragment: DIalog is null...");
        }

    }

    /**
     * This method is used to save the address into the database*/
    private boolean saveAddress(Addresss address){

        if(address!=null){
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("user_data/"+ FirebaseAuth.getInstance().getUid()+"/addresses");
            String address_id = myRef.push().getKey();
            address.setAddress_id(address_id);
            myRef.child(address_id)
                    .setValue(address);
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(),"Address Saved",Toast.LENGTH_SHORT).show();
            return true;


        }
        return false;

    }
}
