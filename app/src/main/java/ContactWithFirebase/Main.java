package ContactWithFirebase;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.chaatgamap.GetCurrentLocation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.chaatgadrive.arif.chaatgadrive.models.HistoryModel.RiderHistory;

import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseRequest.__FirebaseRequest;
import __Firebase.FirebaseResponse.FirebaseResponse;
import __Firebase.FirebaseResponse.RiderInRideMode;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallBackCurrentServerTime;
import __Firebase.ICallbacklisteners.ICallbackMain;

/**
 * Created by User on 12/8/2017.
 */

public class Main implements ICallbackMain, ICallBackCurrentServerTime {

    private GetCurrentLocation getCurrentLocation;
    private FirebaseWrapper firebaseWrapper = null;
    private RiderModel riderModel = null;
    private ClientModel clientModel = null;
    private CurrentRidingHistoryModel currentRidingHistoryModel = null;
    private __FirebaseRequest FirebaseRequestInstance;
    private Context context = null;

    private static long FinalCost;
    private static Pair<Double, Double> FinalLocation;

    public Main(Context context) {
        this.context = context;

    }

    public boolean IsRiderAlreadyCreated(RiderModel RiderModel) {

        firebaseWrapper = FirebaseWrapper.getInstance();
        FirebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        FirebaseRequestInstance.IsRiderAlreadyCreated(RiderModel, Main.this);
        return true;
    }

    public boolean CreateNewRiderFirebase(/*Main Rider Mode*/LoginData loginData, String phoneNumber) {

        getCurrentLocation = new GetCurrentLocation(context);
        firebaseWrapper = FirebaseWrapper.getInstance();
        riderModel = firebaseWrapper.getRiderModelInstance();
        FirebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        riderModel.RiderID = Long.parseLong(loginData.getRiderId());
        riderModel.FullName = loginData.getFirstName();
        riderModel.PhoneNumber = Long.parseLong(phoneNumber);
        riderModel.CurrentRiderLocation = new RiderModel.RiderLocation(
                getCurrentLocation.getLatitude(),
                getCurrentLocation.getLongitude(),
                FirebaseConstant.UNSET_REQUEST_UPDATE_LOCATION
        );

        riderModel.DeviceToken = FirebaseWrapper.getDeviceToken();
        riderModel.CurrentRidingHistoryID = FirebaseConstant.UNKNOWN;
        riderModel.DistanceFromClient = FirebaseConstant.UNKNOWN;
        riderModel.IsRiderBusy = FirebaseConstant.SET_RIDER_FREE;
        riderModel.IsRiderOnline = FirebaseConstant.SET_RIDER_ONLINE;
        riderModel.IsRiderOnRide = FirebaseConstant.SET_RIDER_NO_RIDE;
        riderModel.OnlineBusyOnRide = FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE;

        this.IsRiderAlreadyCreated(riderModel);
        return true;
    }

    public boolean CreateNewHistoryModelFirebase(RiderHistory riderHistory) {

        if (riderHistory == null || riderHistory.HistoryID < 1) return false;

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = FirebaseWrapper.getInstance().getRiderModelInstance();
        this.clientModel = FirebaseWrapper.getInstance().getClientModelInstance();
        this.currentRidingHistoryModel = firebaseWrapper.getRidingHistoryModelModelInstance();
        FirebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();

        this.currentRidingHistoryModel.HistoryID = riderHistory.HistoryID;
        this.currentRidingHistoryModel.ClientID = riderHistory.ClientID;
        this.currentRidingHistoryModel.RiderID = riderHistory.RiderID;
        this.currentRidingHistoryModel.Client_History = riderHistory.Client_History;
        this.currentRidingHistoryModel.Rider_History = riderHistory.Rider_History;
        this.currentRidingHistoryModel.StartingLocation = new CurrentRidingHistoryModel.Location(
                riderHistory.StartLocation.latitude,
                riderHistory.StartLocation.longitude
        );

        this.currentRidingHistoryModel.EndingLocation = new CurrentRidingHistoryModel.Location(
                riderHistory.EndLocation.latitude,
                riderHistory.EndLocation.longitude
        );

        this.currentRidingHistoryModel.CostSoFar = riderHistory.CostSoFar;

        this.currentRidingHistoryModel.IsRideStart = FirebaseConstant.UNKNOWN;
        this.currentRidingHistoryModel.IsRideFinished = FirebaseConstant.UNKNOWN;
        this.currentRidingHistoryModel.RideCanceledByClient = FirebaseConstant.UNKNOWN;
        this.currentRidingHistoryModel.RideCanceledByRider = FirebaseConstant.UNKNOWN;

        FirebaseRequestInstance.CreateNewRideHistory(this.currentRidingHistoryModel, Main.this);
        return true;
    }

