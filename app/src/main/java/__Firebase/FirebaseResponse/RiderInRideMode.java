package __Firebase.FirebaseResponse;

import __Firebase.FirebaseWrapper;

/**
 * Created by User on 2/5/2018.
 */

public class RiderInRideMode {

    public RiderInRideMode(boolean hasRide){
        if(hasRide){
            HasRide();
        }else {
            NoRide();
        }
    }

    private void HasRide(){
        /* Riding History Model*/
        FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance();
    }

    private void NoRide(){
        FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance().ClearData();
    }
}
