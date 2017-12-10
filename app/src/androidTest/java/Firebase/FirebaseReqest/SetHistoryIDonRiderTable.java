package Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import Firebase.Callbacklisteners.CallBackListener;
import Firebase.FirebaseModel.CurrentRidingHistoryModel;
import Firebase.FirebaseModel.RiderModel;
import Firebase.FirebaseUtility.FirebaseConstant;
import Firebase.FirebaseWrapper;

/**
 * Created by User on 11/27/2017.
 */

public class SetHistoryIDonRiderTable {

    private RiderModel Rider = null;
    private CurrentRidingHistoryModel HistoryModel;
    private CallBackListener callBackListener = null;

    public SetHistoryIDonRiderTable(final CurrentRidingHistoryModel HistoryModel, final RiderModel Rider, final CallBackListener callBackListener){
        this.Rider = Rider;
        this.HistoryModel = HistoryModel;
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
                    update.put(FirebaseConstant.CURRENT_RIDING_HISTORY, HistoryModel.HistoryID);
                    dataSnapshot.getChildren().iterator().next().getRef().updateChildren(update);

                    Log.d(FirebaseConstant.CURRENT_RIDING_HISTORY, FirebaseConstant.CURRENT_RIDING_HISTORY);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