    public boolean GetRiderStatus(long RiderId) {

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.getFirebaseRequestInstance().GetRiderStatus(RiderId, Main.this);
        return true;
    }

    public boolean RideRejectedByRider(long ClientId, long RiderId, long Time) {

        if (ClientId == 0 || Time == 0 || RiderId == 0) return false;

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.getFirebaseRequestInstance().RideRejectedByRider(ClientId, RiderId, Time, Main.this);
        return true;
    }

    public boolean GetCurrentRider(long RiderId) {
        if (RiderId < 0) return false;

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.getFirebaseRequestInstance().GetCurrentRider(RiderId, Main.this);
        return true;
    }

    public boolean HasAnyRide(long RiderID) {

        if (RiderID < 1) return false;
        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.getFirebaseRequestInstance().HasAnyRide(RiderID, Main.this);
        return true;
    }

    public boolean GetRecentHistory(long HistoryId) {

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.getFirebaseRequestInstance().GetRecentHistory(HistoryId, Main.this);
        return true;
    }

    public boolean SetRiderBusyOrFree(/*Firebase Rider Model*/ RiderModel riderModel, int value) {

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = riderModel;

        if (value == FirebaseConstant.SET_RIDER_FREE) {
            this.riderModel.IsRiderBusy = FirebaseConstant.SET_RIDER_FREE;
        } else {
            this.riderModel.IsRiderBusy = FirebaseConstant.SET_RIDER_BUSY;
        }

        firebaseWrapper.getFirebaseRequestInstance().SetRiderBusyOrFree(this.riderModel, Main.this);
        return true;
    }

    public boolean SetRiderOnRideOrFree(/*Firebase Rider Model*/ RiderModel riderModel, int value) {

        if (riderModel == null || riderModel.RiderID < 1 || value < 0) return false;

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = riderModel;

        if (value == FirebaseConstant.SET_RIDER_NO_RIDE) {
            this.riderModel.IsRiderOnRide = FirebaseConstant.SET_RIDER_NO_RIDE;
        } else {
            this.riderModel.IsRiderOnRide = FirebaseConstant.SET_RIDER_ON_RIDE;
        }

        firebaseWrapper.getFirebaseRequestInstance().SetRiderOnRideOrFree(this.riderModel, Main.this);

        return true;
    }

    public boolean SetRiderOnLineOrOffLine(/*Firebase Rider Model*/ RiderModel riderModel, int value) {

        if (riderModel == null || riderModel.RiderID < 1) return false;

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = riderModel;

        if (value == FirebaseConstant.SET_RIDER_ONLINE) {
            this.riderModel.IsRiderOnline = FirebaseConstant.SET_RIDER_ONLINE;
        } else {
            this.riderModel.IsRiderOnline = FirebaseConstant.SET_RIDER_OFF_ONLINE;
        }

        firebaseWrapper.getFirebaseRequestInstance().SetRiderOnLineOrOffLine(this.riderModel, Main.this);

        return true;
    }

    public boolean SetRiderOnlineBusyOnRider(/*Firebase Rider Model*/ RiderModel riderModel, int value) {

        if (riderModel == null || riderModel.RiderID < 1 || value < 0) return false;

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = riderModel;

        if (value == FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE) {
            this.riderModel.OnlineBusyOnRide = FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE;
        } else if (value == FirebaseConstant.ONLINE_BUSY_NO_RIDE) {
            this.riderModel.OnlineBusyOnRide = FirebaseConstant.ONLINE_BUSY_NO_RIDE;
        } else if (value == FirebaseConstant.ONLINE_BUSY_ON_RIDE) {
            this.riderModel.OnlineBusyOnRide = FirebaseConstant.ONLINE_BUSY_ON_RIDE;
        } else {
            this.riderModel.OnlineBusyOnRide = FirebaseConstant.OFFLINE_NOT_BUSY_ON_RIDE;
        }

        firebaseWrapper.getFirebaseRequestInstance().SetRiderOnlineBusyOnRider(this.riderModel, Main.this);
        return true;
    }

