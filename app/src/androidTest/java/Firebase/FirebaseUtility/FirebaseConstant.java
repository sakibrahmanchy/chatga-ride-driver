package Firebase.FirebaseUtility;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseConstant {

    public static String CLIENT = "Client";
    public static String CLIENT_ID = "ClientID";
    public static String CURRENT_RIDING_HISTORY_ID = "CurrentRidingHistoryID";
    public static String RIDER = "Rider";
    public static String RIDER_LOCATION = "RiderLocation";
    public static String REQUEST_FOR_UPDATE_LOCATION = "RequestForUpdateLocation";
    public static String ENDING_LOCATION = "EndingLocation";
    public static String LONGITUDE = "Longitude";
    public static String LATITUDE = "Latitude";
    public static String COST_SO_FAR = "CostSoFar";
    public static String IS_RIDE_FINISHED = "IsRideFinished";
    public static String IS_RIDE_START = "IsRideStart";
    public static String RIDE_CANCEL_BY_CLIENT = "RideCanceledByClient";
    public static String RIDE_CANCEL_BY_RIDER = "RideCanceledByRider";
    public static String HISTORY = "History";
    public static String HISTORY_ID = "HistoryID";
    public static String ONLINE_BUSY_RIDE = "OnlineBusyOnRide";
    public static String RIDER_ID = "RiderID";
    public static String IS_RIDER_BUSY_OR_FREE = "IsRiderBusy";
    public static String IS_RIDER_ON_RIDE = "IsRiderOnRide";
    public static String IS_RIDER_ON_LINE = "IsRiderOnline";
    public static String ON_LINE_BUSY_ON_RIDE = "OnlineBusyOnRide";
    public static String CURRENT_RIDING_HISTORY = "CurrentRidingHistoryID";
    public static String NOTIFICATION_TITLE = "You have a ride";

    public static int ONLINE_NOT_BUSY_NO_RIDE = 100;
    public static int ONLINE_BUSY_NO_RIDE = 110;
    public static int ONLINE_BUSY_ON_RIDE = 111;
    public static int BUSY_RIDER = 1;
    public static int SET_RIDER_BUSY = 1;
    public static int SET_RIDER_FREE = 0;
    public static int IS_RIDE_START_SET = 1;
    public static int IS_RIDE_START_RESET = 0;

    /*Log Key*/
    public static String NEW_RIDER_ERROR = "NEW_USER_ERROR";
    public static String NEW_HISTORY_ERROR = "NEW_HISTORY_ERROR";
    public static String LOCATION_ERROR = "LOCATION_ERROR";
    public static String RIDER_INFO = "RIDER_INFO";
    public static String ALL_RIDER_LOADED = "ALL_RIDER_LOADED";
    public static String CLIENT_LOADED = "CLIENT_LOADED";
    public static String CLIENT_LOADED_ERROR = "CLIENT_LOADED_ERROR";
    public static String SET_RIDER_110 = "SET_RIDER_110";
    public static String IS_RIDER_BUSY_OR_FREE_ERROR = "IS_RIDER_BUSY_OR_FREE_ERROR";
    public static String FIREBASE_TOKEN = "FIREBASE_TOKEN";
    public static String REQUEST_FOR_ADD_CHILD = "REQUEST_FOR_ADD_CHILD";
    public static String NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE";

    public FirebaseConstant(){
    }
}
