package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseWrapper;

public class OnRideModeActivity extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private GetCurrentLocation getCurrentLocation;
    private ConnectionCheck connectionCheck;
    private ImageView ic_info;
    private NotificationModel notificationModel;
    private LatLng source,destination;
    private CostEstimation costEstimation;
    private Button startRide;
    private ProgressDialog dialog;
    private NotificationCompat.Builder notification;
    private Button finishRide;
    private Notification note;
    private NotificationManager notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onride_mode);

        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();


        getCurrentLocation = new GetCurrentLocation(this);
        connectionCheck  = new ConnectionCheck(this);
        costEstimation = new CostEstimation();


        ic_info = (ImageView) findViewById(R.id.ic_info);
        startRide = (Button) findViewById(R.id.startBtn);
        finishRide = (Button) findViewById(R.id.finishbtn);
        dialog = new ProgressDialog(OnRideModeActivity.this);
        dialog.setMessage("Please Wait..");
        notification = new NotificationCompat.Builder(OnRideModeActivity.this);
        notification.setAutoCancel(false);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
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
                    AppConstant.RIDING_FLAG=2;
                    notification.setSmallIcon(R.drawable.logo);
                    notification.setTicker("this Chaadga Ride");
                    notification.setContentTitle("You are in Ride");
                    notification.setOnlyAlertOnce(true);
                    notification.setContentText(notificationModel.sourceName + "  To "+notificationModel.destinationName);
                    notification.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);

                    Intent intent = new Intent(OnRideModeActivity.this,OnRideModeActivity.class);
                    PendingIntent pendingIntent =PendingIntent.getActivity(OnRideModeActivity.this,0,intent,0);
                    notification.setContentIntent(pendingIntent);



                    note = notification.build();
                    note.flags = Notification.FLAG_ONGOING_EVENT;
                    notificationManager.notify(AppConstant.NOTIFICATION_ID,note);

                    startRide.setVisibility(View.INVISIBLE);
                    finishRide.setVisibility(View.VISIBLE);
                    setTitle("You are in Ride");
                }

            }
        });


        finishRide.setOnClickListener(new View.OnClickListener() {
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
                    new AlertDialog.Builder(OnRideModeActivity.this)
                            .setTitle("Really Exit?")
                            .setMessage("Are you sure you want to finish ?")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0, int arg1) {
                                    notification.setAutoCancel(true);
                                    notificationManager.cancel(AppConstant.NOTIFICATION_ID);
                                    AppConstant.RIDING_FLAG=1;
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

            String url = getDirectionsUrl(source, destination);
            DownloadTask downloadTask = new DownloadTask(mMap,source,destination);

            downloadTask.execute(url);
             new  GetDistanceAndDuration(this,source,destination);

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


}

