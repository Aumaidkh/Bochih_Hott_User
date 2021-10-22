package com.aumaid.bochihhott.LogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aumaid.bochihhott.Home.HomepageActivity;
import com.aumaid.bochihhott.PartnerModule.PartnerLoginActivity;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.TextFieldHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    /**
     * declaring other variables*/
    private Context mContext;

    /**
     * declaring firebase stuff*/
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;

    /**
     * declaring widgets*/
    private ProgressBar mProgressBar;
    private EditText mEmail;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(getColor(R.color.background_status_bar_filter));
        setContentView(R.layout.final_login_activity);

        //Initializing context
        mContext = LoginActivity.this;

        //initializing widgets
        mProgressBar = findViewById(R.id.progressBar);
        mPassword = findViewById(R.id.passwordinput);
        mEmail = findViewById(R.id.emailinput);

        //Initialize firebase auth
        mAuth = FirebaseAuth.getInstance();


    }

    public void login(View view){

        //Showing up the progressbar
        mProgressBar.setVisibility(View.VISIBLE);

        //Getting values from the input fields
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

       // checkUser();
        //Validating the data and the calling sign in
        if(validateFields(email,password)){
            signInWithEmailAndPassword(email,password);

        }



    }

    /**
     * This method will only navigate the user
     * to SignUpActivity
     * */
    public void joinNow(View view){
        Intent intent = new Intent(mContext, SignUpActivity.class);
        startActivity(intent);
    }

    /**
     * This method is used to update the ui
     * after the user has successfully been authenticated
     * by firebase*/
   private void updateUI(FirebaseUser user){
        // Hiding the Progress Bar
       mProgressBar.setVisibility(View.GONE);
       Intent intent = new Intent(mContext, HomepageActivity.class);
       startActivity(intent);
       finish();

   }

    /**
     * Input fields validation
     * @param email and
     * @param password
     * returns false if any of the inputs are empty
     */
    private boolean validateFields(String email, String password) {

        if (email.matches("")) {
            Toast.makeText(mContext, "Email can't be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.matches("")) {
            Toast.makeText(mContext, "Password can't be blank", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * This method is responsible for logging in a user
     * taking email and password as @params it authenticates the user*/
    private void signInWithEmailAndPassword(String email, String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            //Updating Ui for the current user
                            updateUI(mAuth.getCurrentUser());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(mContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            TextFieldHelperClass.clearField(mEmail, mPassword);
                            mEmail.setError("Invalid email or password");
                            mProgressBar.setVisibility(View.GONE);

                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    /**
     * This method is used to show partner login screen
     * */
    public void partnerLogin(View view){
        startActivity(new Intent(getApplicationContext(), PartnerLoginActivity.class));

    }


    public void checkUser(){
        /*Getting username and password*/
        String _Username = mEmail.getText().toString();
        String _Password = mPassword.getText().toString();

        /*Checking database for the username entered*/
        Query checkUser = FirebaseDatabase.getInstance().getReference("users").orderByChild("email").equalTo(_Username);

        Log.d(TAG, "checkUser: Database Queried");
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    /*Checking database for password for the entered username*/
                    String realPassword = snapshot.child(_Username).child("password").getValue(String.class);
                    Log.d(TAG, "onDataChange: Real Password "+realPassword);
                    if(realPassword.equals(_Password)){
                        /*Log in the user*/
                        Toast.makeText(getApplicationContext(),"Logged in as "+_Username,Toast.LENGTH_SHORT).show();


                    }else{
                        /*Invalid password*/
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Password doesn't match",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    //Invalid username
                    mProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Invalid Username",Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}