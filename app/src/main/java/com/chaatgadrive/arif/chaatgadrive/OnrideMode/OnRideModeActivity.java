package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import ContactWithFirebase.Main;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallBackCurrentServerTime;

public class OnRideModeActivity extends AppCompatActivity implements OnMapReadyCallback, ICallBackCurrentServerTime {


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
    private LinearLayout AccepAndReject;
    private FrameLayout StartAndFinish;
    private NestedScrollView bootmsheet;
    private ImageView clientImage;
    private TextView accepRide,rejectRide,sourceAdress,destinationAdress,totalCost,dateTime,Currentclient_Name;
    private ImageView navigate;


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
        accepRide = (TextView) findViewById(R.id.accept_ride);
        rejectRide =(TextView) findViewById(R.id.reject_ride);
        sourceAdress =(TextView) findViewById(R.id.source_address);
        destinationAdress =(TextView) findViewById(R.id.destination_address);
        totalCost = (TextView) findViewById(R.id.total_fare);
        dateTime =(TextView) findViewById(R.id.date_time);
        Currentclient_Name =(TextView) findViewById(R.id.current_client_name);
        clientImage = (ImageView) findViewById(R.id.client_image);
        navigate = (ImageView) findViewById(R.id.ic_navigate);



        bottomSheet = findViewById( R.id.bottom_sheet );
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(150);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setHideable(false);
        AccepAndReject =(LinearLayout) findViewById(R.id.accep_reject_card_layout);
        StartAndFinish = (FrameLayout) findViewById(R.id.start_and_finish_layout);
        bootmsheet =(NestedScrollView ) findViewById(R.id.bottom_sheet);

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
            clientRating.setText( notificationModel.clientRatting);
            Picasso.with(this).invalidate(notificationModel.clientImageUrl);
            Picasso.with(this)
                    .load(notificationModel.clientImageUrl)
                    .placeholder(R.drawable.profile_image)
                    .error(R.drawable.profile_image)
                    .into(clientProfileImage);
        }

        if( AppConstant.SHOW_ACTIVITY_FOR_ACCEPT_AND_REJECT){
            StartAndFinish.setVisibility(View.GONE);
            bootmsheet.setVisibility(View.GONE);
            sourceAdress.setText(notificationModel.sourceName);
            Currentclient_Name.setText(notificationModel.clientName);
                   totalCost.setText("Estimated: "+notificationModel.totalCost +"Tk");
            Picasso.with(this).invalidate(notificationModel.clientImageUrl);
            Picasso.with(this)
                    .load(notificationModel.clientImageUrl)
                    .placeholder(R.drawable.profile_image)
                    .error(R.drawable.profile_image)
                    .into(clientImage);
            destinationAdress.setText(notificationModel.destinationName);
            accepRide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initialAndFinalCostEstimation.CreateInitialHistory();

                }
            });

            rejectRide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RejectRide();
                    finish();
                }
            });

        }
        else{

            AccepAndReject.setVisibility(View.GONE);
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
                clientRating.setText( AppConstant.ClientModel.Ratting);
//                Intent intent = new Intent(Onridecontext,DistanceCalculationService.class);
//                startService(intent);
                Picasso.with(this).invalidate(AppConstant.ClientModel.ImageUrl);
                Picasso.with(this)
                        .load(AppConstant.ClientModel.ImageUrl)
                        .placeholder(R.drawable.profile_image)
                        .error(R.drawable.profile_image)
                        .into(clientProfileImage);

                if(AppConstant.currentRidingHistoryModel.IsRideStart==0){
                    startRide.setVisibility(View.VISIBLE);
                    finishRide.setVisibility(View.GONE);
                }
                else if(AppConstant.currentRidingHistoryModel.IsRideStart !=-1){
                    startRide.setVisibility(View.GONE);
                    finishRide.setVisibility(View.VISIBLE);
                }
            }

        }
        AllActionClick();


    }

    void AllActionClick(){



        clientName.setText(AppConstant.CLIENT_NAME);
        client_phone.setText("+880"+AppConstant.PHONE_NUMBER+"");

        startRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(OnRideModeActivity.this,R.style.CustomDialogTheme)
                        .setTitle("RIDE START")
                        .setMessage("Are you sure  want to start ride?")
                        .setNegativeButton("NO", null)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                try {

                                    setTitle("You are in Ride");
                                    AppConstant.ON_RIDE_MODE=1;
                                    distanceModel.setSourceLat(getCurrentLocation.getLatitude());
                                    distanceModel.setSourceLong(getCurrentLocation.getLongitude());
                                    editor.remove("DistanceModel");
                                    Gson gson = new Gson();
                                    String json = gson.toJson(distanceModel);
                                    editor.putString("DistanceModel",json);
                                    editor.commit();
                                    initialAndFinalCostEstimation.UpdateStartRide(AppConstant.CURRENT_HISTORY_ID);
                                    AppConstant.PREVIOUS_LATLONG = new LatLng(getCurrentLocation.getLatitude(),getCurrentLocation.getLongitude());

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }).create().show();

                }

        });

        finishRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!connectionCheck.isNetworkConnected()){
                    Intent intent = new Intent(OnRideModeActivity.this, InternetCheckActivity.class);
                    startActivityForResult(intent, AppConstant.INTERNET_CHECK);
                }
                else {
                    new AlertDialog.Builder(OnRideModeActivity.this,R.style.CustomDialogTheme)
                            .setTitle("Really Exit?")
                            .setMessage("Are you sure you want to finish?")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    try {
                                        initialAndFinalCostEstimation.UpdateFinalHistory(AppConstant.CURRENT_HISTORY_ID,0,AppConstant.TOTAL_DISTANCE,
                                                (int)AppConstant.CURRENT_CLIENT_DISCOUNT_ID, AppConstant.SOURCE_NAME,AppConstant.DESTINATION_NAME);
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

        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+AppConstant.SOURCE_LATITUTE+","+AppConstant.SOURCE_LOGITUTE+"&daddr="+AppConstant.DESTINATION_LATITUTE+","+AppConstant.DESTINATION_LOGITUTE;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(Intent.createChooser(intent,"Chatga Ride"));
            }
        });
    }

    void setUpMap(){

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            public void onMapLoaded() {
                //do stuff here
                source = new LatLng(AppConstant.SOURCE_LATITUTE,AppConstant.SOURCE_LOGITUTE);
                destination = new LatLng(AppConstant.DESTINATION_LATITUTE,AppConstant.DESTINATION_LOGITUTE);
                if(connectionCheck.isNetworkConnected()){
                    // Getting URL to the Google Directions API

                    try {
//                        String url = getDirectionsUrl(source, destination);
//                        DownloadTask downloadTask = new DownloadTask(OnRideModeActivity.this, mMap,source,destination);
//                        downloadTask.execute(url);

                        mMap.addMarker(new MarkerOptions().position(destination).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_marker_destination",200,200))).anchor(.5f,.5f));//.icon(BitmapDescriptorFactory.fromBitmap(resizedMarker(200,200) )));
                        mMap.addMarker(new MarkerOptions()
                                .position(source).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_marker_pickup",400,300))).anchor(.5f,.5f));

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        builder.include(source).include(destination);
                        TypedValue tv = new TypedValue();
                        int googleMapPadding=0;
                        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
                        {
                            googleMapPadding = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
                        }


                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), googleMapPadding+100);
                        mMap.animateCamera(cameraUpdate);

                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }
                else{
                    Toast.makeText(OnRideModeActivity.this, "Connection Lost", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                CurrentRidingHistoryModel currentRidingHistoryModel = FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance();
               if(currentRidingHistoryModel.IsRideStart == -1){
                   new AlertDialog.Builder(OnRideModeActivity.this,R.style.CustomDialogTheme)
                           .setTitle("CANCEL RIDE ")
                           .setMessage("Are you sure  want to cancel ride?")
                           .setNegativeButton("NO", null)
                           .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface arg0, int arg1) {
                                   try {
                                       main.ForcedCancelRide(FirebaseConstant.HISTORY_CREATED_FOR_THIS_RIDE);
                                       Intent intent = new Intent(OnRideModeActivity.this, MainActivity.class);
                                       startActivity(intent);
                                       finish();
                                   }catch (Exception e){
                                       e.printStackTrace();
                                   }
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



    private void RejectRide() {
        GetTime(FirebaseConstant.REJECT_RIDE);
    }

    private void GetTime(int type) {
        FirebaseUtilMethod.getNetworkTime(type, this, this);
    }

    @Override
    public void OnResponseServerTime(long Time, int type) {
        if (Time > 0) {
            if (Math.abs(FirebaseWrapper.getInstance().getNotificationModelInstance().time - Time) <= FirebaseConstant.ONE_MINUTE_IN_MILLISECOND) {
                switch (type) {
                    case FirebaseConstant.REJECT_RIDE: {
                        new Main(this).ForcedRejectedRide(notificationModel.clientId);
                        Onridecontext.finish();
                        break;
                    }
                    case FirebaseConstant.ACCEPT_RIDE: {
                        /* Not use yet */
                        new Main(this).ForcedAcceptanceOfRide(FirebaseConstant.INITIAL_ACCEPTANCE);
                        break;
                    }
                }
            }
        }
    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        return Bitmap.createScaledBitmap(decodeResource, (int) (((double) decodeResource.getWidth()) * .25d), (int) (((double) decodeResource.getHeight()) * .25d), false);
    }
}

