package __Firebase.FirebaseRequest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallbackMain;

/**
 * Created by User on 11/23/2017.
 */

public class InitialAcceptanceOfRide {

    private ICallbackMain callBackListener = null;
    private RiderModel Rider = null;

    public InitialAcceptanceOfRide(RiderModel Rider, ICallbackMain callBackListener) {
        this.Rider = Rider;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request() {

        /*
         SetHistoryIDToClient will Notify Client about Initial acceptance of ride
         This history ID wil be set while history create in main server, so don't worry
        */

        try {
            FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
            Query pendingTask = firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID);

            pendingTask.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        if (dataSnapshot.getChildren().iterator().hasNext()) {

                            DataSnapshot dsp = dataSnapshot.getChildren().iterator().next();

                            Map<String, Object> IsRiderBusy = new HashMap<>();
                            IsRiderBusy.put(FirebaseConstant.IS_RIDER_BUSY_OR_FREE, Rider.IsRiderBusy);
                            dsp.getRef().updateChildren(IsRiderBusy);

                            Map<String, Object> OnlineBusyOnRide = new HashMap<>();
                            OnlineBusyOnRide.put(FirebaseConstant.ON_LINE_BUSY_ON_RIDE, Rider.OnlineBusyOnRide);
                            dsp.getRef().updateChildren(OnlineBusyOnRide);

                            Map<String, Object> SetHistoryID = new HashMap<>();
                            SetHistoryID.put(FirebaseConstant.CURRENT_RIDING_HISTORY_ID, Rider.CurrentRidingHistoryID);
                            dsp.getRef().updateChildren(SetHistoryID);

                            FabricExceptionLog.printLog(this.getClass().getSimpleName(), FirebaseConstant.SET_RIDER_110);
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
