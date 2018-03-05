package com.chaatgadrive.arif.chaatgadrive.OnLocationChange;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.chaatgadrive.arif.chaatgadrive.OnrideMode.GetDistanceFromMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import ContactWithFirebase.Main;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

public class UpdateLocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private final static int UPDATE_INTERVAL = 1000;//whatever you want
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private GetDistanceFromMap getDistanceFromMap =new GetDistanceFromMap();
    private Main  main = new Main(this);
    public UpdateLocationService() {
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
                Log.d(FirebaseConstant.UPDATE_LOCATION_TIMER, FirebaseConstant.UPDATE_LOCATION_TIMER);
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
