package com.aumaid.bochihhott.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.aumaid.bochihhott.DAO.User;
import com.aumaid.bochihhott.DAO.UserAccountSettings;
import com.aumaid.bochihhott.Models.Restaurant;
import com.aumaid.bochihhott.Models.UserSettings;
import com.aumaid.bochihhott.R;
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

import java.util.concurrent.TimeUnit;

public class FirebaseMethods{

    private static final String TAG = "FirebaseMethods";

    /*Firebase*/
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private String verificationId;

    private Context mContext;

    public FirebaseMethods(Context context){
        mContext = context;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        if(mAuth.getCurrentUser()!=null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public void createNewUserWithPhoneNumber(String phoneNumber, Activity activity){

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
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    final String code = phoneAuthCredential.getSmsCode();

                    if(code != null){

                        verifyCode(code);
                    }

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);

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


        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            //Do something if sign in is not successful
                        }else{
                            //write something to do is sign in is successful;
                            userID = mAuth.getCurrentUser().getUid();


                        }
                    }
                });
    }

    /**
     * This method is used to add a new user to the database*/

    /**
     * Add  information to the users nonde
     * Add information to the user_account_settings node
     * @param email
     * @param username
     * @param profile_photo */
    public void addNewUser(String email, String phone_number, String username,String profile_photo, String password){


        User user = new User(phone_number,email,StringManipulation.condenseUsername(username).toLowerCase(),password,"0");

        myRef.child(mContext.getString(R.string.db_name_users))
                .child(userID)
                .setValue(user);

//        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                //User with email and password Created
//                Log.d(TAG, "onSuccess: User with email and password created");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });




    }

    public boolean checkIfUserNameExists(String username, DataSnapshot dataSnapshot){
        Log.d(TAG, "checkIfUserNameExists: Checking if username already exists");
        User user = new User();

        for(DataSnapshot ds: dataSnapshot.child(userID).getChildren() ){
            Log.d(TAG, "checkIfUserNameExists: datasnapshot "+ds);

            user.setUsername(ds.getValue(User.class).getUsername());

            if(StringManipulation.expandUsername(user.getUsername()).equals(username)){
                Log.d(TAG, "checkIfUserNameExists: Found a match");
                return true;
            }

        }

        return false;
    }


    public UserSettings getUserInfo(DataSnapshot dataSnapshot){
        Log.d(TAG, "getUserInfo: Getting user information ");

        User user = new User();
        UserAccountSettings settings = new UserAccountSettings();

        for(DataSnapshot ds: dataSnapshot.getChildren()){

            if(ds.getKey().equals("users")){
                try{
                    settings.setFull_name(
                            ds.child(userID)
                            .getValue(UserAccountSettings.class)
                            .getFull_name()
                    );
                    settings.setPhone_number(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getPhone_number()
                    );settings.setProfile_photo(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getProfile_photo()
                    );

                    Log.d(TAG, "getUserInfo: Fetched User Settings "+settings.toString());

                }catch (NullPointerException e){
                    Log.e(TAG, "getUserInfo: NullPointerException "+e.getMessage() );
                }
            }

            if(ds.getKey().equals("user_account_settings")){
                try{
                    user.setEmail(
                            ds.child(userID)
                            .getValue(User.class)
                            .getEmail()
                    );
                    user.setPassword(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getPassword()
                    );
                    user.setUsername(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getUsername()
                    );

                    Log.e(TAG, "getUserInfo: Retrieved User info"+user.toString() );
                }catch (NullPointerException e){
                    Log.e(TAG, "getUserInfo: NullpointerException "+e.getMessage() );
                }
            }

        }

        return new UserSettings(user,settings);

    }

    public boolean checkIfUsernameExists(String username, DataSnapshot datasnapshot) {
        Log.d(TAG, "checkIfUsernameExists: checking if " + username + " already exists.");

        User user = new User();

        for (DataSnapshot ds : datasnapshot.child(userID).getChildren()) {
            Log.d(TAG, "checkIfUsernameExists: datasnapshot: " + ds);

            user.setUsername(ds.getValue(User.class).getUsername());
            Log.d(TAG, "checkIfUsernameExists: username: " + user.getUsername());

            if (StringManipulation.expandUsername(user.getUsername()).equals(username)) {
                Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + user.getUsername());
                return true;
            }
        }
        return false;
    }




}
