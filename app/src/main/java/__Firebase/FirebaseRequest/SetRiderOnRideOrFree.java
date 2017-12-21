package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import __Firebase.ICallbacklisteners.ICallbackMain;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/25/2017.
 */

public class SetRiderOnRideOrFree {

    private RiderModel Rider = null;
    private ICallbackMain callBackListener = null;

    public SetRiderOnRideOrFree(RiderModel Rider, ICallbackMain callBackListener){
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
                        update.put(FirebaseConstant.IS_RIDER_ON_RIDE, Rider.IsRiderOnRide);
                        dataSnapshot.getChildren().iterator().next().getRef().updateChildren(update);

                        Log.d(FirebaseConstant.IS_RIDER_ON_RIDE, FirebaseConstant.IS_RIDER_ON_RIDE);
                        callBackListener.OnResponseSetRiderOnRideOrFree(true);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBackListener.OnResponseSetRiderOnRideOrFree(false);
            }
        });
    }
}
