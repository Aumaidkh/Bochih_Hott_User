package com.aumaid.bochihhott.Addresses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aumaid.bochihhott.CheckOut.AddressSelectorFragment;
import com.aumaid.bochihhott.CheckOut.AddressSelectorFragment1;
import com.aumaid.bochihhott.R;

public class AddressesParentActivity extends AppCompatActivity {

    private static final String TAG = "AddressesParentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses_parent);

        showAddressesSelectorFragment();

    }

    private void showAddressesSelectorFragment(){

        AddressSelectorFragment1 selectorFragment = new AddressSelectorFragment1();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,selectorFragment).commit();

    }
}