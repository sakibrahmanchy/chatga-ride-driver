package __Firebase.FirebaseRequest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallbackMain;

/**
 * Created by User on 1/21/2018.
 */

public class CancelRideByRider {

    private CurrentRidingHistoryModel HistoryModel = null;
    private RiderModel Rider = null;
    private ICallbackMain callBackListener = null;

    public CancelRideByRider(CurrentRidingHistoryModel HistoryModel, RiderModel Rider, ICallbackMain callBackListener) {
        this.HistoryModel = HistoryModel;
        this.Rider = Rider;
        this.callBackListener = callBackListener;

        Request();
    }

    private void Request() {
        /*
        * If Rider == null then no initial acceptance by rider
        * If Rider == model Notify rider
        */

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY)
                    .orderByChild(FirebaseConstant.RIDER_HISTORY)
                    .equalTo(Rider.RiderID + FirebaseConstant.JOIN + HistoryModel.HistoryID)
                    .addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                                if (dataSnapshot.getChildren().iterator().hasNext()) {

                                    HashMap<String, Object> CancelRideByRider = new HashMap<>();
                                    CancelRideByRider.put(FirebaseConstant.CANCEL_RIDE_BY_RIDER, HistoryModel.RideCanceledByRider);
                                    dataSnapshot.getChildren().iterator().next().getRef().updateChildren(CancelRideByRider);

                                    FabricExceptionLog.printLog(this.getClass().getSimpleName(), FirebaseConstant.CANCELED_RIDE_BY_RIDER);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                        }
                    });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
