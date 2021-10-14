package com.aumaid.bochihhott.LogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aumaid.bochihhott.DAO.User;
import com.aumaid.bochihhott.Firebase.Firebase;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.TextFieldHelperClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    /**
     * declaring other variables*/
    private Context mContext;

    /**
     * declaring firebase stuff*/
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Firebase mFirebase;

    //Database Variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;


    /**
     * Declaring views or Widgets
     * */
    private EditText mFullName;
    private EditText mPhoneNumber;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mReenteredPassword;
    private Button sendOtpButton;
    private ProgressBar mProgressBar;


    private String email;
    private String username;
    private String password1;
    private String password2;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        bindViews();
        init();

    }

    /**
     * This method is used to bind all the widgets/views*/
    private void bindViews(){
        mContext = SignUpActivity.this;
        //Binding all the widgets or views together
        mFullName = findViewById(R.id.fullnameinput);
        mPhoneNumber = findViewById(R.id.phonenumberinput);
        mEmail = findViewById(R.id.emailinput);
        mPassword = findViewById(R.id.createpasswordinput);
        mReenteredPassword = findViewById(R.id.reenterpasswordinput);
        sendOtpButton = findViewById(R.id.sign_up_btn);
        mProgressBar = findViewById(R.id.progressBar);
    }

    /**
     * This method does all the work of validations and sending user to the next Screen*/
    private void init(){
        sendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //making the progress bar visible
                mProgressBar.setVisibility(View.VISIBLE);

               // Log.d(TAG, "onClick: Setting OnClick for SignUp button");
                email = mEmail.getText().toString();
                username = mFullName.getText().toString();
                phoneNumber = "+91"+mPhoneNumber.getText().toString();
                password1 = mPassword.getText().toString();
                password2 = mReenteredPassword.getText().toString();

              //  Toast.makeText(mContext, "Sign Up Button Tapped", Toast.LENGTH_SHORT).show();

                //Validating Text Fields
                if(!validatePasswords(password1,password2)){
                    Log.d(TAG, "First if Ran");
                    //making the progress bar invisible
                    mProgressBar.setVisibility(View.GONE);
                    return;
                }
                if(!validateBasicDetailsFields(username,phoneNumber,email,password1,password2)){
                    Log.d(TAG, "onClick: second if ran");
                    //making the progress bar invisible
                    mProgressBar.setVisibility(View.GONE);
                    return;

                }

                mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

                mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(!checkPhoneNumber(phoneNumber,snapshot)){
                            Log.d(TAG, "onDataChange:No match found for "+phoneNumber);
                            if(!checkEmail(email,snapshot)){
                                Log.d(TAG, "onDataChange: No match found for "+email);

                                //Update the UI here
                                updateUI();

                                Log.d(TAG, "onDataChange: Updating UI");
                                return;
                            }
                            Log.d(TAG, "onDataChange: Email already exists");
                            //making the progress bar invisible
                            mProgressBar.setVisibility(View.GONE);
                            mEmail.setError("Email already exists");
                            return;
                        }
                        //making the progress bar invisible
                        mProgressBar.setVisibility(View.GONE);

                        mPhoneNumber.setError("Phone Number already exists");
                       // mPhoneNumber.setError(null);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    /**
     * This method takes all the inputs from the edit text views and returns false if
     * any of the views is empty else it returns true*/
    private boolean validateBasicDetailsFields(String fullname, String phoneNumber, String email, String password1, String password2){

        if(TextFieldHelperClass.emptyField(fullname)){
            mFullName.setError("Full Name can't be empty");
            return false;
        }else if(TextFieldHelperClass.emptyField(phoneNumber)){
            mPhoneNumber.setError("Phone Number can't be empty");
            return false;
        }else if(TextFieldHelperClass.emptyField(email)){
            mEmail.setError("Email can't be empty");
            return false;
        }else if(TextFieldHelperClass.emptyField(password1)){
            mPassword.setError("Phone Number can't be empty");
            return false;
        }else if(TextFieldHelperClass.emptyField(password2)){
            mReenteredPassword.setError("Phone Number can't be empty");
            return false;
        }else{
            return true;
        }

    }

    /**
     * This method is used to check if both the passwords are same:
     * returns true if same else
     * returns false
     * */
    private boolean validatePasswords(String password1, String password2){
        if (password1.equals(password2)){
            return true;
        }
        mReenteredPassword.setError("Passwords don't match");
        return false;
    }

    /**
     * This Method returns true if
     * @param phoneNumber is found in
     * @param snapshot */
    private boolean checkPhoneNumber(String phoneNumber, DataSnapshot snapshot){

        Log.d(TAG, "checkPhoneNumber: Checking phone Number in datasnatpshot");
        for(DataSnapshot ds: snapshot.getChildren()){
           // Log.d(TAG, "Logging all Phone Numbers: "+ds.getValue(User.class).getPhone_number().equals(phoneNumber));
            if(ds.getValue(User.class).getPhone_number().equals(phoneNumber)){
                return true;
            }

        }
        return false;

    }

    /**
     * This method return true if
     * @param email is found in
     * @param snapshot */
    private boolean checkEmail(String email, DataSnapshot snapshot){

        for(DataSnapshot ds: snapshot.getChildren()){
            // Log.d(TAG, "Logging all Phone Numbers: "+ds.getValue(User.class).getPhone_number().equals(phoneNumber));
            if(ds.getValue(User.class).getEmail().equals(email)){
                return true;
            }
        }

        return false;

    }

    /**
     * This method is used to direct the user to the otp verification screen*/
    private void updateUI(){
        Intent intent = new Intent(mContext,OtpVerification.class);
        intent.putExtra("FULL_NAME",username);
        intent.putExtra("EMAIL",email);
        intent.putExtra("PHONE_NUMBER",phoneNumber);
        intent.putExtra("PASSWORD",password1);

        startActivity(intent);
        finish();
    }

}