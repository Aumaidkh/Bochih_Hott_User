package com.aumaid.bochihhott.PartnerModule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aumaid.bochihhott.DAO.User;
import com.aumaid.bochihhott.Profile.EditProfile;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.FirebaseMethods;
import com.aumaid.bochihhott.Utils.TextFieldHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PartnerLoginActivity extends AppCompatActivity {

    private static final String TAG = "PartnerLoginActivity";

    /**
     * Declaring variables*/
    private Context mContext;

    /**
     * Setting up Firebase stuff*/
    private FirebaseMethods firebaseMethods;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;

    /**
     * Declaring Widgets*/
    private EditText mEmail;
    private EditText mPassword;
    private Button mLoginBtn;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_login);
        bindViews();

        //Setting OnClick listener for the PartnerLogin Button
       init();

    }

    /**
     * This method is used to bind all the views to the activity*/
    private void bindViews(){
        mContext = PartnerLoginActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);
        mAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.restaurantId);
        mPassword = findViewById(R.id.restaurantPassword);
        mProgressBar = findViewById(R.id.progressBar);
        mLoginBtn = findViewById(R.id.partner_login_btn);
    }

    private void init(){
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

                mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Log.d(TAG, "onDataChange: Querieng Snapshot");
                        isPartner(mEmail.getText().toString(),snapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    private boolean isPartner(String email, DataSnapshot snapshot){

        for(DataSnapshot ds: snapshot.getChildren()){

          //  Log.d(TAG, "isPartner: "+ds.getValue(User.class).getEmail().equals(email));
            if(ds.getValue(User.class).getEmail().equals(email)){
                Log.d(TAG, "isPartner: Checking Privileges for the user "+email);
                if(ds.getValue(User.class).getIsPartner().equals("1")){
                    //User is a Partner Therefore sign in the Partner
                    Log.d(TAG, "isPartner: User is a Partner");
                    mAuth.signInWithEmailAndPassword(email,mPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                           // Log.d(TAG, "onSuccess: Navigating To The partner Dashboard");
                            updateUI();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                           // Log.d(TAG, "onFailure: Error Logging in "+e.getMessage());
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(mContext, "No Partner Account exists for the input credentials", Toast.LENGTH_SHORT).show();
                }
            }

        }
        return true;

    }

    /**
     * This method is used to navigate to the partner dashboard screen*/
    private void updateUI(){
       Intent intent = new Intent(mContext,PartnerHomeActivity.class);
       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
       startActivity(intent);
       finish();
    }
}