package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

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
    private InitialAndFinalCostEstimation initialAndFinalCostEstimation;
    private GetDistanceAndDuration getDistanceAndDuration;
    private Handler handler = new Handler();
    private GetDistanceFromMap  getDistanceFromMap;
    private long CurrentTimeInSecond = 0;
    private Main main = null;
    private CostEstimation costEstimation;
    private Date startTime,endTime;


    Calendar rightNow = Calendar.getInstance();


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


        costEstimation = new CostEstimation();



        ic_info = (ImageView) findViewById(R.id.ic_info);
        startRide = (Button) findViewById(R.id.startBtn);
        finishRide = (Button) findViewById(R.id.finishbtn);
        dialog = new ProgressDialog(OnRideModeActivity.this);
        dialog.setMessage("Please Wait..");

        startRide.setVisibility(View.VISIBLE);
        finishRide.setVisibility(View.INVISIBLE);
        if(AppConstant.currentRidingHistoryModel !=null){

            AppConstant.CURRENT_HISTORY_ID = (int)AppConstant.currentRidingHistoryModel.HistoryID;
            AppConstant.SOURCE_NAME = AppConstant.currentRidingHistoryModel.Client_History;
            AppConstant.DESTINATION_NAME =AppConstant.currentRidingHistoryModel.Rider_History;
            AppConstant.SOURCE_LATITUTE = AppConstant.currentRidingHistoryModel.StartingLocation.Latitude;
            AppConstant.SOURCE_LOGITUTE = AppConstant.currentRidingHistoryModel.StartingLocation.Longitude;
            AppConstant.DESTINATION_LATITUTE = AppConstant.currentRidingHistoryModel.EndingLocation.Latitude;
            AppConstant.DESTINATION_LOGITUTE =AppConstant.currentRidingHistoryModel.EndingLocation.Longitude;
            AppConstant.ON_RIDE_MODE=1;


            if(AppConstant.currentRidingHistoryModel.IsRideStart==0){
                startRide.setVisibility(View.VISIBLE);
                finishRide.setVisibility(View.INVISIBLE);
            }
            if(AppConstant.currentRidingHistoryModel.IsRideStart !=-1){
                startRide.setVisibility(View.INVISIBLE);
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

                    startTime = new Date();
                    //noinspection deprecation
                    AppConstant.RIDING_FLAG = 2;
                  //  initialAndFinalCostEstimation.CreateInitialHistory();
                    startRide.setVisibility(View.INVISIBLE);
                    finishRide.setVisibility(View.VISIBLE);
                    setTitle("You are in Ride");
                    AppConstant.ON_RIDE_MODE=1;

                    initialAndFinalCostEstimation.UpdateStartRide(AppConstant.CURRENT_HISTORY_ID);
                    AppConstant.PREVIOUS_LATLONG = new LatLng(mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude());
                    MandatoryCall();
                    main.ForcedAcceptanceOfRide(FirebaseConstant.FINAL_ACCEPTANCE);
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
                                                (int)AppConstant.CURRENT_CLIENT_DISCOUNT_ID, AppConstant.SOURCE_NAME, AppConstant.DESTINATION_NAME);
                                        AppConstant.ON_RIDE_MODE=0;

                                    }catch (Exception e){

                                        Intent intent = new Intent(OnRideModeActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        e.printStackTrace();
                                    }

                                }
                            }).create().show();
                }
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
                        finish();
                        Intent intent = new Intent(OnRideModeActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                }).create().show();

    }


    private void MandatoryCall() {


        /*
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
        */
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


}

