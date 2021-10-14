package com.aumaid.bochihhott.CheckOut;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aumaid.bochihhott.Firebase.Firebase;
import com.aumaid.bochihhott.Models.Address;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.TextFieldHelperClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddressFragment extends Fragment {

    private static final String TAG = "AddressFragment";
    private final String fragment = "AddressFragment";
    private final String CITY = "anantnag";
    private final String PIN_CODE = "pincode";

    /**
     * Declaring widgets
     */
    private View view;
    private Button mContinueBtn;
    private EditText mAddressLine1;
    private EditText mLandmark;
    private EditText mVillage;
    private EditText mCity;
    private EditText mPinCode;
    private ImageView mBackBtn;
    private TextView mTotalTextView;
    private TextView mTotalText;

    /**
     * Declaring variables
     */
    private String addressLine1;
    private String landmark;
    private String village;
    private String city;
    private String pincode;
    private String grand_total;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_address_layout, container, false);

       if(grand_total!=null){
           grand_total = getArguments().getString("GRAND_TOTAL");
       }

        bindViews();
        init();
        return view;
    }

    private void saveNewAddress(){
        extractDataFromEditTexts();
        //Form an Address Object
        Address address = new Address(addressLine1, landmark, city, village, pincode);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user_data/"+ FirebaseAuth.getInstance().getUid());
        //Saving the address here
        String key = reference.push().getKey();
        address.setAddress_id(key);

        reference.child(key)
                .setValue(address);

        Toast.makeText(getActivity(), "Address saved", Toast.LENGTH_SHORT).show();
    }

    private void init() {
        if(grand_total==null){
            mTotalTextView.setVisibility(View.GONE);
            mTotalText.setVisibility(View.GONE);
            mContinueBtn.setText("Save");

            //Saving the new Address only
            mContinueBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveNewAddress();
                }
            });

        }
        //Setting Total on Total TextView
        mTotalTextView.setText("₹ "+grand_total);
        //Back Button
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        //continue btn
        mContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                extractDataFromEditTexts();
                //Validating Fields
                if(validateFields(addressLine1,landmark,village,city,pincode)){
                    //Check service availability
                    if (checkServiceAvailability()) {
                        //Binding data with the bundle
                        Bundle bundle = new Bundle();
                        bundle.putString("CALLING_FRAGMENT_NAME",fragment);
                        bundle.putString("ADDRESS_ID","addressId");
                        bundle.putString("ADDRESS_LINE_1",addressLine1);
                        bundle.putString("LANDMARK",landmark);
                        bundle.putString("VILLAGE",village);
                        bundle.putString("CITY",city);
                        bundle.putString("PIN_CODE",pincode);
                        bundle.putString("GRAND_TOTAL",grand_total);

                        PaymentFragment fragment = new PaymentFragment();
                        fragment.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, fragment);
                        transaction.addToBackStack("Payment Fragment");
                        transaction.commit();
                    }else{
                        Toast.makeText(getActivity(), "We will soon be available in your city", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private boolean checkServiceAvailability() {
        return city.toLowerCase().matches(CITY);
    }

    private void extractDataFromEditTexts() {
        addressLine1 = mAddressLine1.getText().toString().trim();
        landmark = mLandmark.getText().toString().trim();
        village = mVillage.getText().toString().trim();
        city = mCity.getText().toString().trim();
        pincode = mPinCode.getText().toString().trim();
    }

    private void bindViews() {
        mContinueBtn = view.findViewById(R.id.continueBtn);
        mAddressLine1 = view.findViewById(R.id.addressLine1Input);
        mLandmark = view.findViewById(R.id.landMark);
        mVillage = view.findViewById(R.id.villageInput);
        mCity = view.findViewById(R.id.cityInput);
        mPinCode = view.findViewById(R.id.pinCodeInput);
        mBackBtn = view.findViewById(R.id.back_btn);
        mTotalTextView = view.findViewById(R.id.totalTv);
        mTotalText = view.findViewById(R.id.totalText);
    }

    /**
     * This method takes all the inputs from the edit text views and returns false if
     * any of the views is empty else it returns true*/
    private boolean validateFields(String addressLine1, String landmark, String village, String city, String pincode){

        if(TextFieldHelperClass.emptyField(addressLine1)){
            mAddressLine1.setError("Field can't be blank");
            return false;
        }else if(TextFieldHelperClass.emptyField(landmark)){
            mLandmark.setError("Field can't be blank");
            return false;
        }else if(TextFieldHelperClass.emptyField(village)){
            mVillage.setError("Field can't be blank");
            return false;
        }else if(TextFieldHelperClass.emptyField(city)){
            mCity.setError("Field can't be blank");
            return false;
        }else if(TextFieldHelperClass.emptyField(pincode)){
            mPinCode.setError("Field can't be blank");
            return false;
        }else{
            return true;
        }

    }

}
