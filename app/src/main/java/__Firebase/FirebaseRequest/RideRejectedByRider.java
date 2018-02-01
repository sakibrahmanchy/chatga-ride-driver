package __Firebase.FirebaseRequest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallbackMain;

/**
 * Created by User on 2/1/2018.
 */

public class RideRejectedByRider {
    private long ClientId;
    private long Time;
    private long RiderId;
    private ICallbackMain callBackListener = null;

    public RideRejectedByRider(long ClientId, long RiderId, long Time, ICallbackMain callBackListener){
        this.ClientId = ClientId;
        this.RiderId = RiderId;
        this.Time = Time;
        this.callBackListener = callBackListener;

        Request();
    }

    private void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT)
                .orderByChild(FirebaseConstant.CLIENT_ID)
                .equalTo(ClientId).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {

                        HashMap<String, Object> CancelRideByRider = new HashMap<>();
                        CancelRideByRider.put(FirebaseConstant.RIDE_REJECTED_BY_RIDER, RiderId + (" ") + Time);
                        dataSnapshot.getChildren().iterator().next().getRef().updateChildren(CancelRideByRider);

                        Log.d(FirebaseConstant.REJECT_RIDE_BY_RIDER, FirebaseConstant.REJECT_RIDE_BY_RIDER);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
