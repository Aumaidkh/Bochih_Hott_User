package com.aumaid.bochihhott.DAO;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.aumaid.bochihhott.Firebase.Firebase;
import com.aumaid.bochihhott.Models.FoodItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class FoodItemDao {

    private static final String TAG = "FoodItemDao";

    /*Firebase*/
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String foodItemID;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference mStorageReference;

    private Context mContext;
    private int count;
    private double mPhotoUploadProgress = 0;

    public FoodItemDao(Context context){
        mContext = context;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        count = 1;

    }

    /**
     * This method is later going to be used to count the number of images of a food item
     * @param dataSnapshot
     * @param foodId
     * @return number of Photos*/
    public int getImageCount(String foodId, DataSnapshot dataSnapshot){
        int count = 0;
        for(DataSnapshot ds: dataSnapshot
                .child("")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()){
            count++;
        }
        return count;
    }

    /**
     * This method is used to upload a food item to the firebase database with the url of the photo stored in
     * firebase storage*/
    public void uploadToFirebase(boolean active, String category_id, String description, String item_name, Uri uri,float ratings, float price){

       // final StorageReference fileRef = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        foodItemID = myRef.push().getKey();
        final StorageReference fileRef = mStorageReference.child("photos").child("food_item_photos").child(foodItemID).child(item_name+"-"+System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //Creating a new Food item

//                        FoodItem1 item = new FoodItem1(
//                                foodItemID,
//                                true,
//                                category_id,
//                                description,
//                                item_name,
//                                uri.toString(),
//                                ratings,
//                                price
//                        );

                        myRef = mFirebaseDatabase.getReference("food_items").child(foodItemID);

                    //    myRef.setValue(item);

                        myRef = mFirebaseDatabase.getReference("categories").child(category_id).child(foodItemID);

                   //     myRef.setValue(item);

                      //  Model model = new Model(uri.toString());
                       // String modelId = myRef.push().getKey();
                     //   myRef.child("item_photo").setValue(model.getImageUrl());

                        Toast.makeText(mContext, "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(mContext, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
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
     * This method is used to remove a food item from the favorites*/
    public void removeFoodItem(String foodItemID){
        //Creating Database Connection
        String user_id = FirebaseAuth.getInstance().getUid();
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("user_data")
                .child(user_id)
                .child("favorites")
                .child(foodItemID)
                .removeValue();
       // myRef = mFirebaseDatabase.getReference("user_data/"+user_id+"favorites/"+foodItemID);
       // myRef.removeValue();
        Log.d(TAG, "removeFoodItem: Item Removed From the Favorites");
    }

    public void removeItemFromFavorites(FoodItem item){
        String user_id = mAuth.getCurrentUser().getUid();
        //Write an efficent firebase query here to get the food item and insert it into the cart
        // DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
       /* Query query = reference
                .child("food_items")
                .orderByChild("item_id")
                .equalTo(item_id);*/
        //get food item from the database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("user_data")
                .child(user_id)
                .child("favorites")
                .child(item.getItem_id())
                .removeValue();

        Log.d(TAG, "removeItemFromFavorites: Item has been Removed from Favorites");
    }

    /**
     * This method is used to add a food item to the favorites*/
    public void addItemToFavorites(FoodItem item){
        Log.d(TAG, "addItemToFavorites: adding item to the favorites");
        String user_id = mAuth.getCurrentUser().getUid();
        //Write an efficent firebase query here to get the food item and insert it into the cart
        // DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
       /* Query query = reference
                .child("food_items")
                .orderByChild("item_id")
                .equalTo(item_id);*/
        //get food item from the database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("user_data")
                .child(user_id)
                .child("favorites")
                .child(item.getItem_id())
                .setValue(item);

        Log.d(TAG, "Item Has been Added to the Favorites");
    }


    private ArrayList<FoodItem> getFoodItems(String food_name){
        return new ArrayList<FoodItem>();
    }


}