    public boolean SetHistoryIDonRiderTable(/*Firebase Rider Model*/ CurrentRidingHistoryModel historyModel, RiderModel riderModel) {

        if (historyModel == null || riderModel == null) return false;
        if (historyModel.HistoryID < 0 || riderModel.RiderID < 1) return false;

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = riderModel;
        this.currentRidingHistoryModel = historyModel;

        firebaseWrapper.getFirebaseRequestInstance().SetHistoryIDonRiderTable(this.currentRidingHistoryModel, this.riderModel, Main.this);
        return true;
    }

    public boolean SentNotificationToClient(/*Firebase Rider Mode*/RiderModel Rider, String clientDeviceToken) {

        if (Rider == null || Rider.RiderID < 1 || FirebaseUtilMethod.IsEmptyOrNull(clientDeviceToken))
            return false;

        this.riderModel = Rider;
        firebaseWrapper = FirebaseWrapper.getInstance();

        firebaseWrapper.getFirebaseRequestInstance().SentNotificationToClient(this.riderModel, clientDeviceToken, Main.this);
        return true;
    }

    public boolean SetDeviceTokenToRiderTable(/*Firebase Rider Model*/ RiderModel riderModel, String deviceToken) {

        if (riderModel == null || riderModel.RiderID < 1 || FirebaseUtilMethod.IsEmptyOrNull(deviceToken))
            return false;

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = riderModel;
        this.riderModel.DeviceToken = deviceToken;

        firebaseWrapper.getFirebaseRequestInstance().SetDeviceTokenToRiderTable(this.riderModel, Main.this);
        return true;
    }

    public boolean UpdateRiderLocation(/*Firebase Rider Model*/ RiderModel riderModel, Pair<Double, Double> newLocation) {

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = riderModel;

        this.riderModel.CurrentRiderLocation.Latitude = newLocation.first;
        this.riderModel.CurrentRiderLocation.Longitude = newLocation.second;
        this.riderModel.CurrentRiderLocation.RequestForUpdateLocation = FirebaseConstant.UPDATE_LOCATION;

        firebaseWrapper.getFirebaseRequestInstance().UpdateRiderLocation(this.riderModel, Main.this);

        return true;
    }

    public boolean GetCurrentClient(long ClientId) {

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.getFirebaseRequestInstance().GetCurrentClient(ClientId, Main.this);
        return true;
    }

    public boolean CancelRideByRider(/* Firebase HistoryModel, RiderModel */ CurrentRidingHistoryModel HistoryModel, RiderModel Rider, long Time) {

        if (HistoryModel == null || HistoryModel.HistoryID < 1 || Rider == null || Rider.RiderID < 1 || Time <= 0)
            return false;

        firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = Rider;
        this.currentRidingHistoryModel = HistoryModel;

        this.currentRidingHistoryModel.RideCanceledByRider = Time;

        firebaseWrapper.getFirebaseRequestInstance().CancelRideByRider(currentRidingHistoryModel, riderModel, Main.this);
        return true;
    }

    public boolean SetHistoryIDToClient(/* Firebase HistoryModel, RiderModel */ CurrentRidingHistoryModel HistoryModel, ClientModel Client) {

        if (HistoryModel == null || HistoryModel.HistoryID < 1 || Client == null || Client.ClientID < 1)
            return false;

        FirebaseUtilMethod.getNetworkTime(FirebaseConstant.INITIAL_AC_OF_RIDE_NOTIFY_CLIENT, this.context, this);
        return true;
    }

    public boolean ResetRiderStatus(/* Firebase HistoryModel, RiderModel */ RiderModel Rider) {

        if (Rider == null || Rider.RiderID < 1) return false;
        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = Rider;

        this.riderModel.IsRiderOnline = FirebaseConstant.SET_RIDER_ONLINE;
        this.riderModel.IsRiderOnRide = FirebaseConstant.SET_RIDER_NO_RIDE;
        this.riderModel.IsRiderBusy = FirebaseConstant.SET_RIDER_FREE;
        this.riderModel.OnlineBusyOnRide = FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE;

        firebaseWrapper.getFirebaseRequestInstance().ResetRiderStatus(riderModel, Main.this);
        return true;
    }

