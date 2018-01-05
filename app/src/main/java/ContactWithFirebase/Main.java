package ContactWithFirebase;

import android.content.Context;
import android.util.Log;
import android.util.Pair;


import com.chaatgadrive.arif.chaatgadrive.chaatgamap.GetCurrentLocation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.chaatgadrive.arif.chaatgadrive.models.HistoryModel.RiderHistory;

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

    public boolean CreateNewHistoryModelFirebase(RiderHistory riderHistory){

        firebaseWrapper = FirebaseWrapper.getInstance();
        this.currentRidingHistoryModel = firebaseWrapper.getRidingHistoryModelModelInstance();
        FirebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();

        this.currentRidingHistoryModel.HistoryID = riderHistory.HistoryID;
        this.currentRidingHistoryModel.ClientID = riderHistory.ClientID;
        this.currentRidingHistoryModel.RiderID = riderHistory.RiderID;
        this.currentRidingHistoryModel.Client_History =riderHistory.Client_History  + FirebaseConstant.UNDER_SCORE + "11040066";
        this.currentRidingHistoryModel.Rider_History = riderHistory.Rider_History + FirebaseConstant.UNDER_SCORE + "11040066";;
        this.currentRidingHistoryModel.StartingLocation = new CurrentRidingHistoryModel.Location(
                riderHistory.StartLocation.latitude,
                riderHistory.StartLocation.longitude
        );
        this.currentRidingHistoryModel.EndingLocation = new CurrentRidingHistoryModel.Location(
                riderHistory.EndLocation.latitude,
                riderHistory.EndLocation.longitude
        );
        this.currentRidingHistoryModel.CostSoFar = 1111;

        this.currentRidingHistoryModel.IsRideStart = FirebaseConstant.RIDE_NOT_START;
        this.currentRidingHistoryModel.IsRideFinished = FirebaseConstant.RIDE_NOT_FINISHED;
        this.currentRidingHistoryModel.RideCanceledByClient = FirebaseConstant.UNKNOWN;
        this.currentRidingHistoryModel.RideCanceledByRider = FirebaseConstant.UNKNOWN;

        FirebaseRequestInstance.CreateNewRideHistory(this.currentRidingHistoryModel, Main.this);
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
            SetDeviceTokenToRiderTable(
                    FirebaseWrapper.getInstance().getRiderModelInstance(),
                    FirebaseWrapper.getDeviceToken()
            );
            return;
        }
        FirebaseRequestInstance.CreateRiderFirstTime(riderModel, Main.this);
    }

    @Override
    public void OnSetDeviceTokenToRiderTable(boolean value) {
        Log.d(FirebaseConstant.DEVICE_TOKEN_UPDATE, value + "");
    }
}
