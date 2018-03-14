package __Firebase.FirebaseUtility;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseConstant {

    public static String CLIENT = "Client";
    public static String CLIENT_ID = "ClientID";
    public static String FULL_NAME = "FullName";
    public static String IMAGE_URL = "ImageUrl";
    public static String RATTING = "Ratting";
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
    public static String RIDER_HISTORY = "Rider_History";
    public static String ONLINE_BUSY_RIDE = "OnlineBusyOnRide";
    public static String RIDER_ID = "RiderID";
    public static String IS_RIDER_BUSY_OR_FREE = "IsRiderBusy";
    public static String IS_RIDER_ON_RIDE = "IsRiderOnRide";
    public static String IS_RIDER_ON_LINE = "IsRiderOnline";
    public static String ON_LINE_BUSY_ON_RIDE = "OnlineBusyOnRide";
    public static String CURRENT_RIDING_HISTORY = "CurrentRidingHistoryID";
    public static String NOTIFICATION_TITLE = "You have a ride";
    public static String UNDER_SCORE = "_";
    public static String RIDE_NOTIFICATION = "RIDE_NOTIFICATION";
    public static final String Empty = ("");
    public static String UTF_8 = "UTF-8";
    public static String REQUEST_METHOD = "POST";
    public static String EQUAL = "=";
    public static String AMPERSAND = "&";
    public static String DEVICE_TOKEN = "DeviceToken";
    public static String JOIN = "_";
    public static String CANCEL_RIDE_BY_RIDER = "RideCanceledByRider";
    public static String RIDE_REJECTED_BY_RIDER = "RideRejectedByRider";
    public static String TIME = "time";
    public static String DATE = "date";
    public static String TIME_DATE_PATTERN = "yyyy-M-dd hh:mm:ss";
    public static String APP_SETTINGS_INFO = "AppSettings";

    public static int ONLINE_NOT_BUSY_NO_RIDE = 100;
    public static int ONLINE_BUSY_NO_RIDE = 110;
    public static int ONLINE_BUSY_ON_RIDE = 111;
    public static int OFFLINE_NOT_BUSY_ON_RIDE = 000;
    public static int BUSY_RIDER = 1;
    public static int SET_RIDER_BUSY = 1;
    public static int SET_RIDER_FREE = 0;
    public static int IS_RIDE_START_SET = 1;
    public static int IS_RIDE_START_RESET = 0;
    public static int SET_RIDER_ONLINE = 1;
    public static int SET_RIDER_OFF_ONLINE = 0;
    public static int SET_RIDER_NO_RIDE = 0;
    public static int SET_RIDER_ON_RIDE = 1;
    public static int SET_REQUEST_UPDATE_LOCATION = 1;
    public static int UNSET_REQUEST_UPDATE_LOCATION = 0;
    public static int RIDE_NOT_START = 0;
    public static int RIDE_START = 1;
    public static int RIDE_NOT_FINISHED = 0;
    public static int RIDE_FINISHED = 1;
    public static int RIDE_CANCELED_BY_RIDER = 1;
    public static int RIDE_CANCELED_BY_CLIENT = 2;
    public static int UNKNOWN = -1;
    public static int UPDATE_LOCATION = 0;
    public static int INITIAL_ACCEPTANCE = 1;
    public static int FINAL_ACCEPTANCE = 2;
    public static int HISTORY_CREATED_FOR_THIS_RIDE = 1;
    public static int SET = 1;
    public static int UNSET = 0;
    public static String STRING_ZERO = ("0");
    public static String STRING_ONE = ("1");
    public static String STRING_UNKNOWN = ("-1");


    /*Log Key*/
    public static String NEW_RIDER_CREATE = "NEW_RIDER_CREATE";
    public static String NEW_HISTORY_CREATE = "NEW_RIDER_CREATE";
    public static String NEW_RIDER_ERROR = "NEW_USER_ERROR";
    public static String NEW_HISTORY_ERROR = "NEW_HISTORY_ERROR";
    public static String LOCATION_ERROR = "LOCATION_ERROR";
    public static String RIDER_INFO = "RIDER_INFO";
    public static String ALL_RIDER_LOADED = "ALL_RIDER_LOADED";
    public static String RIDER_LOADED = "RIDER_LOADED";
    public static String HISTORY_LOADED = "HISTORY_LOADED";
    public static String HISTORY_LOADED_ERROR = "HISTORY_LOADED_ERROR";
    public static String HISTORY_ID_ADDED_TO_RIDER = "HISTORY_ID_ADDED_TO_RIDER";
    public static String RIDER_BUSY_OR_FREE = "RIDER_BUSY_OR_FREE";
    public static String RIDER_ONLINE_OR_OFFLINE = "RIDER_ONLINE_OR_OFFLINE";
    public static String RIDER_ON_RIDE_OR_FREE = "RIDER_BUSY_OR_FREE";
    public static String CLIENT_LOADED = "CLIENT_LOADED";
    public static String CLIENT_LOADED_ERROR = "CLIENT_LOADED_ERROR";
    public static String RIDER_LOADED_ERROR = "RIDER_LOADED_ERROR";
    public static String SET_RIDER_110 = "SET_RIDER_110";
    public static String IS_RIDER_BUSY_OR_FREE_ERROR = "IS_RIDER_BUSY_OR_FREE_ERROR";
    public static String FIREBASE_TOKEN = "FIREBASE_TOKEN";
    public static String REQUEST_FOR_ADD_CHILD = "REQUEST_FOR_ADD_CHILD";
    public static String NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE";
    public static String RIDER_ONLINE_BUSY_ON_RIDE = "RIDER_ONLINE_BUSY_ON_RIDE";
    public static String RESET_RIDER_STATUS = "RESET_RIDER_STATUS";
    public static String UPDATE_LOCATION_TIMER = "UPDATE_LOCATION_TIMER";
    public static String DEVICE_TOKEN_UPDATE = "DEVICE_TOKEN_UPDATE";
    public static String RESPONSE_FROM_SERVER = "RESPONSE_FROM_SERVER";
    public static String NOTIFICATION_SEND = "NOTIFICATION_SEND";
    public static String RIDE_STARTED = "RIDE_STARTED";
    public static String FINISHED_RIDE = "FINISHED_RIDE";
    public static String RIDE_CANCELED = "RIDE_CANCELED";
    public static String CURRENT_SERVER_TIME = "CURRENT_SERVER_TIME";
    public static String CANCELED_RIDE_BY_RIDER = "CANCELED_RIDE_BY_RIDER";
    public static String REJECT_RIDE_BY_RIDER = "REJECT_RIDE_BY_RIDER";
    public static String RESPONSE_INITIAL_AC_NOTF = "RESPONSE_INITIAL_AC_NOTF";
    public static String NOTIFICATION_RESPONSE = "NOTIFICATION_RESPONSE";
    public static String FIREBASE_NOT_INITIALIZE = "FIREBASE_NOT_INITIALIZE";
    public static String UPDATE_NAME_IMAGE_URL = "UPDATE_NAME_IMAGE_URL";
    public static String APP_SETTINGS = "APP_SETTINGS";
    public static String APP_SETTINGS_LOADED = "APP_SETTINGS_LOADED";

    public static int CURRENT_VERSION = 1000;
    public static int FORCE_UPDATE_INTERVAL = 20;
    public static int CONSECUTIVE_REQUEST_ACCEPT_INTERVAL = 60000;

    /*Notification Action Type*/
    public static final String RIDER_TO_CLIENT = "3012";
    public static final String GET_CURRENT_TIME = "4001";
    public static final String INITIAL_ACCEPTANCE_NOTIFY_CLIENT = "5012";
    public static final String FINAL_ACCEPTANCE_NOTIFY_CLIENT = "6012";
    public static final String FINISH_RIDE_NOTIFY_CLIENT = "7012";
    public static final String CANCEL_RIDE_NOTIFY_CLIENT = "8012";

    public static final int INT_INITIAL_ACCEPTANCE_NOTIFY_CLIENT = 5012;
    public static final int INT_FINAL_ACCEPTANCE_NOTIFY_CLIENT = 6012;
    public static final int INT_FINISH_RIDE_NOTIFY_CLIENT = 7012;
    public static final int INT_CANCEL_RIDE_NOTIFY_CLIENT = 8012;

    /*Request Type*/
    public static final int INITIAL_AC_OF_RIDE_NOTIFY_CLIENT = 1;
    public static final int FINAL_AC_OF_RIDE_NOTIFY_CLIENT = 2;
    public static final int FINISHED_RIDE_NOTIFY_CLIENT = 3;
    public static final int CANCELED_RIDE_BY_RIDER_NOTIFY_CLIENT = 4;
    public static final int GET_NOTIFICATION_TO_NOTIFY_RIDER = 5;
    public static final int REJECTION_OF_RIDE_NOTIFY_CLIENT = 6;
    public static final int ACCEPT_RIDE = 7;
    public static final int REJECT_RIDE = 8;

    /*Constant Value*/
    public static long ONE_MINUTE_IN_MILLISECOND = 60000;
    public static int ZERO = 0;

    public static long LONG_HISTORY_ID = -100;

    public FirebaseConstant(){
    }
}
