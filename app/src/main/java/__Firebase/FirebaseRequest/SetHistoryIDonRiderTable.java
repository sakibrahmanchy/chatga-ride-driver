package __Firebase.FirebaseRequest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallbackMain;

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

        try {
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

                            FabricExceptionLog.printLog(this.getClass().getSimpleName(), FirebaseConstant.CURRENT_RIDING_HISTORY);
                            callBackListener.OnSetHistoryIDonRiderTable(true);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callBackListener.OnSetHistoryIDonRiderTable(false);
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
