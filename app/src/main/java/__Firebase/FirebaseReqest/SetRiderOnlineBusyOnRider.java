package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

<<<<<<< Updated upstream:app/src/main/java/__Firebase/FirebaseReqest/SetRiderOnlineBusyOnRider.java
import __Firebase.Callbacklisteners.CallBackListener;
import __Firebase.Callbacklisteners.ICallbackMain;
=======
import __Firebase.ICallbacklisteners.ICallbackMain;
>>>>>>> Stashed changes:app/src/main/java/__Firebase/FirebaseRequest/SetRiderOnlineBusyOnRider.java
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/27/2017.
 */

public class SetRiderOnlineBusyOnRider {

    private RiderModel Rider = null;
    private ICallbackMain callBackListener = null;

    public SetRiderOnlineBusyOnRider(RiderModel Rider, ICallbackMain callBackListener){
        this.Rider = Rider;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {

                        Map<String, Object> update = new HashMap<>();
                        update.put(FirebaseConstant.ON_LINE_BUSY_ON_RIDE, Rider.OnlineBusyOnRide);
                        dataSnapshot.getChildren().iterator().next().getRef().updateChildren(update);

                        Log.d(FirebaseConstant.ON_LINE_BUSY_ON_RIDE, FirebaseConstant.ON_LINE_BUSY_ON_RIDE);
                        callBackListener.OnResponseSetRiderOnlineBusyOnRide(true);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBackListener.OnResponseSetRiderOnlineBusyOnRide(true);
            }
        });
    }
}