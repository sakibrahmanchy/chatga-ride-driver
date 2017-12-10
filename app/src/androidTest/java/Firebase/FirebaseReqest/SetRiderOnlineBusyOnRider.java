package Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import Firebase.Callbacklisteners.CallBackListener;
import Firebase.FirebaseModel.RiderModel;
import Firebase.FirebaseUtility.FirebaseConstant;
import Firebase.FirebaseWrapper;

/**
 * Created by User on 11/27/2017.
 */

public class SetRiderOnlineBusyOnRider {
    private RiderModel Rider = null;
    private int value;
    private CallBackListener callBackListener = null;

    public SetRiderOnlineBusyOnRider(RiderModel Rider, int value, CallBackListener callBackListener){
        this.Rider = Rider;
        this.value = value;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildren().iterator().hasNext()) {
                    Map<String, Object> update = new HashMap<>();
                    update.put(FirebaseConstant.ON_LINE_BUSY_ON_RIDE, value);
                    dataSnapshot.getChildren().iterator().next().getRef().updateChildren(update);

                    Log.d(FirebaseConstant.ON_LINE_BUSY_ON_RIDE, FirebaseConstant.ON_LINE_BUSY_ON_RIDE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}