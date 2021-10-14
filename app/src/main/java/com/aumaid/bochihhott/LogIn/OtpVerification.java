package com.aumaid.bochihhott.LogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aumaid.bochihhott.DAO.User;
import com.aumaid.bochihhott.Firebase.Firebase;
import com.aumaid.bochihhott.Firebase.FirebaseMessagingService;
import com.aumaid.bochihhott.Home.HomepageActivity;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.FirebaseMethods;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.TimeUnit;

public class OtpVerification extends AppCompatActivity {

    private static final String TAG = "OtpVerification";

    /**
     * declaring other variables*/
    private Context mContext;


    /**
     * declaring firebase stuff*/
    private FirebaseMethods firebaseMethods;
    private Firebase mFirebase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private String verificationId;
    private String userID;
    private DatabaseReference mDatabaseRef;
    private FirebaseMessagingService messagingService;

    private String append = "";
    
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        mContext = OtpVerification.this;

      //  firebaseMethods = new FirebaseMethods(mContext);
        mFirebase = new Firebase(mContext);

        setUpFirebaseAuth();

        retrieveDataFromPreviousIntent();

        initWidgets();




        //Binding and Declaring phoneNumber text View
        TextView mPhoneNumber = findViewById(R.id.textPhoneNumber);

        //Setting up Phone number on PhoneNumber text view
        String phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
        mPhoneNumber.setText(phoneNumber);

        /*Disabling the recaptcha verification*/
        // Force reCAPTCHA flow
        FirebaseAuth.getInstance().getFirebaseAuthSettings().forceRecaptchaFlowForTesting(false);
        // Turn off phone auth app verification.
        FirebaseAuth.getInstance().getFirebaseAuthSettings()
                .setAppVerificationDisabledForTesting(false);

     //   firebaseMethods.createNewUserWithPhoneNumber(phoneNumber,this);
        user = new User(phoneNumber,email,username,password,"0");
       createUserWithPhoneNumber(phoneNumber);


        //Saving the user to firebase Database
       // mFirebase.saveNewUserData(email,Long.parseLong(phoneNumber),StringManipulation.condenseUsername(username).toLowerCase(),"0",password);

    }



    /**
     * This is the first method for creating a user with phone number*/
    private void createUserWithPhoneNumber(String phoneNumber){

        PhoneAuthOptions options
                = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setActivity(this)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(mCallBacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    /**
     * Creating mCallBacks*/
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks
            = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        /**
         * This method is invoked when auto verification is completed*/
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted: verificationCompleted");
            final String code = phoneAuthCredential.getSmsCode();
            Log.d(TAG, "onVerificationCompleted: Code "+code);

            if(code != null){
                //create a user by email id and then store the user by userId in database
                Log.d(TAG, "onVerificationCompleted: code is sent");
                /*verifyCode(code);*/
                PinView mPinView = findViewById(R.id.otpPinView);
                mPinView.setText(code);
                verifyCode(code);

            }

        }

        /**
         * This method is invoked when verification fails by some reason*/
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d(TAG, "onVerificationFailed: Verification Failed");
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        /**
         * This method is invoked when code is sent to the entered number, the code then
         * needs to be checked if it matches the code entered */
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.d(TAG, "onCodeSent: Code send "+s);
            verificationId = s;
        }

    };

    /**
     * This method is used to verify the code
     * by checking if it matches the code that is sent by the firebase*/
    public boolean verifyCode(String code){
        Log.d(TAG, "verifyCode: Verifying Code");
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);
        return true;
    }

    /**
     * This method is responsible for signing in the user (Partner) which is then saved to the
     * database using the userId*/
    private void signInWithCredential(PhoneAuthCredential credential){
        Log.d(TAG, "signInWithCredential: Signing in with credentials");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Log.d(TAG, "onComplete: Sign in Unsuccessful");
                            //Do something if sign in is not successful

                        }else{
                            //write something to do is sign in is successful;
                            //   Log.d(TAG, "onComplete: Saving User to database");
                            //  Log.d(TAG, "onComplete: "+mAuth.getCurrentUser().getUid());
                            userID = mAuth.getCurrentUser().getUid();
                            initFCM(userID);
                            mDatabaseRef
                                    .child("users")
                                    .child(userID)
                                    .setValue(user);
                            Log.d(TAG, "onComplete: Sign in Successful for User:"+userID+"\nUser saved to firebase");

                            linkAccount();
                            updateUi();
                        }
                    }
                });
    }

    /**
     * This method is used to update the Ui when a user successfully signs up
     * back stack gets cleared and Home activity is made the start of the new activity*/
    private void updateUi(){
        Log.d(TAG, "updateUi: Sign up Successful Navigating to the home activity");
        Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
        finish();
    }

    /**
     * This method generates a message token for the user and then saves the same in the database*/
    private void initFCM(String user_id){
        // mDatabaseRef = mFirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        messagingService = new FirebaseMessagingService();
        Log.d(TAG, "initFCM: Retrieving current Registration Token");
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d(TAG, "onComplete: Registration Token: "+token);
                        //  Log.d(TAG, "onComplete: User Id: "+userID);
                        mDatabaseRef.child("messaging_tokens")
                                .child("restaurants")
                                .child(user_id)
                                .setValue(token);
                        // messagingService.sendRegistrationToServer(token);
                    }
                });

    }

    /**
     * This method is used to initialize firebase auth setup*/
    private void setUpFirebaseAuth(){
        mContext = OtpVerification.this;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference();
    }


    /**
     * This method is responsible for linking the email and password with phone number
     * just to make sure that the user would be able to login using the email and phone number*/
    private void linkAccount() {
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        Log.d(TAG, "linkAccount: Email: "+email);
        Log.d(TAG, "linkAccount: Password : "+password);
        Log.d(TAG, "linkAccount: Credential : "+credential);
        // [START link_credential]
        //Problem with this snippet of code________________________________________________________
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "linkWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            // updateUI(user);
                        } else {
                            Log.w(TAG, "linkWithCredential:failure", task.getException());
                            Toast.makeText(mContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //  updateUI(null);
                        }
                    }
                });
        // [END link_credential]
    }

    private void retrieveDataFromPreviousIntent(){
        username = getIntent().getStringExtra("FULL_NAME");
        phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
        email = getIntent().getStringExtra("EMAIL");
        password = getIntent().getStringExtra("PASSWORD");
    }

    /**
     * This method is used to initialize the widgets*/
    private void initWidgets(){
        TextView mPhoneNumber = findViewById(R.id.textPhoneNumber);
        mPhoneNumber.setText(phoneNumber);
        Button mVerifyOtp = findViewById(R.id.verifyOtpBtn);
        PinView mPinView = findViewById(R.id.otpPinView);
        mVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: verify otp pressed");

                verifyCode(mPinView.getText().toString());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: On Start Called");
       // mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
       // if(mAuthListener !=null){
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
    }


}