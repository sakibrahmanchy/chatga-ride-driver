package com.chaatgadrive.arif.chaatgadrive.chaatgamap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaatgadrive.arif.chaatgadrive.InternetConnection.ConnectionCheck;
import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ContactWithFirebase.Main;

/**
 * Created by Arif on 11/27/2017.
 */

public class Mapfragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener {

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Marker mMarcadorActual;
    SupportMapFragment mSupportMapFragment;
    private GoogleMap mMap;
    private String Tag;
    int ZOOM_LEVEL = 15;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public Boolean mLocationPermissionsGranted;
    private GetCurrentLocation getCurrentLocation;
    private ConnectionCheck connectionCheck;
    private UiSettings uiSettings;
    private Main main;
    private ImageView traffic_mode;
    private LoginData loginData;
    private UserInformation userInformation;
    private TextView DriverVerifiedStatus;
    public Mapfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mapview = inflater.inflate(R.layout.activity_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        Tag = ConstentUtilityModel.MapFragment;
        getCurrentLocation = new GetCurrentLocation(getContext());
        userInformation = new UserInformation(getContext());
        loginData = userInformation.getuserInformation();
        main = new Main(getContext());
        DriverVerifiedStatus = mapview.findViewById(R.id.service_not_available);
        if(loginData.getIsVerified()==1){
            DriverVerifiedStatus.setVisibility(View.INVISIBLE);
        }
        else {
            DriverVerifiedStatus.setVisibility(View.VISIBLE);

        }
        connectionCheck  = new ConnectionCheck(getContext());
          getLocationPermission();
        traffic_mode = mapview.findViewById(R.id.traffice_mode);
        traffic_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMap.isTrafficEnabled()){
                    mMap.setTrafficEnabled(false);
                }
                else{
                    mMap.setTrafficEnabled(true);
                }
            }
        });
        mapFragment.getMapAsync(this);
        return mapview;

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //ask for permissions..
        mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getContext(), R.raw.style_json));

        if (connectionCheck.isNetworkConnected()) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
          //  mMap.getUiSettings().setMyLocationButtonEnabled(false);
            uiSettings = googleMap.getUiSettings();
            uiSettings.setMapToolbarEnabled(false);

            googleMap.setTrafficEnabled(false);
            //noinspection deprecation
            mMap.setOnMyLocationChangeListener(this);
         //   init();
            Toast.makeText(getContext(), "Map is Ready", Toast.LENGTH_SHORT).show();
        }

    }



    private void getDeviceLocation(){
        if(connectionCheck.isGpsEnable()){
            moveCamera(new LatLng(getCurrentLocation.getLatitude(), getCurrentLocation.getLongitude()),
                    ConstentUtilityModel.DEFAULT_ZOOM,
                    "My Location");
        }
        else{
              Toast.makeText(getContext(),"GPS OFF",Toast.LENGTH_SHORT).show();
        }

    }

    private void getLocationPermission(){
        Log.d(ConstentUtilityModel.MapFragment, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission((getActivity().getApplicationContext()),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                ConstentUtilityModel.mLocationPermissionsGranted = true;

            }else{
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                       ConstentUtilityModel.LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,
                    ConstentUtilityModel.LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(ConstentUtilityModel.MapFragment, "onRequestPermissionsResult: called.");
        ConstentUtilityModel.mLocationPermissionsGranted = false;

        switch(requestCode){
            case ConstentUtilityModel.LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            ConstentUtilityModel.mLocationPermissionsGranted  = false;
                            Log.d(ConstentUtilityModel.MapFragment, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(ConstentUtilityModel.MapFragment, "onRequestPermissionsResult: permission granted");
                    ConstentUtilityModel.mLocationPermissionsGranted  = true;
                    //initialize our map

                }
            }
        }
    }
    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(ConstentUtilityModel.MapFragment, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }


    }

    @Override
    public void onMyLocationChange(Location location) {
        /*
        if(location !=null){
            final Pair newLocation = Pair.create(location.getLatitude(), location.getLongitude());
            if (FirebaseWrapper.getInstance().getRiderModelInstance().RiderID > 0) {
                main.UpdateRiderLocation(
                        FirebaseWrapper.getInstance().getRiderModelInstance(),
                        newLocation
                );
                Log.d(FirebaseConstant.UPDATE_LOCATION_TIMER, FirebaseConstant.UPDATE_LOCATION_TIMER);
            }
        }
        */

    }
}
