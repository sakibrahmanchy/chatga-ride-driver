package com.chaatgadrive.arif.chaatgadrive.AppConstant;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Arif on 1/4/2018.
 */

public class AppConstant {
    public static final double BASE_TAKA = 20;
    public static final double PER_KILOMITTER = 10;
    public static final double DURATION_PER_KILOMITTER = 1;
    public static final int NOTIFICATION_ID = 1010;
    public  static int RIDING_FLAG = 1;
    public static  int INTERNET_CHECK = 0;
    public static String DISTANCE = ("");
    public static String DURATION = ("");
    public static LatLng PREVIOUS_LATLONG = null;
    public static double TOTAL_DISTANCE = 0.0;
    public static boolean IS_RIDE_FINISH = false;
    public static double TOTAL_DURATION = 0;
    public static Location GlobalLocation = null;
    public static boolean mRequestingLocationUpdates =true;
    public  static final int UPDATE_INTERVAL_IN_MILLISECONDS =3000;
    public static final int FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =2000;

}
