package __Firebase.FirebaseRequest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallbackMain;

/**
 * Created by User on 2/5/2018.
 */

public class HasAnyRide {

    private long RiderID = 0;
    private ICallbackMain callBackListener = null;

    public HasAnyRide(long RiderID, ICallbackMain callBackListener) {
        this.RiderID = RiderID;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request() {

        final FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(this.RiderID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        DataSnapshot snp = dataSnapshot.getChildren().iterator().next();
                        if (snp.exists()) {
                            firebaseWrapper.getRiderModelInstance().LoadData(snp);
                            callBackListener.OnHasAnyRide(true);
                            FabricExceptionLog.printLog(this.getClass().getSimpleName(), FirebaseConstant.RIDER_LOADED);
                        } else {
                            callBackListener.OnHasAnyRide(false);
                        }
                    } else {
                        callBackListener.OnHasAnyRide(false);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callBackListener.OnHasAnyRide(false);
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
