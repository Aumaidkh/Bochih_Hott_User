package com.aumaid.bochihhott.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.aumaid.bochihhott.Models.CartItem;
import com.aumaid.bochihhott.Models.CartItem_;
import com.aumaid.bochihhott.Models.FoodItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class CartDao {

    private static final String TAG = "CartDao";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String foodItemID;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private Context mContext;

    public CartDao(Context mContext) {
        this.mContext = mContext;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void addItemToCart(String item_id, int quantity){
        
        String user_id = mAuth.getCurrentUser().getUid();
        //Write an efficent firebase query here to get the food item and insert it into the cart
       // DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
       /* Query query = reference
                .child("food_items")
                .orderByChild("item_id")
                .equalTo(item_id);*/
        //get food item from the database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //   Log.d(TAG, "onDataChange: "+ds);
                FoodItem item = snapshot.child("food_items").child(item_id).getValue(FoodItem.class);

                Log.d(TAG, "onDataChange: "+item.getItem_name()+" added to the cart");

                //Adding item into the cart
                CartItem_ cartItem = new CartItem_(user_id,item_id,quantity,item);

                myRef.child("user_data")
                        .child(user_id)
                        .child("cart")
                        .child(item_id)
                        .setValue(cartItem);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Log.d(TAG, "addItemToCart: Item added to the cart");


    }

    public void clearCart(){
        myRef = mFirebaseDatabase.getReference("user_data")
                .child(mAuth.getCurrentUser().getUid())
                .child("cart");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Deleting Nodes");
                for(DataSnapshot ds :dataSnapshot.getChildren()){

                    ds.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

    public void addItemToCart(FoodItem item){
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
                .child("cart")
                .child(item.getItem_id())
                .setValue(item);

        Log.d(TAG, "addItemToCart: Food Item added to the cart");
    }

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


    private void deleteTask (final String userName){

        myRef = mFirebaseDatabase.getReference("user_data")
                .child(mAuth.getCurrentUser().getUid())
                .child("cart");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds :dataSnapshot.getChildren()){

                    ds.getRef().removeValue();

//                    if(ds.child("NewUser").child("userName").getValue(String.class).equals(userName)){
//                        ds.child("NewUser").child("userName").getRef().removeValue();
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

    public void deleteItemFromCart(String item_id, String user_id){
        
        myRef.child("cart")
                .child(user_id)
                .child(item_id)
                .removeValue();
        
    }
}
