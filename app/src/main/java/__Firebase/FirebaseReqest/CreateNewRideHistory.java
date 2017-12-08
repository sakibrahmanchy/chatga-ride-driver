package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import __Firebase.Callbacklisteners.CallBackListener;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/23/2017.
 */

public class CreateNewRideHistory {

    private CallBackListener callBackListener = null;
    private CurrentRidingHistoryModel HistoryModel = null;

    public CreateNewRideHistory(CurrentRidingHistoryModel HistoryModel, CallBackListener callBackListener){
        this.HistoryModel = HistoryModel;
        this.callBackListener = callBackListener;
        Request();
    }

    private void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).push()
                    .setValue(HistoryModel, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            callBackListener.onCreateNewRideHistory(true);
                        }
                    });

        } catch (Exception e) {
            callBackListener.onCreateNewRideHistory(false);
            e.printStackTrace();
            Log.d(FirebaseConstant.NEW_HISTORY_ERROR, e.toString());
        }
    }
}
