package __Firebase.FirebaseRequest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallbackMain;

/**
 * Created by User on 12/24/2017.
 */

public class IsRiderAlreadyCreated {

    private RiderModel Rider = null;
    private ICallbackMain callBackListener = null;

    public IsRiderAlreadyCreated(RiderModel Rider, ICallbackMain callBackListener) {
        this.Rider = Rider;
        this.callBackListener = callBackListener;
        Request();
    }

    public void Request() {

        try {
            final FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        DataSnapshot snp = dataSnapshot.getChildren().iterator().next();
                        if (snp.exists()) {
                            firebaseWrapper.getRiderModelInstance().LoadData(snp);
                            callBackListener.OnOnIsRiderAlreadyCreated(true);
                            FabricExceptionLog.printLog(this.getClass().getSimpleName(), FirebaseConstant.RIDER_LOADED);
                        } else {
                            callBackListener.OnOnIsRiderAlreadyCreated(false);
                        }
                    } else {
                        callBackListener.OnOnIsRiderAlreadyCreated(false);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callBackListener.OnOnIsRiderAlreadyCreated(false);
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
