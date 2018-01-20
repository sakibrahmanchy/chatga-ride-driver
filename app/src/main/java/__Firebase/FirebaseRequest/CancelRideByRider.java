package __Firebase.FirebaseRequest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import __Firebase.FirebaseModel.ClientModel;
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

    public CancelRideByRider(CurrentRidingHistoryModel HistoryModel, RiderModel Rider, ICallbackMain callBackListener){
        this.HistoryModel = HistoryModel;
        this.Rider = Rider;
        this.callBackListener = callBackListener;

        Request();
    }

    private void Request(){
        /*
        * If Rider == null then no initial acceptance by rider
        * If Rider == model Notify rider
        */

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY)
                .orderByChild(FirebaseConstant.RIDER_HISTORY).
                equalTo(Rider.RiderID + FirebaseConstant.JOIN + HistoryModel.HistoryID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {

                        HashMap<String, Object> CancelRideByRider = new HashMap<>();
                        CancelRideByRider.put(FirebaseConstant.CANCEL_RIDE_BY_RIDER, HistoryModel.RideCanceledByClient);
                        dataSnapshot.getChildren().iterator().next().getRef().updateChildren(CancelRideByRider);

                        Log.d(FirebaseConstant.CANCELED_RIDE_BY_RIDER, FirebaseConstant.CANCELED_RIDE_BY_RIDER);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
