package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.InternetConnection.ConnectionCheck;
import com.chaatgadrive.arif.chaatgadrive.CostEstimation.CostEstimation;
import com.chaatgadrive.arif.chaatgadrive.Dailog.BottomSheetDailogRide;
import com.chaatgadrive.arif.chaatgadrive.InternetConnection.InternetCheckActivity;
import com.chaatgadrive.arif.chaatgadrive.MainActivity;
import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.chaatgamap.ConstentUtilityModel;
import com.chaatgadrive.arif.chaatgadrive.chaatgamap.GetCurrentLocation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ContactWithFirebase.Main;
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
    private Notification note;
    private NotificationManager notificationManager;
    private InitialCostEstimation initialCostEstimation;
    private GetDistanceAndDuration getDistanceAndDuration;
    private Handler handler = new Handler();
    private GetDistanceFromMap  getDistanceFromMap;
    private SetNotificationWhenRideStart setNotificationWhenRideStart;
    private long CurrentTimeInSecond = 0;
    private Main main = null;

    Calendar rightNow = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onride_mode);
        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
        main = new Main(this);

        connectionCheck  = new ConnectionCheck(this);
        initialCostEstimation = new InitialCostEstimation(this);
        getCurrentLocation = new GetCurrentLocation(this);
        getDistanceFromMap = new GetDistanceFromMap();
        setNotificationWhenRideStart = new SetNotificationWhenRideStart(this);
        notification = new NotificationCompat.Builder(this);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        ic_info = (ImageView) findViewById(R.id.ic_info);
        startRide = (Button) findViewById(R.id.startBtn);
        finishRide = (Button) findViewById(R.id.finishbtn);
        dialog = new ProgressDialog(OnRideModeActivity.this);
        dialog.setMessage("Please Wait..");

        startRide.setVisibility(View.VISIBLE);
        finishRide.setVisibility(View.INVISIBLE);

        if(AppConstant.RIDING_FLAG==2){
            startRide.setVisibility(View.INVISIBLE);
            finishRide.setVisibility(View.VISIBLE);
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
                getDistanceAndDuration = new GetDistanceAndDuration(this,new LatLng(notificationModel.sourceLatitude,notificationModel.sourceLongitude),
                        new LatLng(notificationModel.destinationLatitude,notificationModel.destinationLongitude));
            }catch (Exception e){

            }

            initMap();
            AllActionClick();

        }

//        dialog.show();
    }

    void AllActionClick(){
        ic_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialogFragment myBottomSheet = BottomSheetDailogRide.newInstance("Modal Bottom Sheet");
                myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());
            }
        });

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
                    AppConstant.RIDING_FLAG = 2;
                    initialCostEstimation.CreateInitialHistory();
                    startRide.setVisibility(View.INVISIBLE);
                    finishRide.setVisibility(View.VISIBLE);
                    setTitle("You are in Ride");
                    setNotificationWhenRideStart.Notification();
                    AppConstant.PREVIOUS_LATLONG = new LatLng(mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude());
                    MandatoryCall();
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
                                    main.ForcedFinishedRide(000 /*Final cost*/, Pair.create(0d, 0d)/*Final Destination*/);
                                    notification.setAutoCancel(true);
                                    notificationManager.cancel(AppConstant.NOTIFICATION_ID);
                                    AppConstant.RIDING_FLAG = 1;
                                    finish();
                                    Intent intent = new Intent(OnRideModeActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }).create().show();
                }
            }
        });
    }

    void setUpMap(){
        source = new LatLng(notificationModel.sourceLatitude,notificationModel.sourceLongitude);
        destination = new LatLng(notificationModel.destinationLatitude,notificationModel.destinationLongitude);
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
        if (ConstentUtilityModel.mLocationPermissionsGranted && connectionCheck.isGpsEnable() && connectionCheck.isNetworkConnected()) {

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
    }

    private void initMap(){

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(OnRideModeActivity.this);
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
                        finish();
                        Intent intent = new Intent(OnRideModeActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                }).create().show();

    }

    private void MandatoryCall() {


        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                Thread thisThread = Thread.currentThread();
                try {

                    double Currentdistance=0;
                    AppConstant.TOTAL_DURATION +=5;

                    currentLatlong = new LatLng(mMap.getMyLocation().getLongitude(),mMap.getMyLocation().getLongitude());
                    Currentdistance= getDistanceFromMap.getDistance(AppConstant.PREVIOUS_LATLONG,currentLatlong);
                    AppConstant.PREVIOUS_LATLONG = currentLatlong;
                    AppConstant.TOTAL_DISTANCE += (Currentdistance/1000.0);


                    Log.d("Total_Distance  ",AppConstant.TOTAL_DISTANCE+" ");
                    Log.d("Total_Duration",AppConstant.TOTAL_DURATION+" ");

                    if(AppConstant.IS_RIDE_FINISH){

                       return;

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
    }


}

