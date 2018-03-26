package __Firebase.FirebaseRequest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallbackMain;

/**
 * Created by User on 11/25/2017.
 */

public class SetRiderOnRideOrFree {

    private RiderModel Rider = null;
    private ICallbackMain callBackListener = null;

    public SetRiderOnRideOrFree(RiderModel Rider, ICallbackMain callBackListener) {
        this.Rider = Rider;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request() {

        try {
            FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        if (dataSnapshot.getChildren().iterator().hasNext()) {

                            Map<String, Object> update = new HashMap<>();
                            update.put(FirebaseConstant.IS_RIDER_ON_RIDE, Rider.IsRiderOnRide);
                            dataSnapshot.getChildren().iterator().next().getRef().updateChildren(update);

                            FabricExceptionLog.printLog(this.getClass().getSimpleName(), FirebaseConstant.IS_RIDER_ON_RIDE);
                            callBackListener.OnResponseSetRiderOnRideOrFree(true);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callBackListener.OnResponseSetRiderOnRideOrFree(false);
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
