package ContactWithFirebase;

import android.util.Log;

import __Firebase.Callbacklisteners.ICallbackMain;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseReqest.FirebaseRequest;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 12/8/2017.
 */

public class Main implements ICallbackMain {

    private FirebaseWrapper firebaseWrapper = null;
    private RiderModel riderModel = null;
    private CurrentRidingHistoryModel currentRidingHistoryModel = null;
    private FirebaseRequest FirebaseRequestInstance;

    public Main(){
    }

    public boolean CreateNewRiderFirebase(/*Main Rider Mode*/){

        firebaseWrapper = FirebaseWrapper.getInstance();
        riderModel = firebaseWrapper.getRiderModelInstance();
        FirebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();

        riderModel.RiderID = 1104006;
        riderModel.FullName = "FirebaseWrapper";
        riderModel.PhoneNumber = Long.parseLong("01752062838");
        riderModel.CurrentRiderLocation = new RiderModel.RiderLocation(11, 11, FirebaseConstant.UNSET_REQUEST_UPDATE_LOCATION);

        riderModel.DeviceToken = FirebaseWrapper.getDeviceToken();
        riderModel.CurrentRidingHistoryID = 0;
        riderModel.DistanceFromClient = 0;
        riderModel.IsRiderBusy = FirebaseConstant.SET_RIDER_FREE;
        riderModel.IsRiderOnline = FirebaseConstant.SET_RIDER_ONLINE;
        riderModel.IsRiderOnRide = FirebaseConstant.SET_RIDER_NO_RIDE;
        riderModel.OnlineBusyOnRide = FirebaseConstant.ONLINE_NOT_BUSY_NO_RIDE;

        FirebaseRequestInstance.CreateRiderFirstTime(riderModel, Main.this);
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

    public boolean SetRiderBusyOrFre(/*Firebase Rider Model*/ RiderModel riderModel, int value){

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
        }else {
            this.riderModel.OnlineBusyOnRide = FirebaseConstant.ONLINE_BUSY_ON_RIDE;
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
        this.GetRecentHistory(10101010);
        //this.SetRiderOnlineBusyOnRider(FirebaseWrapper.getInstance().getRiderModelInstance(), FirebaseConstant.ONLINE_BUSY_ON_RIDE);
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
        this.SetHistoryIDonRiderTable(
                FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance(),
                FirebaseWrapper.getInstance().getRiderModelInstance()
        );
        Log.d(FirebaseConstant.RIDER_LOADED, value + "");
    }

    @Override
    public void OnSetHistoryIDonRiderTable(boolean value) {
        Log.d(FirebaseConstant.HISTORY_ID_ADDED_TO_RIDER, value + "");
    }
}
