package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.DistanceModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

/**
 * Created by Arif on 2/19/2018.
 */

public class DistanceCalculationService extends Service {
    private static final String TAG = "BOOMBOOMTESTGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 10000;
    private static final float LOCATION_DISTANCE = 0;
    SharedPreferences pref ;
    private SharedPreferences.Editor editor;
    private UserInformation userInformation;
    GetDistanceFromMap getDistanceFromMap;
    DistanceModel distanceModel;
    private class LocationListener implements android.location.LocationListener
    {
        Location mLastLocation;

        public LocationListener(String provider)
        {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location)
        {
            if(location !=null){
                LatLng SharePrefLatLon = new LatLng(userInformation.GetRidingDistance().getSourceLat(),userInformation.GetRidingDistance().getSourceLong());
                LatLng currentLatlong = new LatLng(location.getLatitude(),location.getLongitude());
                double Currentdistance= getDistanceFromMap.getDistance(currentLatlong,SharePrefLatLon);
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
                Log.e(TAG, "onLocationChanged: " + location);
            }

        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        Log.e(TAG, "onCreate");

        pref= getSharedPreferences("MyPref",0);
        editor = pref.edit();
         userInformation = new UserInformation(this);
         getDistanceFromMap  = new GetDistanceFromMap();
         distanceModel = new DistanceModel();
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }

    }

    @Override
    public void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}