    public boolean InitialAcceptanceOfRide(/* Firebase HistoryModel, RiderModel */ RiderModel Rider) {

        if (Rider == null || Rider.RiderID < 1)
            return false;

        firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = Rider;

        this.riderModel.IsRiderBusy = FirebaseConstant.SET_RIDER_BUSY;
        this.riderModel.OnlineBusyOnRide = FirebaseConstant.ONLINE_BUSY_NO_RIDE;
        this.riderModel.CurrentRidingHistoryID = FirebaseConstant.UNKNOWN;

        firebaseWrapper.getFirebaseRequestInstance().InitialAcceptanceOfRide(riderModel, Main.this);
        return true;
    }

    public boolean FinalAcceptanceOfRide(/* Firebase HistoryModel, RiderModel */ CurrentRidingHistoryModel HistoryModel, RiderModel Rider) {

        if (HistoryModel == null || HistoryModel.HistoryID < 1 || Rider == null || Rider.RiderID < 1)
            return false;

        firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = Rider;
        this.currentRidingHistoryModel = HistoryModel;

        this.riderModel.IsRiderOnRide = FirebaseConstant.SET_RIDER_ON_RIDE;
        this.riderModel.IsRiderBusy = FirebaseConstant.SET_RIDER_BUSY;
        this.riderModel.OnlineBusyOnRide = FirebaseConstant.ONLINE_BUSY_ON_RIDE;

        FirebaseUtilMethod.getNetworkTime(FirebaseConstant.FINAL_AC_OF_RIDE_NOTIFY_CLIENT, this.context, this);
        return true;
    }

    public boolean FinishedRide(/* Firebase HistoryModel, RiderModel */ CurrentRidingHistoryModel HistoryModel, RiderModel Rider, /*Server*/long FinalCost, /*Local*/ Pair<Double, Double> FinalLocation, long Time) {

        firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = Rider;
        this.currentRidingHistoryModel = HistoryModel;

        this.currentRidingHistoryModel.IsRideFinished = Time;
        this.currentRidingHistoryModel.CostSoFar = FinalCost;
        this.currentRidingHistoryModel.HistoryID = FirebaseConstant.UNKNOWN;
        this.currentRidingHistoryModel.EndingLocation.Latitude = FinalLocation.first;
        this.currentRidingHistoryModel.EndingLocation.Longitude = FinalLocation.second;

        firebaseWrapper.getFirebaseRequestInstance().FinishedRide(currentRidingHistoryModel, Main.this);
        return true;
    }

    /*Accept Ride*/
    public boolean ForcedAcceptanceOfRide(int value) {

        if (value == FirebaseConstant.FINAL_ACCEPTANCE) {
            this.FinalAcceptanceOfRide(
                    FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance(),
                    FirebaseWrapper.getInstance().getRiderModelInstance()
            );
        } else if (value == FirebaseConstant.INITIAL_ACCEPTANCE) {
            this.InitialAcceptanceOfRide(
                    FirebaseWrapper.getInstance().getRiderModelInstance()
            );
        }
        return true;
    }

    /*Cancel Ride*/
    public boolean ForcedCancelRide(int value) {

        if (value == FirebaseConstant.HISTORY_CREATED_FOR_THIS_RIDE) {
            this.SetRiderBusyOrFree(
                    FirebaseWrapper.getInstance().getRiderModelInstance(),
                    FirebaseConstant.SET_RIDER_FREE
            );
            this.SetRiderOnlineBusyOnRider(
                    FirebaseWrapper.getInstance().getRiderModelInstance(),
                    FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE
            );
            FirebaseUtilMethod.getNetworkTime(FirebaseConstant.CANCELED_RIDE_BY_RIDER_NOTIFY_CLIENT, this.context, this);
        }

        return true;
    }

    /*Final Finish*/
    public boolean ForcedFinishedRide(final long finalCost, final Pair<Double, Double> finalLocation) {

        this.SetRiderOnRideOrFree(
                FirebaseWrapper.getInstance().getRiderModelInstance(),
                FirebaseConstant.SET_RIDER_NO_RIDE
        );
        this.SetRiderBusyOrFree(
                FirebaseWrapper.getInstance().getRiderModelInstance(),
                FirebaseConstant.SET_RIDER_FREE
        );
        this.SetRiderOnlineBusyOnRider(
                FirebaseWrapper.getInstance().getRiderModelInstance(),
                FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE
        );
        FirebaseWrapper.getInstance().getRiderModelInstance().CurrentRidingHistoryID = FirebaseConstant.ZERO;
        this.SetHistoryIDonRiderTable(
                FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance(),
                FirebaseWrapper.getInstance().getRiderModelInstance()
        );
        FinalCost = finalCost;
        FinalLocation = finalLocation;
        FirebaseUtilMethod.getNetworkTime(FirebaseConstant.FINISHED_RIDE_NOTIFY_CLIENT, this.context, this);
        return true;
    }

