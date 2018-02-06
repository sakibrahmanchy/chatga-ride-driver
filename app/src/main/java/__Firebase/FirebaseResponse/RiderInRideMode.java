package __Firebase.FirebaseResponse;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.MainActivity;

import ContactWithFirebase.Main;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.CallBackListener;

/**
 * Created by User on 2/5/2018.
 */

public class RiderInRideMode implements CallBackListener {

    private long HistoryID = FirebaseConstant.UNKNOWN;
    private Main main;
    public RiderInRideMode(boolean hasRide, long HistoryID){
        if(hasRide){
            this.HistoryID = HistoryID;
            this.main = new Main(MainActivity.getContextOfApplication());
            GetCurrentHistory();
        }else {
            NoRide();
        }
    }

    private void HasRide(){
        /* Riding History Model*/
        AppConstant.currentRidingHistoryModel = FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance();
        /*Client Model*/
        AppConstant.ClientModel=FirebaseWrapper.getInstance().getClientModelInstance();
        AppConstant.IS_RIDE=1;
    }

    private void NoRide(){
        AppConstant.IS_RIDE=0;
    }

    private void GetCurrentHistory(){
        this.main.GetRecentHistory(this.HistoryID, this);
    }

    @Override
    public void OnGetHistoryModel(boolean value) {
        if(value == true) {
            this.main.GetCurrentClient(FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance().ClientID, this);
        }
    }

    @Override
    public void OnGetCurrentClient(boolean value) {
        if(value == true){
            HasRide();
        }
    }
}
