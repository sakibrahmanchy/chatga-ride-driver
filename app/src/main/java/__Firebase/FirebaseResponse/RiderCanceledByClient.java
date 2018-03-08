package __Firebase.FirebaseResponse;

import android.content.Intent;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.MainActivity;

import ContactWithFirebase.Main;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

import static com.chaatgadrive.arif.chaatgadrive.OnrideMode.OnRideModeActivity.Onridecontext;

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

        if(AppConstant.ONRIDEMODE_ACTIVITY){
            Intent intent = new Intent(Onridecontext,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Onridecontext.startActivity(intent);
            Onridecontext.finish();
        }
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
