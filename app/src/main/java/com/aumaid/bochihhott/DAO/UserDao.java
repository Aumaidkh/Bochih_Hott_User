package com.aumaid.bochihhott.DAO;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.aumaid.bochihhott.Firebase.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UserDao {

    private static final String TAG = "UserDao";

    private FirebaseStorage mStorage;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private Context mContext;

    private String user_id;
    private User user;

    public UserDao(Context mContext) {
        this.user = new User();
        this.user_id = FirebaseAuth.getInstance().getUid();
        this.mContext = mContext;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users/"+user_id);
        mStorageRef = FirebaseStorage.getInstance().getReference("profile_photos/"+user_id);
    }

    /**
     * saves changes
     * @param username
     * @param about
     * @param imageUri
     * to the database*/
    public void updateChangesToUser(String username, String about, Uri imageUri, ProgressBar progressBar, Activity activity){
        progressBar.setVisibility(View.VISIBLE);
        //Save Photo to Firebase Storage and save its url
        mStorageRef.child(user_id+"-"+System.currentTimeMillis()+"."+getFileExtension(imageUri));
        mStorageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //Save Changes to the Database Here
                        mDatabaseRef.child("username").setValue(username);
                        mDatabaseRef.child("description").setValue(about);
                        mDatabaseRef.child("profile_photo").setValue(uri.toString());
                        Toast.makeText(mContext,"Profile Updated",Toast.LENGTH_SHORT).show();
                        //Hiding Progress Bar
                        progressBar.setVisibility(View.GONE);
                        activity.finish();

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
                Toast.makeText(mContext,"Error updating profile",Toast.LENGTH_SHORT).show();
                //Hiding Progress Bar
                progressBar.setVisibility(View.GONE);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        });
        //Save changes to Firebase Database

    }

    /**
     * This method is used to get the file extension of the image
     * that is selected for uploading the image*/
    private String getFileExtension(Uri mUri){

        ContentResolver cr = mContext.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

    /**
     * This method is used to get the user from the database*/
    public User getUser(){
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                Log.d(TAG, "onDataChange: User Retreived");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return user;
    }

    public void updateProfile(String username, String about, Uri imageUri, ProgressBar progressBar, Activity activity){
        progressBar.setVisibility(View.VISIBLE);
        if(imageUri!=null){
            updateProfilePhoto(imageUri, username, about, progressBar, activity);
        }else{ //Save Changes to the Database Here
            mDatabaseRef.child("username").setValue(username);
            mDatabaseRef.child("description").setValue(about);
            Toast.makeText(mContext,"Profile Updated",Toast.LENGTH_SHORT).show();
            //Hiding Progress Bar
            progressBar.setVisibility(View.GONE);
            activity.finish();}
    }

    private void updateProfilePhoto(Uri imageUri,String username,String about,ProgressBar progressBar,Activity activity){
        //Save Photo to Firebase Storage and save its url
        mStorageRef.child(user_id+"-"+System.currentTimeMillis()+"."+getFileExtension(imageUri));
        mStorageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //Save Changes to the Database Here
                        //Save Changes to the Database Here
                        mDatabaseRef.child("username").setValue(username);
                        mDatabaseRef.child("description").setValue(about);
                        mDatabaseRef.child("profile_photo").setValue(uri.toString());
                        Toast.makeText(mContext,"Profile Updated",Toast.LENGTH_SHORT).show();
                        //Hiding Progress Bar
                        progressBar.setVisibility(View.GONE);
                        activity.finish();


                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
                Toast.makeText(mContext,"Error updating profile",Toast.LENGTH_SHORT).show();
                //Hiding Progress Bar
              //  progressBar.setVisibility(View.GONE);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        });
        //Save changes to Firebase Database
    }

}
