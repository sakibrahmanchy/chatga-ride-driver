package com.chaatgadrive.arif.chaatgadrive.AppConstant;

import android.location.Location;
import android.media.MediaPlayer;

import com.google.android.gms.maps.model.LatLng;

import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;

/**
 * Created by Arif on 1/4/2018.
 */

public class AppConstant {
    public static final double BASE_TAKA = 20;
    public static final double PER_KILOMITTER = 10;
    public static final double DURATION_PER_KILOMITTER = 1;
    public static final int NOTIFICATION_ID = 1010;
    public static int RIDING_FLAG = 1;
    public static int INTERNET_CHECK = 0;
    public static String DISTANCE = ("");
    public static String DURATION = ("");
    public static LatLng PREVIOUS_LATLONG = null;
    public static double TOTAL_DISTANCE = 0.0;
    public static boolean IS_RIDE_FINISH = false;
    public static double TOTAL_DURATION = 0;
    public static Location GlobalLocation = null;
    public static boolean mRequestingLocationUpdates = true;
    public static final int UPDATE_INTERVAL_IN_MILLISECONDS = 3000;
    public static final int FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 2000;
    public static int CURRENT_HISTORY_ID = 0;
    public static int CURRENT_CLIENT_DISCOUNT_ID = 0;
    public static int TOTAL_RIDING_COST = 0;
    public static int ON_RIDE_MODE = 0;
    public static double RATING = 0;
    public static int OnOffSwith = 2;
    public static CurrentRidingHistoryModel currentRidingHistoryModel = null;
    public static ClientModel ClientModel = null;
    public static int IS_RIDE = 2;
    public static String SOURCE_NAME = "";
    public static String DESTINATION_NAME = "";
    public static double SOURCE_LATITUTE = 0;
    public static double SOURCE_LOGITUTE = 0;
    public static double DESTINATION_LATITUTE = 0;
    public static double DESTINATION_LOGITUTE = 0;
    public static boolean IS_ALARM = true;

    /*Notification Action and Type*/
    public static final String ACTION_TYPE = ("actionType");
    public static final String CLIENT_ID = ("clientId");

    public static final int RIDE_CANCEL_BY_CLIENT_NOTF = 5021;

    /*Notification Title and Body*/
    public static final String CANCEL_RIDE_BODY = ("Client has canceled the ride");
    public static final String CANCEL_RIDE_TITLE = ("Ride Canceled");
    public static final int GET_SMS_PERMISSION = 1;
    public static String CLIENT_NAME="UNKNOWN";
    public  static long PHONE_NUMBER=0;
    public static boolean REGISTRATION_ACTIVITY = false;
    public static boolean USERCHECK_ACTIVITY = false;
    public static boolean MAIN_ACTIVITY = false;
    public static boolean ONRIDEMODE_ACTIVITY=false;
    public static boolean SHOW_ACTIVITY_FOR_ACCEPT_AND_REJECT=false;
    public static MediaPlayer mediaPlayer;
}
