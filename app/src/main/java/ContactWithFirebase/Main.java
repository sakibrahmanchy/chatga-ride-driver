package ContactWithFirebase;

import android.content.Context;
import android.util.Log;
import android.util.Pair;


import com.chaatgadrive.arif.chaatgadrive.chaatgamap.GetCurrentLocation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;

import __Firebase.FirebaseUtility.FirebaseUtilMethod;
import __Firebase.ICallbacklisteners.ICallbackMain;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseRequest.__FirebaseRequest;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 12/8/2017.
 */

public class Main implements ICallbackMain {

    private GetCurrentLocation getCurrentLocation = null;
    private FirebaseWrapper firebaseWrapper = null;
    private RiderModel riderModel = null;
    private CurrentRidingHistoryModel currentRidingHistoryModel = null;
    private __FirebaseRequest FirebaseRequestInstance;

    public Main(Context context){
        getCurrentLocation = new GetCurrentLocation(context);
    }

    public boolean IsRiderAlreadyCreated(RiderModel RiderModel){

        firebaseWrapper = FirebaseWrapper.getInstance();
        FirebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();

        FirebaseRequestInstance.IsRiderAlreadyCreated(RiderModel, Main.this);
        return true;
    }

    public boolean CreateNewRiderFirebase(/*Main Rider Mode*/LoginData loginData, String phoneNumber){

        firebaseWrapper = FirebaseWrapper.getInstance();
        riderModel = firebaseWrapper.getRiderModelInstance();
        FirebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();

        riderModel.RiderID = Long.parseLong(loginData.getUserId());
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

    public boolean CreateNewHistoryModelFirebase(/*Main History Mode, Client Model, Rider Model*/){

        firebaseWrapper = FirebaseWrapper.getInstance();
        currentRidingHistoryModel = firebaseWrapper.getRidingHistoryModelModelInstance();
        FirebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();

        currentRidingHistoryModel.HistoryID = 11040066;
        currentRidingHistoryModel.ClientID = 11040067;
        currentRidingHistoryModel.RiderID = 11040068;
        currentRidingHistoryModel.Client_History = "11040067" + FirebaseConstant.UNDER_SCORE + "11040066";
        currentRidingHistoryModel.Rider_History = "11040068" + FirebaseConstant.UNDER_SCORE + "11040066";;
        currentRidingHistoryModel.StartingLocation = new CurrentRidingHistoryModel.Location(
                11.04006d,
                11.04006d
        );
        currentRidingHistoryModel.EndingLocation = new CurrentRidingHistoryModel.Location(
                11.04006d,
                11.04006d
        );
        currentRidingHistoryModel.CostSoFar = 1111;

        currentRidingHistoryModel.IsRideStart = FirebaseConstant.RIDE_NOT_START;
        currentRidingHistoryModel.IsRideFinished = FirebaseConstant.RIDE_NOT_FINISHED;
        currentRidingHistoryModel.RideCanceledByClient = FirebaseConstant.UNKNOWN;
        currentRidingHistoryModel.RideCanceledByRider = FirebaseConstant.UNKNOWN;

        FirebaseRequestInstance.CreateNewRideHistory(currentRidingHistoryModel, Main.this);
        return true;
    }

    public boolean GetCurrentRider(long RiderId){

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.getFirebaseRequestInstance().GetCurrentRider(RiderId, Main.this);
        return true;
    }

    public boolean GetRecentHistory(long HistoryId){

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.getFirebaseRequestInstance().GetRecentHistory(HistoryId, Main.this);
        return true;
    }

    public boolean SetRiderBusyOrFree(/*Firebase Rider Model*/ RiderModel riderModel, int value){

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = riderModel;

        if(value == FirebaseConstant.SET_RIDER_FREE){
            this.riderModel.IsRiderBusy = FirebaseConstant.SET_RIDER_FREE;
        }else {
            this.riderModel.IsRiderBusy = FirebaseConstant.SET_RIDER_BUSY;
        }

        firebaseWrapper.getFirebaseRequestInstance().SetRiderBusyOrFree(this.riderModel, Main.this);
        return true;
    }

    public boolean SetRiderOnRideOrFree(/*Firebase Rider Model*/ RiderModel riderModel, int value){

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = riderModel;

        if(value == FirebaseConstant.SET_RIDER_NO_RIDE){
            this.riderModel.IsRiderOnRide = FirebaseConstant.SET_RIDER_NO_RIDE;
        }else {
            this.riderModel.IsRiderOnRide = FirebaseConstant.SET_RIDER_ON_RIDE;
        }

        firebaseWrapper.getFirebaseRequestInstance().SetRiderOnRideOrFree(this.riderModel, Main.this);

        return true;
    }

    public boolean SetRiderOnLineOrOffLine(/*Firebase Rider Model*/ RiderModel riderModel, int value){

        if(riderModel == null || riderModel.RiderID < 1)    return false;

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = riderModel;

        if(value == FirebaseConstant.SET_RIDER_ONLINE){
            this.riderModel.IsRiderOnline = FirebaseConstant.SET_RIDER_ONLINE;
        }else {
            this.riderModel.IsRiderOnline = FirebaseConstant.SET_RIDER_OFF_ONLINE;
        }

        firebaseWrapper.getFirebaseRequestInstance().SetRiderOnLineOrOffLine(this.riderModel, Main.this);

        return true;
    }

    public boolean SetRiderOnlineBusyOnRider(/*Firebase Rider Model*/ RiderModel riderModel, int value){

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = riderModel;

        if(value == FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE){
            this.riderModel.OnlineBusyOnRide = FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE;
        }else if(value == FirebaseConstant.ONLINE_BUSY_NO_RIDE){
            this.riderModel.OnlineBusyOnRide = FirebaseConstant.ONLINE_BUSY_NO_RIDE;
        }else if(value == FirebaseConstant.ONLINE_BUSY_ON_RIDE) {
            this.riderModel.OnlineBusyOnRide = FirebaseConstant.ONLINE_BUSY_ON_RIDE;
        } else {
            this.riderModel.OnlineBusyOnRide = FirebaseConstant.OFFLINE_NOT_BUSY_ON_RIDE;
        }

        firebaseWrapper.getFirebaseRequestInstance().SetRiderOnlineBusyOnRider(this.riderModel, Main.this);
        return true;
    }

    public boolean SetHistoryIDonRiderTable(/*Firebase Rider Model*/ CurrentRidingHistoryModel historyModel, RiderModel riderModel){

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = riderModel;
        this.currentRidingHistoryModel = historyModel;

        firebaseWrapper.getFirebaseRequestInstance().SetHistoryIDonRiderTable(this.currentRidingHistoryModel, this.riderModel, Main.this);
        return true;
    }

    public boolean SentNotificationToRider(/*Firebase Client Mode*/RiderModel Rider, String clientDeviceToken){

        if(Rider == null || Rider.RiderID < 1 || FirebaseUtilMethod.IsEmptyOrNull(clientDeviceToken))   return false;

        this.riderModel = Rider;
        firebaseWrapper = FirebaseWrapper.getInstance();

        firebaseWrapper.getFirebaseRequestInstance().SentNotificationToRider(this.riderModel, clientDeviceToken,Main.this);
        return true;
    }

    public boolean SetDeviceTokenToRiderTable(/*Firebase Rider Model*/ RiderModel riderModel, String deviceToken){

        if(riderModel == null || riderModel.RiderID < 1 || FirebaseUtilMethod.IsEmptyOrNull(deviceToken))   return false;

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = riderModel;
        this.riderModel.DeviceToken = deviceToken;

        firebaseWrapper.getFirebaseRequestInstance().SetDeviceTokenToRiderTable(this.riderModel, Main.this);
        return true;
    }

    public boolean UpdateRiderLocation(/*Firebase Rider Model*/ RiderModel riderModel, Pair<Double, Double> newLocation){

        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = riderModel;

        this.riderModel.CurrentRiderLocation.Latitude = newLocation.first;
        this.riderModel.CurrentRiderLocation.Longitude = newLocation.second;
        this.riderModel.CurrentRiderLocation.RequestForUpdateLocation = FirebaseConstant.UPDATE_LOCATION;

        firebaseWrapper.getFirebaseRequestInstance().UpdateRiderLocation(this.riderModel, Main.this);

        return true;
    }

    public boolean GetCurrentClient(long ClientId){

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.getFirebaseRequestInstance().GetCurrentClient(ClientId, Main.this);
        return true;
    }

    public boolean SetHistoryIDToClient(/* Firebase HistoryModel, RiderModel */ CurrentRidingHistoryModel HistoryModel, ClientModel Client){

        firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.getFirebaseRequestInstance().SetHistoryIDToClient(HistoryModel, Client, Main.this);
        return true;
    }

    public boolean ResetRiderStatus(/* Firebase HistoryModel, RiderModel */ RiderModel Rider){

        if(Rider == null || Rider.RiderID < 1)  return false;
        this.firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = Rider;

        this.riderModel.IsRiderOnline = FirebaseConstant.SET_RIDER_ONLINE;
        this.riderModel.IsRiderOnRide = FirebaseConstant.SET_RIDER_NO_RIDE;
        this.riderModel.IsRiderBusy = FirebaseConstant.SET_RIDER_FREE;
        this.riderModel.OnlineBusyOnRide = FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE;

        firebaseWrapper.getFirebaseRequestInstance().ResetRiderStatus(riderModel, Main.this);
        return true;
    }

    public boolean InitialAcceptanceOfRide(/* Firebase HistoryModel, RiderModel */ CurrentRidingHistoryModel HistoryModel, RiderModel Rider){

        firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = Rider;
        this.currentRidingHistoryModel = HistoryModel;

        this.riderModel.IsRiderBusy = FirebaseConstant.SET_RIDER_BUSY;
        this.riderModel.OnlineBusyOnRide = FirebaseConstant.ONLINE_BUSY_NO_RIDE;

        firebaseWrapper.getFirebaseRequestInstance().InitialAcceptanceOfRide(currentRidingHistoryModel, riderModel, Main.this);
        return true;
    }

    public boolean FinalAcceptanceOfRide(/* Firebase HistoryModel, RiderModel */ CurrentRidingHistoryModel HistoryModel, RiderModel Rider){

        firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = Rider;
        this.currentRidingHistoryModel = HistoryModel;

        this.riderModel.IsRiderOnRide = FirebaseConstant.SET_RIDER_ON_RIDE;
        this.riderModel.IsRiderBusy = FirebaseConstant.SET_RIDER_BUSY;
        this.riderModel.OnlineBusyOnRide = FirebaseConstant.ONLINE_BUSY_ON_RIDE;

        this.currentRidingHistoryModel.IsRideStart = FirebaseConstant.IS_RIDE_START_SET;

        firebaseWrapper.getFirebaseRequestInstance().FinalAcceptanceOfRide(currentRidingHistoryModel, riderModel, Main.this);
        return true;
    }

    public boolean FinishedRide(/* Firebase HistoryModel, RiderModel */ CurrentRidingHistoryModel HistoryModel, RiderModel Rider, /*Server*/long FinalCost, /*Local*/ Pair<Double, Double>FinalLocation){

        firebaseWrapper = FirebaseWrapper.getInstance();
        this.riderModel = Rider;
        this.currentRidingHistoryModel = HistoryModel;

        this.currentRidingHistoryModel.IsRideFinished = FirebaseConstant.RIDE_FINISHED;
        this.currentRidingHistoryModel.CostSoFar = FinalCost;
        this.currentRidingHistoryModel.EndingLocation.Latitude = FinalLocation.first;
        this.currentRidingHistoryModel.EndingLocation.Longitude = FinalLocation.second;

        firebaseWrapper.getFirebaseRequestInstance().FinishedRide(currentRidingHistoryModel, Main.this);
        return true;
    }



    /* All Responses Goes Here */
    @Override
    public void OnResponseCreateNewRider(boolean value) {
        Log.d(FirebaseConstant.NEW_RIDER_CREATE, value + "");
    }

    @Override
    public void OnResponseCreateNewHistory(boolean value) {
        Log.d(FirebaseConstant.NEW_HISTORY_CREATE, value + "");
    }

    @Override
    public void OnResponseGetRiderModel(boolean value) {
        Log.d(FirebaseConstant.RIDER_LOADED, value + "");
    }

    @Override
    public void OnResponseSetRiderBusyOrFree(boolean value) {
        Log.d(FirebaseConstant.RIDER_BUSY_OR_FREE, value + "");
    }

    @Override
    public void OnResponseSetRiderOnRideOrFree(boolean value) {
        Log.d(FirebaseConstant.RIDER_ON_RIDE_OR_FREE, value + "");
    }

    @Override
    public void OnResponseSetRiderOnLineOffLine(boolean value) {
        Log.d(FirebaseConstant.RIDER_ONLINE_OR_OFFLINE, value + "");
    }

    @Override
    public void OnResponseSetRiderOnlineBusyOnRide(boolean value) {
        Log.d(FirebaseConstant.RIDER_ONLINE_BUSY_ON_RIDE, value + "");
    }

    @Override
    public void OnResponseGetHistoryModel(boolean value) {
        Log.d(FirebaseConstant.RIDER_LOADED, value + "");
    }

    @Override
    public void OnSetHistoryIDonRiderTable(boolean value) {
        Log.d(FirebaseConstant.HISTORY_ID_ADDED_TO_RIDER, value + "");
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
        if(value == true){
            this.SetDeviceTokenToRiderTable(
                    FirebaseWrapper.getInstance().getRiderModelInstance(),
                    FirebaseWrapper.getDeviceToken()
            );
            this.ResetRiderStatus(FirebaseWrapper.getInstance().getRiderModelInstance());
            return;
        }
        FirebaseRequestInstance.CreateRiderFirstTime(riderModel, Main.this);
    }

    @Override
    public void OnSetDeviceTokenToRiderTable(boolean value) {
        Log.d(FirebaseConstant.DEVICE_TOKEN_UPDATE, value + "");
    }

    @Override
    public void OnSentNotificationToRider(boolean value) {
        Log.d(FirebaseConstant.NOTIFICATION_SEND, Boolean.toString(value));
    }
}