    /*Refresh Rider*/
    public boolean ForcedRefreshRider() {

        this.SetRiderOnRideOrFree(
                FirebaseWrapper.getInstance().getRiderModelInstance(),
                FirebaseConstant.SET_RIDER_NO_RIDE
        );
        this.SetRiderBusyOrFree(
                FirebaseWrapper.getInstance().getRiderModelInstance(),
                FirebaseConstant.SET_RIDER_FREE
        );
        this.SetRiderOnLineOrOffLine(
                FirebaseWrapper.getInstance().getRiderModelInstance(),
                FirebaseConstant.SET_RIDER_ONLINE
        );
        this.SetRiderOnlineBusyOnRider(
                FirebaseWrapper.getInstance().getRiderModelInstance(),
                FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE
        );
        return true;
    }

    public boolean ForcedRejectedRide(long ClientID) {
        FirebaseUtilMethod.getNetworkTime(FirebaseConstant.REJECTION_OF_RIDE_NOTIFY_CLIENT, this.context, this);
        return true;
    }

    public static boolean ForcedClearFirebaseData(int value) {

        if (value == FirebaseConstant.RIDE_FINISHED) {
            FirebaseWrapper.getInstance().getClientModelInstance().ClearData();
            FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance().ClearData();
            FirebaseWrapper.getInstance().getNotificationModelInstance().ClearData();
        }

        return true;
    }


    /* All Responses Goes Here */
    @Override
    public void OnResponseCreateNewRider(boolean value) {
        Log.d(FirebaseConstant.NEW_RIDER_CREATE, value + "");
    }

    @Override
    public void OnResponseCreateNewHistory(boolean value) {
        this.SetHistoryIDonRiderTable(
                FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance(),
                FirebaseWrapper.getInstance().getRiderModelInstance()
        );
        this.SetHistoryIDToClient(
                FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance(),
                FirebaseWrapper.getInstance().getClientModelInstance()
        );
        FirebaseResponse.RiderCanceledByRiderResponse(FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance().HistoryID);
        Log.d(FirebaseConstant.NEW_HISTORY_CREATE, Boolean.toString(value));
    }

    @Override
    public void OnResponseGetRiderModel(boolean value) {
        Log.d(FirebaseConstant.RIDER_LOADED, value + "");
    }

    @Override
    public void OnResponseSetRiderBusyOrFree(boolean value) {
        Log.d(FirebaseConstant.RIDER_BUSY_OR_FREE, Boolean.toString(value));
    }

    @Override
    public void OnResponseSetRiderOnRideOrFree(boolean value) {
        Log.d(FirebaseConstant.RIDER_ON_RIDE_OR_FREE, Boolean.toString(value));
    }

    @Override
    public void OnResponseSetRiderOnLineOffLine(boolean value) {
        Log.d(FirebaseConstant.RIDER_ONLINE_OR_OFFLINE, value + "");
    }

    @Override
    public void OnResponseSetRiderOnlineBusyOnRide(boolean value) {
        Log.d(FirebaseConstant.RIDER_ONLINE_BUSY_ON_RIDE, Boolean.toString(value));
    }

    @Override
    public void OnResponseGetHistoryModel(boolean value) {
        Log.d(FirebaseConstant.RIDER_LOADED, value + "");
    }

    @Override
    public void OnSetHistoryIDonRiderTable(boolean value) {
        Log.d(FirebaseConstant.HISTORY_ID_ADDED_TO_RIDER, Boolean.toString(value));
    }

    @Override
    public void OnGetCurrentClient(boolean value) {
        Log.d(FirebaseConstant.CLIENT_LOADED, value + "");
    }

    @Override
    public void OnResetRiderStatus(boolean value) {
        Log.d(FirebaseConstant.RESET_RIDER_STATUS, value + "");
    }

