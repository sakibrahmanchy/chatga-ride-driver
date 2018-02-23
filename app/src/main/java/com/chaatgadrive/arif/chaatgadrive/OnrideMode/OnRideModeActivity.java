package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.CostEstimation.CostEstimation;
import com.chaatgadrive.arif.chaatgadrive.InternetConnection.ConnectionCheck;
import com.chaatgadrive.arif.chaatgadrive.InternetConnection.InternetCheckActivity;
import com.chaatgadrive.arif.chaatgadrive.MainActivity;
import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.chaatgamap.GetCurrentLocation;
import com.chaatgadrive.arif.chaatgadrive.models.DistanceModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

import ContactWithFirebase.Main;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
public class OnRideModeActivity extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private GetCurrentLocation getCurrentLocation;
    private ConnectionCheck connectionCheck;
    private ImageView ic_info;
    private NotificationModel notificationModel;
    private LatLng source,destination,currentLatlong;
    private Button startRide;
    private ProgressDialog dialog;
    private NotificationCompat.Builder notification;
    private Button finishRide;
    private InitialAndFinalCostEstimation initialAndFinalCostEstimation;
    private GetDistanceAndDuration getDistanceAndDuration;
    private Handler handler = new Handler();
    private GetDistanceFromMap  getDistanceFromMap;
    private long CurrentTimeInSecond = 0;
    private Main main = null;
    private CostEstimation costEstimation;
    private Date startTime,endTime;
    SharedPreferences pref;
    private SharedPreferences.Editor editor;
    Calendar rightNow = Calendar.getInstance();
    private DistanceModel distanceModel = new DistanceModel();
    private UserInformation userInformation;
    public  static Activity Onridecontext  ;
    private BottomSheetBehavior mBottomSheetBehavior;
    private View bottomSheet;
    private TextView client_phone,client_phone_call_number,clientName,clientRating;
    private  ImageView clientProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onride_mode);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(OnRideModeActivity.this);
        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
        main = new Main(this);
        connectionCheck  = new ConnectionCheck(this);
        initialAndFinalCostEstimation = new InitialAndFinalCostEstimation(this);
        getCurrentLocation = new GetCurrentLocation(this);
        getDistanceFromMap = new GetDistanceFromMap();
        userInformation = new UserInformation(this);
        pref = getApplicationContext().getSharedPreferences("MyPref",0);
        editor =pref.edit();
        costEstimation = new CostEstimation();
        Onridecontext = this;
        startRide = (Button) findViewById(R.id.startBtn);
        finishRide = (Button) findViewById(R.id.finishbtn);
        dialog = new ProgressDialog(OnRideModeActivity.this);
        client_phone =(TextView) findViewById(R.id.phone_number);
        client_phone_call_number =(TextView) findViewById(R.id.contact_with_client);
        clientName = (TextView) findViewById(R.id.client_name);
        clientProfileImage =(ImageView) findViewById(R.id.Client_profile_pic);
        clientRating =(TextView) findViewById(R.id.client_rating);


        bottomSheet = findViewById( R.id.bottom_sheet );
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(150);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setHideable(false);


        dialog.setMessage("Please Wait..");

        startRide.setVisibility(View.VISIBLE);
        finishRide.setVisibility(View.INVISIBLE);
        if(AppConstant.currentRidingHistoryModel !=null){

            AppConstant.CURRENT_HISTORY_ID = (int)AppConstant.currentRidingHistoryModel.HistoryID;
            AppConstant.SOURCE_NAME = AppConstant.currentRidingHistoryModel.StartingLocation.LocationName;
            AppConstant.DESTINATION_NAME =AppConstant.currentRidingHistoryModel.EndingLocation.LocationName;
            AppConstant.SOURCE_LATITUTE = AppConstant.currentRidingHistoryModel.StartingLocation.Latitude;
            AppConstant.SOURCE_LOGITUTE = AppConstant.currentRidingHistoryModel.StartingLocation.Longitude;
            AppConstant.DESTINATION_LATITUTE = AppConstant.currentRidingHistoryModel.EndingLocation.Latitude;
            AppConstant.DESTINATION_LOGITUTE =AppConstant.currentRidingHistoryModel.EndingLocation.Longitude;
            AppConstant.CURRENT_CLIENT_DISCOUNT_ID = (int) AppConstant.currentRidingHistoryModel.DiscountID;
            AppConstant.ON_RIDE_MODE=1;
            AppConstant.CLIENT_NAME = AppConstant.ClientModel.FullName;
            AppConstant.PHONE_NUMBER = AppConstant.ClientModel.PhoneNumber;


            if(AppConstant.currentRidingHistoryModel.IsRideStart==0){
                startRide.setVisibility(View.VISIBLE);
                finishRide.setVisibility(View.GONE);
            }
           else if(AppConstant.currentRidingHistoryModel.IsRideStart !=-1){
                startRide.setVisibility(View.GONE);
                finishRide.setVisibility(View.VISIBLE);
            }
        }

        if(notificationModel.clientId !=0){
            AppConstant.DESTINATION_NAME = notificationModel.destinationName;
            AppConstant.SOURCE_NAME = notificationModel.sourceName;
            AppConstant.SOURCE_LATITUTE =notificationModel.sourceLatitude;
            AppConstant.SOURCE_LOGITUTE = notificationModel.sourceLongitude;
            AppConstant.DESTINATION_LATITUTE = notificationModel.destinationLatitude;
            AppConstant.DESTINATION_LOGITUTE = notificationModel.destinationLongitude;
            AppConstant.CURRENT_CLIENT_DISCOUNT_ID = (int) notificationModel.discountID;
            AppConstant.CLIENT_NAME = notificationModel.clientName;
            AppConstant.PHONE_NUMBER = Long.parseLong(notificationModel.clientPhone);
        }
        if(!connectionCheck.isNetworkConnected()){
            Intent intent = new Intent(OnRideModeActivity.this, InternetCheckActivity.class);
            startActivityForResult(intent,AppConstant.INTERNET_CHECK);
        }
        else if (!connectionCheck.isGpsEnable()){
            connectionCheck.showGPSDisabledAlertToUser();
        }
        else {

            try{

                    getDistanceAndDuration = new GetDistanceAndDuration(this,new LatLng(AppConstant.SOURCE_LATITUTE, AppConstant.SOURCE_LOGITUTE),
                            new LatLng( AppConstant.DESTINATION_LATITUTE ,AppConstant.DESTINATION_LOGITUTE));


            }catch (Exception e){

            }

            AllActionClick();

        }
