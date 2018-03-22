package __Firebase.FirebaseRequest;

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.ICallbacklisteners.ICallbackMain;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/23/2017.
 */

public class CreateNewRideHistory {

    private ICallbackMain callBackListener = null;
    private CurrentRidingHistoryModel HistoryModel = null;

    public CreateNewRideHistory(CurrentRidingHistoryModel HistoryModel, ICallbackMain callBackListener){
        this.HistoryModel = HistoryModel;
        this.callBackListener = callBackListener;
        Request();
    }

    private void Request(){

        try {
            FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).push()
                    .setValue(HistoryModel, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            callBackListener.OnResponseCreateNewHistory(true);
                        }
                    });

        } catch (Exception e) {
            callBackListener.OnResponseCreateNewHistory(false);
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
