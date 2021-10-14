package com.aumaid.bochihhott.CheckOut;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.aumaid.bochihhott.DAO.AddressDao;
import com.aumaid.bochihhott.DAO.CartDao;
import com.aumaid.bochihhott.DAO.PlacedOrderDao;
import com.aumaid.bochihhott.Firebase.FcmNotificationsSender;
import com.aumaid.bochihhott.Home.HomepageActivity;
import com.aumaid.bochihhott.Models.Address;
import com.aumaid.bochihhott.Models.CartItem_;
import com.aumaid.bochihhott.Models.Order;
import com.aumaid.bochihhott.Models.OrderModel;
import com.aumaid.bochihhott.Models.PlacedOrderModel;
import com.aumaid.bochihhott.R;
import com.aumaid.bochihhott.Utils.StringManipulation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static com.aumaid.bochihhott.Constants.Constants.CHANNEL_ID;

public class SuccessFragment extends Fragment {

    private static final String TAG = "SuccessFragment";

    /**
     * Firebase Stuff*/
    private DatabaseReference mDatabaseReference;
    private CartDao cartDao;
    private PlacedOrderDao placedOrderDao;

    /**
     * Declaring widgets
     */
    private View view;


    /**
     * Declaring Variables
     */
    private String callingFragment;
    private String order_id;
    private Order order;

    /**
     * Declaring Widgets*/
    private ImageView mSuccessIcon;
    private TextView mSuccessMessage;
    private TextView mOrderId;
    private TextView mPleaseWait;
    private Button mTrackOrderBtn;
    private ProgressBar mProgressBar;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_status_screen,container,false);
        bindViews();
        attachButtonListeners();
        placeNewOrder();
        return view;
    }

    /**
     * Test Notification pushing here in the snippet*/
    private void attachButtonListeners(){
        mTrackOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  // generateNotification();
                 // fcm settings for particular user





            }
        });
    }

    /**
     * This is Initialization section*/
    private void bindViews(){
        mSuccessIcon = view.findViewById(R.id.successIcon);
        mSuccessMessage = view.findViewById(R.id.successMessageText);
        mOrderId = view.findViewById(R.id.orderIdTv);
        mProgressBar = view.findViewById(R.id.progressBar);
        mPleaseWait = view.findViewById(R.id.pleaseWaitText);
        mTrackOrderBtn = view.findViewById(R.id.trackOrderBtn);

    }

    /**
     * Step 1:
     * This Method is used to place an Order by receiving the order object from the previous
     * fragment, adding timestamp and order id to it and then forwarding it to get
     * the Fcm tokens for every restaurant_id associated with the order*/
    private void placeNewOrder(){
        //Getting Incomplete Order
        assert getArguments() != null;
        placedOrderDao = new PlacedOrderDao(getActivity());
        order = (Order) getArguments().getSerializable("ORDER");
        Log.d(TAG, "placeNewOrder: Adding Time Stamps to the Order");
        order.setTimestamp(getOrderTime());
        order.setOrder_time(StringManipulation.extractTime(getOrderTime()));
        order_id = placedOrderDao.placeOrder(order);
        getFcmTokens(order.getRes_ids());

    }

    /**
     * Step 2:
     * @param resIds
     * finds fcm token for every res_id inside the res_ids arraylist and then stores each token
     * received into a Hashmap of with res_id as key and tokens as value and then sends notification
     * to the every fcm token by forwarding the Hashmap to the sendOrderNotification() function*/
    private void getFcmTokens(ArrayList<String> resIds){

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("messaging_tokens/restaurants");
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> fcmTokens = new ArrayList<>();
                HashMap<String, String> map = new HashMap<>();
                Log.d(TAG, "onDataChange: Datasnapshot Size:"+snapshot.getChildrenCount());
                for(DataSnapshot ds: snapshot.getChildren()){
                  //Store all fcm tokens into arrayList
                    map.put(ds.getKey(),ds.getValue(String.class));
                    fcmTokens.add(ds.getValue(String.class));
                }
                sendOrderNotifications(map, resIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Step 3:
     * @param map
     * @param restaurantIds
     * This method traverses through the resIds arraylist to get the each resId from the ArrayList
     * then checks the HashMap for every resId to get its value (Which is an Fcm TOken Associated)
     * then calls a new Method to send the notification for every token
     * which is accompanied by clearing the cart and forwarding it to next method*/
    private void sendOrderNotifications(HashMap<String, String> map, ArrayList<String> restaurantIds){

        for(int i=0; i<restaurantIds.size(); i++){
         //   Log.d(TAG, "sendOrderNotifications: "+i+" -> "+tokens.get(i));
            String res_id = restaurantIds.get(i);
            String token = map.get(res_id);
            String item_name = order.getItems().get(i).getItem_name();
            int quantity = order.getItems().get(i).getQuantity();
            sendPushNotificationToTheToken(token,item_name,quantity);
        }
        cartDao = new CartDao(getActivity());
        cartDao.clearCart();
        showWidgets();


    }

    /**
     * This method is used to show UI elements when an order is placed successfully*/
    private void showWidgets(){
        mOrderId.setText("#"+order_id);
        //  mOrderId.setText("#thisisTheOrderId");
        //Log.d(TAG, "showWidgets: Order id: "+order_id);
        mSuccessMessage.animate().alphaBy(1.0f).scaleX(1.0f).scaleYBy(1.0f).setDuration(2000);
        mSuccessIcon.animate().scaleX(1.0f).scaleYBy(1.0f).alphaBy(1.0f).rotationBy(1080f).setDuration(2000);
        mOrderId.animate().scaleXBy(1.0f).scaleYBy(1.0f).alphaBy(1.0f).setDuration(2000);

        mTrackOrderBtn.setVisibility(View.VISIBLE);
        mTrackOrderBtn.animate().scaleX(1.0f).scaleYBy(1.0f).alphaBy(1.0f).setDuration(2000);

        mProgressBar.setVisibility(View.GONE);
        mPleaseWait.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().finish();
            }
        },3000);
    }

    /**
     * TODO: This is unimplemented method for calculating the time of the delivery*/
    private String calculateDeliveryTime(){
        // //Log.d(TAG, "calculateDeliveryTime: Calculating delivery time");
        return "45 minutes";
    }

    /**
     * This method returns a String of just the order time which is then attached to the order*/
    private String getOrderTime(){
        long millis = System.currentTimeMillis();
        java.util.Date date=new java.util.Date(millis);
        return date.toString();
    }

    /**
     * Step 4:
     * This method is used to send the push notification to the provided token with info
     * name of Food items and quantity of those food items in an order*/
    private void sendPushNotificationToTheToken(String token,String foodItemName, int quantity){
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(token,"Order","Please confirm "+quantity+" "+foodItemName,getActivity(),getActivity());
        notificationsSender.SendNotifications();
    }
}
