package com.chaatgadrive.arif.chaatgadrive.OnLocationChange;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.widget.Toast;

import com.chaatgadrive.arif.chaatgadrive.ActiveContext;
import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.OnrideMode.GetDistanceFromMap;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.DistanceModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import ContactWithFirebase.Main;
import __Firebase.FirebaseWrapper;

public class UpdateLocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private final static int UPDATE_INTERVAL = 10000;//whatever you want
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    SharedPreferences pref ;
    private SharedPreferences.Editor editor;
    private UserInformation userInformation;
    GetDistanceFromMap getDistanceFromMap;
    DistanceModel distanceModel;
    private Main  main = new Main(this);

    public UpdateLocationService() {
        ActiveContext.activeContext=this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        buildGoogleApiClient();
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(UpdateLocationService.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        pref= getSharedPreferences("MyPref",0);
        editor = pref.edit();
        userInformation = new UserInformation(this);
        getDistanceFromMap  = new GetDistanceFromMap();
        distanceModel = new DistanceModel();

        try{
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, UpdateLocationService.this);

        }catch(SecurityException e){

        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        if(location !=null){
            final Pair newLocation = Pair.create(location.getLatitude(), location.getLongitude());
            if (FirebaseWrapper.getInstance().getRiderModelInstance().RiderID > 0) {
                main.UpdateRiderLocation(
                        FirebaseWrapper.getInstance().getRiderModelInstance(),
                        newLocation
                );

            }

            if(AppConstant.IS_RIDE==1){
                if(userInformation.GetRidingDistance() !=null){
                    LatLng SharePrefLatLon = new LatLng(userInformation.GetRidingDistance().getSourceLat(),userInformation.GetRidingDistance().getSourceLong());
                    LatLng currentLatlong = new LatLng(location.getLatitude(),location.getLongitude());
                    double Currentdistance= getDistanceFromMap.getDistance(currentLatlong,SharePrefLatLon);
                    if(Currentdistance>20.0){
                        distanceModel = userInformation.GetRidingDistance();
                        AppConstant.TOTAL_DISTANCE = distanceModel.getTotaldistance();
                        AppConstant.TOTAL_DISTANCE += (Currentdistance/1000.0);
                        distanceModel.setTotaldistance(AppConstant.TOTAL_DISTANCE);
                        distanceModel.setSourceLat(currentLatlong.latitude);
                        distanceModel.setSourceLong(currentLatlong.longitude);
                        Gson gson = new Gson();
                        String json = gson.toJson(distanceModel);
                        editor.putString("DistanceModel",json);
                        editor.commit();
                    }
                }



            }


        }
      // Toast.makeText(UpdateLocationService.this, "onLocationChanged "+location, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(UpdateLocationService.this, "onConnectionFailed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, UpdateLocationService.this);

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, UpdateLocationService.this);
    }


}