//        dialog.show();
    }

    void AllActionClick(){


        clientRating.setText("100%");
        clientName.setText(AppConstant.CLIENT_NAME);
        client_phone.setText(AppConstant.PHONE_NUMBER+"");

        startRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!connectionCheck.isNetworkConnected()){
                    Intent intent = new Intent(OnRideModeActivity.this, InternetCheckActivity.class);
                    startActivityForResult(intent,AppConstant.INTERNET_CHECK);
                }
                else if (!connectionCheck.isGpsEnable()){
                    connectionCheck.showGPSDisabledAlertToUser();
                }
                else {

                    startTime = new Date();
                    //noinspection deprecation
                    AppConstant.RIDING_FLAG = 2;
                  //  initialAndFinalCostEstimation.CreateInitialHistory();
                    startRide.setVisibility(View.GONE);
                    finishRide.setVisibility(View.VISIBLE);
                    setTitle("You are in Ride");
                    AppConstant.ON_RIDE_MODE=1;
                    distanceModel.setSourceLat(getCurrentLocation.getLatitude());
                    distanceModel.setDestinationLat(getCurrentLocation.getLongitude());
                    Gson gson = new Gson();

                    String json = gson.toJson(distanceModel);
                    editor.putString("DistanceModel",json);
                    editor.commit();

                    initialAndFinalCostEstimation.UpdateStartRide(AppConstant.CURRENT_HISTORY_ID);
                    AppConstant.PREVIOUS_LATLONG = new LatLng(getCurrentLocation.getLatitude(),getCurrentLocation.getLongitude());

                }
            }
        });

        finishRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!connectionCheck.isNetworkConnected()){
                    Intent intent = new Intent(OnRideModeActivity.this, InternetCheckActivity.class);
                    startActivityForResult(intent, AppConstant.INTERNET_CHECK);
                }
                else if (!connectionCheck.isGpsEnable()){
                    connectionCheck.showGPSDisabledAlertToUser();
                }
                else {
                    AppConstant.IS_RIDE_FINISH = true;
                    new AlertDialog.Builder(OnRideModeActivity.this)
                            .setTitle("Really Exit?")
                            .setMessage("Are you sure you want to finish?")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {

                                    endTime = new Date();

                                    try{
                                        AppConstant.TOTAL_DURATION = ((endTime.getTime() - startTime.getTime())/(1000*60));
                                    }catch ( Exception e){
                                        AppConstant.TOTAL_DURATION=30;
                                    }

                                    try {

                                        initialAndFinalCostEstimation.UpdateFinalHistory(AppConstant.CURRENT_HISTORY_ID,AppConstant.TOTAL_DURATION,AppConstant.TOTAL_DISTANCE,
                                                (int)AppConstant.CURRENT_CLIENT_DISCOUNT_ID, AppConstant.SOURCE_NAME,userInformation.GetRidingDistance().getTotaldistance()+"");
                                        AppConstant.ON_RIDE_MODE=0;


                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                                }
                            }).create().show();
                }
            }
        });

        client_phone_call_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();
            }
        });
    }

    void setUpMap(){
        source = new LatLng(AppConstant.SOURCE_LATITUTE,AppConstant.SOURCE_LOGITUTE);
        destination = new LatLng(AppConstant.DESTINATION_LATITUTE,AppConstant.DESTINATION_LOGITUTE);
        if(connectionCheck.isGpsEnable() && connectionCheck.isNetworkConnected()){
            // Getting URL to the Google Directions API

            try {
                String url = getDirectionsUrl(source, destination);
                DownloadTask downloadTask = new DownloadTask(mMap,source,destination);
                downloadTask.execute(url);

            } catch (Exception e){
                e.printStackTrace();
            }


        }
        else{
            Toast.makeText(OnRideModeActivity.this, "Connection Lost", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            setUpMap();
            Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
    }


    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(OnRideModeActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);


                    }
                }).create().show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode ==0) {
            if (resultCode == RESULT_OK) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.onridemode_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cancel_ride:

                RiderModel riderModel = FirebaseWrapper.getInstance().getRiderModelInstance();
                CurrentRidingHistoryModel currentRidingHistoryModel = FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance();
               if(currentRidingHistoryModel.IsRideStart == -1){

                   new AlertDialog.Builder(OnRideModeActivity.this)
                           .setTitle("Really Exit?")
                           .setMessage("Are you sure you want to Cancel the Ride?")
                           .setNegativeButton(android.R.string.no, null)
                           .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface arg0, int arg1) {
                                   main.ForcedCancelRide(FirebaseConstant.HISTORY_CREATED_FOR_THIS_RIDE);
                                   Intent intent = new Intent(OnRideModeActivity.this, MainActivity.class);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                   startActivity(intent);
                                   finish();
                               }
                           }).create().show();
               }
               else{
                   Toast.makeText(getApplicationContext(),"You Can not Cancel the Ride",Toast.LENGTH_LONG).show();
               }

                return true;
            case R.id.help:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:0"+AppConstant.PHONE_NUMBER)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        AppConstant.ONRIDEMODE_ACTIVITY = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        AppConstant.ONRIDEMODE_ACTIVITY = false;
    }



}