    @Override
    public void OnOnIsRiderAlreadyCreated(boolean value) {

        if (value == true) {
            this.SetDeviceTokenToRiderTable(
                    FirebaseWrapper.getInstance().getRiderModelInstance(),
                    FirebaseWrapper.getDeviceToken()
            );
            /*
             *this.ResetRiderStatus(FirebaseWrapper.getInstance().getRiderModelInstance());
             */
            return;
        }
        FirebaseRequestInstance.CreateRiderFirstTime(riderModel, Main.this);
    }

    @Override
    public void OnSetDeviceTokenToRiderTable(boolean value) {
        Log.d(FirebaseConstant.DEVICE_TOKEN_UPDATE, value + "");
    }

    @Override
    public void OnSentNotificationToClient(boolean value) {

        if (value == true) {
            this.SetRiderOnlineBusyOnRider(
                    FirebaseWrapper.getInstance().getRiderModelInstance(),
                    FirebaseConstant.ONLINE_BUSY_NO_RIDE
            );
            this.SetRiderBusyOrFree(
                    FirebaseWrapper.getInstance().getRiderModelInstance(),
                    FirebaseConstant.SET_RIDER_BUSY
            );
        }
        Log.d(FirebaseConstant.NOTIFICATION_SEND, Boolean.toString(value));
    }

    @Override
    public void OnFinalAcceptanceOfRide(boolean value) {
        Log.d(FirebaseConstant.RIDE_STARTED, Boolean.toString(value));
    }

    @Override
    public void OnFinishedRide(boolean value) {
        if (value == true) {
            ForcedClearFirebaseData(FirebaseConstant.RIDE_FINISHED);
        }
        Log.d(FirebaseConstant.FINISHED_RIDE, Boolean.toString(value));
    }

    @Override
    public void OnGetRiderStatus(boolean value) {
        if (value == true) {
            riderModel = FirebaseWrapper.getInstance().getRiderModelInstance();
            long onlineOffline = riderModel.IsRiderOnline;
            AppConstant.OnOffSwith =(int)onlineOffline;
        }
    }

    @Override
    public void OnHasAnyRide(boolean value) {
        if (value == true) {
            CurrentRidingHistoryModel History = FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance();
            if (History.HistoryID > 0) {
                /*Rider has a ride*/
                new RiderInRideMode(true);
            } else {
                /*Rider has no ride*/
                new RiderInRideMode(false);
            }
        }
    }

    @Override
    public void OnResponseServerTime(long value, int type) {
        if (value > 0) {
            switch (type) {
                case FirebaseConstant.INITIAL_AC_OF_RIDE_NOTIFY_CLIENT: {
                    FirebaseWrapper.getInstance().getFirebaseRequestInstance().SetHistoryIDToClient(
                            FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance(),
                            FirebaseWrapper.getInstance().getClientModelInstance(),
                            value,
                            Main.this
                    );
                    break;
                }
                case FirebaseConstant.FINAL_AC_OF_RIDE_NOTIFY_CLIENT: {
                    FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance().IsRideStart = value;
                    FirebaseWrapper.getInstance().getFirebaseRequestInstance().FinalAcceptanceOfRide(
                            FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance(),
                            FirebaseWrapper.getInstance().getRiderModelInstance(),
                            Main.this
                    );
                    break;
                }
                case FirebaseConstant.FINISHED_RIDE_NOTIFY_CLIENT: {
                    this.FinishedRide(
                            FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance(),
                            FirebaseWrapper.getInstance().getRiderModelInstance(),
                            FinalCost,
                            FinalLocation,
                            value
                    );
                    break;
                }
                case FirebaseConstant.CANCELED_RIDE_BY_RIDER_NOTIFY_CLIENT: {
                    this.CancelRideByRider(
                            FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance(),
                            FirebaseWrapper.getInstance().getRiderModelInstance(),
                            value
                    );
                    break;
                }
                case FirebaseConstant.REJECTION_OF_RIDE_NOTIFY_CLIENT: {
                    this.RideRejectedByRider(
                            FirebaseWrapper.getInstance().getNotificationModelInstance().clientId,
                            FirebaseWrapper.getInstance().getRiderModelInstance().RiderID,
                            value
                    );
                    break;
                }
            }
            Log.d(FirebaseConstant.CURRENT_SERVER_TIME, Long.toString(value));
        }
    }
}
