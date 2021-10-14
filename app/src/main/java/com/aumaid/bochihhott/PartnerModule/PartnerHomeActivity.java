package com.aumaid.bochihhott.PartnerModule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.aumaid.bochihhott.Adapters.UniversalImageLoader;
import com.aumaid.bochihhott.DAO.FoodItemDao;
import com.aumaid.bochihhott.Models.Model;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.FirebaseMethods;
import com.bumptech.glide.Glide;
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


public class PartnerHomeActivity extends AppCompatActivity {

    private static final String TAG = "PartnerHomeActivity";

    private StorageReference mSReference;
    private ImageView mProductImage;
    private Button mAddProductBtn;
    private Uri imageUri;
    private Context mContext;

    /**
     * declaring firebase stuff*/
    private FirebaseMethods firebaseMethods;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    /*Newly added stuff*/
    //vars
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("photos/food_image_photos/");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();


    /*Declaring Widgets*/
    private EditText mProductName;
    private EditText mProductPrice;
    private Spinner mProductCategory;
    private EditText mProductDescription;
    private ImageView mBackButton;

    /*Variables*/
    /**
     * This array stores food categories*/
    private String[] foodCategories = {
            "Pizzas",
            "Burgers",
            "Soups",
            "Chicken",
            "Noodles",
            "Rolls",
            "Biryani",
            "Fries",
            "Dals",
            "Waazwaan"};

    private String selectedCategory;
    private double mPhotoUploadProgress = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_home);

        //Hooking all the widgets
        bindViews();

        //Setting up Firebase
        setUpFireBaseAuth();

        //Selecting Image by clicking on the Image View
        mProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filechooser();
            }
        });

        //Uploading the Product by Clicking on the button
        mAddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Attempting to upload the food item", Toast.LENGTH_SHORT).show();
                String productName = mProductName.getText().toString().trim();
                float productPrice = Float.parseFloat(mProductPrice.getText().toString().trim());
                String productDescription = mProductDescription.getText().toString().trim();

                Log.d(TAG, "onClick: "+selectedCategory);

                if(imageUri != null) {
                    FoodItemDao dao = new FoodItemDao(mContext);
                    dao.uploadToFirebase(true,selectedCategory.toLowerCase(),productDescription,productName,imageUri,4.5f,productPrice);
                }else{
                    Toast.makeText(mContext, "Please Select Image", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Assigning functionality to the Back Button
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        

       
    }

    /**
     * This method is used to hook all the widgets*/
    private void bindViews(){
        mContext = PartnerHomeActivity.this;

        firebaseMethods = new FirebaseMethods(mContext);
        mProductName = findViewById(R.id.productName);
        mProductPrice = findViewById(R.id.productPrice);
        mProductCategory = findViewById(R.id.productCategory);
        mProductDescription = findViewById(R.id.productDescription);
        mProductImage = findViewById(R.id.productPhoto);
        mAddProductBtn = findViewById(R.id.addProductButton);
        mBackButton = findViewById(R.id.back_btn);

        // Creating adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, foodCategories);
        adapter.setDropDownViewResource(R.layout.simple_dropdown_spinner_layout);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        mProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = foodCategories[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mProductCategory.setAdapter(adapter);
    }


    /**
     * This method opens up the image type intent
     * and then is used to store the uri of the image that is choosen*/
    private void filechooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData() !=null){
            imageUri = data.getData();

            mProductImage.setImageURI(imageUri);

        }
    }

    /***
     * This section contains all the firebase related Methods*/
    private void setUpFireBaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}