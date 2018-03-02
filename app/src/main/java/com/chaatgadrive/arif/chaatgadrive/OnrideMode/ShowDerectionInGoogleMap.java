package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    private  Context mContext;
    private LatLng source,destination;
    ShowDerectionInGoogleMap(Context context,GoogleMap mMap, PolylineOptions lineOptions, LatLng src, LatLng dest){
        this.mMap = mMap;
        this.polylineOptions=lineOptions;
        this.source=src;
        this.destination=dest;
        this.mContext =context;
        placeDirection();
    }

    public  void placeDirection(){
        mMap.addMarker(new MarkerOptions().position(destination).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_marker_destination",200,200))).anchor(.5f,.5f));//.icon(BitmapDescriptorFactory.fromBitmap(resizedMarker(200,200) )));
        mMap.addMarker(new MarkerOptions()
                .position(source).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_marker_pickup",400,300))).anchor(.5f,.5f));
        mMap.addPolyline(polylineOptions);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(source).include(destination);

        TypedValue tv = new TypedValue();
        int googleMapPadding=0;
        if (mContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            googleMapPadding = TypedValue.complexToDimensionPixelSize(tv.data,mContext.getResources().getDisplayMetrics());
        }


        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), googleMapPadding+100);


        try {
            mMap.animateCamera(cameraUpdate);
            mMap.setLatLngBoundsForCameraTarget(builder.build());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap decodeResource = BitmapFactory.decodeResource(mContext.getResources(),mContext.getResources().getIdentifier(iconName, "drawable", mContext.getPackageName()));
        return Bitmap.createScaledBitmap(decodeResource, (int) (((double) decodeResource.getWidth()) * .25d), (int) (((double) decodeResource.getHeight()) * .25d), false);
    }

}
