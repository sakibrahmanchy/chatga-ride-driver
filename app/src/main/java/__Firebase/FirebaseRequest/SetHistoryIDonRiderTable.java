package __Firebase.FirebaseRequest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.model.SnappedPoint;

import java.util.HashMap;
import java.util.Map;

import __Firebase.ICallbacklisteners.ICallbackMain;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/27/2017.
 */

public class SetHistoryIDonRiderTable {

    private RiderModel Rider = null;
    private CurrentRidingHistoryModel HistoryModel;
    private ICallbackMain callBackListener = null;

    public SetHistoryIDonRiderTable(CurrentRidingHistoryModel HistoryModel, RiderModel Rider, ICallbackMain callBackListener) {
        this.Rider = Rider;
        this.HistoryModel = HistoryModel;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request() {

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {

                        Map<String, Object> update = new HashMap<>();
                        update.put(FirebaseConstant.CURRENT_RIDING_HISTORY, Rider.CurrentRidingHistoryID);
                        DataSnapshot snp = dataSnapshot.getChildren().iterator().next();

                        if (snp.exists()) {
                            snp.getRef().updateChildren(update);
                        }

                        Log.d(FirebaseConstant.CURRENT_RIDING_HISTORY, FirebaseConstant.CURRENT_RIDING_HISTORY);
                        callBackListener.OnSetHistoryIDonRiderTable(true);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBackListener.OnSetHistoryIDonRiderTable(false);
            }
        });
    }
}
