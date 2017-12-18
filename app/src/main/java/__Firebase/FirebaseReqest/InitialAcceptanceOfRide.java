package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import __Firebase.ICallbacklisteners.ICallbackMain;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/23/2017.
 */

public class InitialAcceptanceOfRide {

    private ICallbackMain callBackListener = null;
    private CurrentRidingHistoryModel HistoryModel = null;
    private RiderModel Rider = null;

    public InitialAcceptanceOfRide(CurrentRidingHistoryModel HistoryModel, RiderModel Rider, ICallbackMain callBackListener){
        this.Rider = Rider;
        this.HistoryModel = HistoryModel;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        Query pendingTask = firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID);

        pendingTask.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {

                        DataSnapshot dsp = dataSnapshot.getChildren().iterator().next();

                        Map<String, Object> IsRiderBusy = new HashMap<>();
                        IsRiderBusy.put(FirebaseConstant.IS_RIDER_BUSY_OR_FREE, Rider.IsRiderBusy);
                        dsp.getRef().updateChildren(IsRiderBusy);

                        Map<String, Object> OnlineBusyOnRide = new HashMap<>();
                        OnlineBusyOnRide.put(FirebaseConstant.ON_LINE_BUSY_ON_RIDE, Rider.OnlineBusyOnRide);
                        dsp.getRef().updateChildren(OnlineBusyOnRide);

                        Map<String, Object> SetHistoryID = new HashMap<>();
                        SetHistoryID.put(FirebaseConstant.CURRENT_RIDING_HISTORY_ID, HistoryModel.HistoryID);
                        dsp.getRef().updateChildren(SetHistoryID);

                        Log.d(FirebaseConstant.SET_RIDER_110, FirebaseConstant.SET_RIDER_110);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
