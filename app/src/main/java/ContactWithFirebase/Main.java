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

    public FirebaseWrapper firebaseWrapper = null;
    private CurrentRidingHistoryModel currentRidingHistoryModel = null;
    private RiderModel riderModel = null;
    private FirebaseRequest FirebaseRequestInstance;
    private ClientModel clientModel = null;

    public Main(){
    }

    public int CreateNewRiderFirebase(/*Mode*/){
        firebaseWrapper = FirebaseWrapper.getInstance();
        riderModel = firebaseWrapper.getRiderModelInstance();

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

        FirebaseRequestInstance = firebaseWrapper.getFirebaseRequestInstance();
        FirebaseRequestInstance.CreateRiderFirstTime(riderModel, Main.this);
        return 0;
    }

    @Override
    public void OnResponseCreateNewRider(boolean value) {
        Log.d(FirebaseConstant.NEW_RIDER_CREATE, value + "");
    }
}
