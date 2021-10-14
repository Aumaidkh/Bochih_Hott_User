package com.aumaid.bochihhott.Firebase;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.aumaid.bochihhott.DAO.User;
import com.aumaid.bochihhott.DAO.UserAccountSettings;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.StringManipulation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.concurrent.TimeUnit;

public class Firebase {

    /**
     * Declaring all the variables*/
    private static final String TAG = "Firebase";

    //Authentication Variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String verificationId;

    //Database Variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;

    //Cloud Storage Variables
    private FirebaseStorage mStorage;

    //Other Variables
    private Context mContext;
    private String mUserId;
    private User mUser;


    public Firebase(Context context){
        mContext = context;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference();
        mUser = new User();

        if(mAuth.getCurrentUser()!=null){
            mUserId = mAuth.getCurrentUser().getUid();
        }
    }

    /**
     * This is database related section*/
    public void saveNewUserData(User user){

        mDatabaseRef.child(mContext.getString(R.string.db_name_users))
                .child(mUserId)
                .setValue(user);

    }

    /**
     * This is user registration and login  section*/
    public void createNewUserWithPhoneNumber(String phoneNumber,User user,Activity activity){

        mUser.setUsername(user.getUsername());
        mUser.setEmail(user.getEmail());
        mUser.setPhone_number(user.getPhone_number());
        mUser.setPassword(user.getPassword());
        mUser.setIsPartner(user.getIsPartner());

        PhoneAuthOptions options
                = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setActivity(activity)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(mCallBacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                /**
                 * This method is invoked when auto verification is completed*/
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    Log.d(TAG, "onVerificationCompleted: verificationCompleted");
                    final String code = phoneAuthCredential.getSmsCode();

                    if(code != null){
                        //create a user by email id and then store the user by userId in database
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

    public boolean verifyCode(String code){
        Log.d(TAG, "verifyCode: Verifying Code");
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);
        return true;
    }

    private void signInWithCredential(PhoneAuthCredential credential){
        Log.d(TAG, "signInWithCredential: Sigining in with credentials");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Log.d(TAG, "onComplete: Sign in Unsuccessful");
                            //Do something if sign in is not successful

                        }else{
                            //write something to do is sign in is successful;
                            Log.d(TAG, "onComplete: Saving User to database");
                            mUserId = mAuth.getCurrentUser().getUid();
                            mAuth.createUserWithEmailAndPassword(mUser.getEmail(),mUser.getPassword())
                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            Log.d(TAG, "onSuccess: User Created with Email");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Unable To Create User with email");
                                }
                            });
                            mDatabaseRef
                                    .child(mContext.getString(R.string.db_name_users))
                                    .child(mUserId)
                                    .setValue(mUser);
                            Log.d(TAG, "onComplete: Sign in Successful for User:"+mUserId+"\nUser saved to firebase");

                        }
                    }
                });
    }

    public boolean checkIfNumberExists(String phoneNumber, DataSnapshot snapshot){
        Log.d(TAG, "checkIfUsernameExists: Checking if  " + phoneNumber + " already exists.");

        for(DataSnapshot ds: snapshot.getChildren()){
            Log.d(TAG, "onDataChange: Iterating the data snapshots");
            if(snapshot.getValue(User.class).getPhone_number().equals(phoneNumber)){
                Log.d(TAG, "onDataChange: Found a match for "+phoneNumber);
                return true;
            }

        }
        return false;
    }

}
