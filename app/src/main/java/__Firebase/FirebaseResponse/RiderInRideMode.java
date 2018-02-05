package __Firebase.FirebaseResponse;

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
    public RiderInRideMode(boolean hasRide, long HistoryID){
        if(hasRide){
            this.HistoryID = HistoryID;
            GetCurrentHistory();
        }else {
            NoRide();
        }
    }

    private void HasRide(){
        /* Riding History Model*/
        FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance();
    }

    private void NoRide(){

    }

    private void GetCurrentHistory(){
        new Main(MainActivity.getContextOfApplication()).GetRecentHistory(this.HistoryID, this);
    }

    @Override
    public void OnGetHistoryModel(boolean value) {
        HasRide();
    }
}
