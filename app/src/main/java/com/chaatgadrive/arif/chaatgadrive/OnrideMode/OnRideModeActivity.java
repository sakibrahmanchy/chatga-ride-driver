package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaatgadrive.arif.chaatgadrive.ConnectionCheck;
import com.chaatgadrive.arif.chaatgadrive.CostEstimation.CostEstimation;
import com.chaatgadrive.arif.chaatgadrive.Dailog.BottomSheetDailogRide;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onride_mode);

        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
        getCurrentLocation = new GetCurrentLocation(this);
        connectionCheck  = new ConnectionCheck(this);
        costEstimation = new CostEstimation();

        ic_info = (ImageView) findViewById(R.id.ic_info);
        initMap();

        AllActionClick();

    }

    void AllActionClick(){
        ic_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialogFragment myBottomSheet = BottomSheetDailogRide.newInstance("Modal Bottom Sheet");
                myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());
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
             new  GetDistanceAndDuration(source,destination);

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


}

