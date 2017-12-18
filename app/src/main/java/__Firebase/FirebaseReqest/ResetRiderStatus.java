package __Firebase.FirebaseReqest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

<<<<<<< Updated upstream:app/src/main/java/__Firebase/FirebaseReqest/ResetRiderStatus.java
import __Firebase.Callbacklisteners.CallBackListener;
import __Firebase.Callbacklisteners.ICallbackMain;
=======
import __Firebase.ICallbacklisteners.ICallbackMain;
>>>>>>> Stashed changes:app/src/main/java/__Firebase/FirebaseRequest/ResetRiderStatus.java
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/28/2017.
 */

public class ResetRiderStatus {

    private RiderModel Rider = null;
    private ICallbackMain callBackListener = null;

    public ResetRiderStatus(RiderModel Rider, ICallbackMain callBackListener){
        this.Rider = Rider;
        this.callBackListener = callBackListener;

        Request();
    }

    private void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        Query pendingTask = firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID);
        pendingTask.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {

                        Map<String, Object> IsRiderOnline = new HashMap<>();
                        IsRiderOnline.put(FirebaseConstant.IS_RIDER_ON_LINE, Rider.IsRiderOnline);
                        dataSnapshot.getChildren().iterator().next().getRef().updateChildren(IsRiderOnline);

                        Map<String, Object> IsRiderOnRide = new HashMap<>();
                        IsRiderOnRide.put(FirebaseConstant.IS_RIDER_ON_RIDE, Rider.IsRiderOnRide);
                        dataSnapshot.getChildren().iterator().next().getRef().updateChildren(IsRiderOnRide);

                        Map<String, Object> IsRiderBusy = new HashMap<>();
                        IsRiderBusy.put(FirebaseConstant.IS_RIDER_BUSY_OR_FREE, Rider.IsRiderBusy);
                        dataSnapshot.getChildren().iterator().next().getRef().updateChildren(IsRiderBusy);

                        Map<String, Object> OnlineBusyOnRide = new HashMap<>();
                        OnlineBusyOnRide.put(FirebaseConstant.ON_LINE_BUSY_ON_RIDE, Rider.OnlineBusyOnRide);
                        dataSnapshot.getChildren().iterator().next().getRef().updateChildren(OnlineBusyOnRide);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
