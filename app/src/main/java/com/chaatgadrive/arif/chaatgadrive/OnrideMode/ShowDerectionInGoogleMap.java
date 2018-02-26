package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by Arif on 11/12/2017.
 */

public class ShowDerectionInGoogleMap {

    private  GoogleMap mMap;
    private  PolylineOptions polylineOptions;
    private  MarkerOptions markerOptions;
    private LatLng source,destination;
    ShowDerectionInGoogleMap(GoogleMap mMap, PolylineOptions lineOptions,LatLng src, LatLng dest){
        this.mMap = mMap;
        this.polylineOptions=lineOptions;
        this.source=src;
        this.destination=dest;
        placeDirection();
    }

    public  void placeDirection(){
        mMap.addMarker(new MarkerOptions()
                .position(destination));
        mMap.addMarker(new MarkerOptions()
                .position(source));

        mMap.addPolyline(polylineOptions);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(source).include(destination);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), 30);
        try {
            mMap.animateCamera(cameraUpdate);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
