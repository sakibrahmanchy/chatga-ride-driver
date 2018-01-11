package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.security.acl.LastOwnerException;

/**
 * Created by Arif on 1/11/2018.
 */

public class GetDistanceFromMap {

    private Location Startlocation;
    private Location  Endlocation;

    public GetDistanceFromMap() {
       Startlocation= new Location(

               "GPS");
       Endlocation = new Location("GPS");
    }

    public double getDistance(LatLng startPoint, LatLng endPoint){
        Startlocation.setLatitude(startPoint.latitude);
        Startlocation.setLongitude(startPoint.longitude);
        Endlocation.setLongitude(endPoint.latitude);
        Endlocation.setLongitude(endPoint.longitude);
        double distance = Startlocation.distanceTo(Endlocation);//in meters
        return  distance;
    }
}
