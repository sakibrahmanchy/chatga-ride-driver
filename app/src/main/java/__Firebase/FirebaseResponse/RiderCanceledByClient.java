package __Firebase.FirebaseResponse;

import com.chaatgadrive.arif.chaatgadrive.MainActivity;

import ContactWithFirebase.Main;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 1/15/2018.
 */

public class RiderCanceledByClient {

    Main main = new Main(MainActivity.getContextOfApplication());
    public RiderCanceledByClient(long Time){

        Response();
    }

    private void Response(){

        ClearData();
    }

    private void ClearData(){
        main.ForcedRefreshRider();

        FirebaseWrapper.getInstance().getRiderModelInstance().CurrentRidingHistoryID = FirebaseConstant.ZERO;
        FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance().HistoryID = FirebaseConstant.ZERO;
        main.SetHistoryIDonRiderTable(
                FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance(),
                FirebaseWrapper.getInstance().getRiderModelInstance()
        );
    }
}
