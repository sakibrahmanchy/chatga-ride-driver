package __Firebase.FirebaseRequest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallbackMain;

/**
 * Created by User on 11/27/2017.
 */

public class FinishedRide {

    private CurrentRidingHistoryModel HistoryModel = null;
    private ICallbackMain callBackListener = null;

    public FinishedRide(CurrentRidingHistoryModel HistoryModel, ICallbackMain callBackListener) {
        this.HistoryModel = HistoryModel;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request() {

        try {
            FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
            Query updateDestination = firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).orderByChild(FirebaseConstant.HISTORY_ID).equalTo(HistoryModel.HistoryID);

            updateDestination.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        if (dataSnapshot.getChildren().iterator().hasNext()) {

                            Map<String, Object> FinishedRide = new HashMap<>();
                            FinishedRide.put(FirebaseConstant.IS_RIDE_FINISHED, HistoryModel.IsRideFinished);
                            dataSnapshot.getChildren().iterator().next().getRef().updateChildren(FinishedRide);

                            Map<String, Object> CostSoFar = new HashMap<>();
                            CostSoFar.put(FirebaseConstant.COST_SO_FAR, HistoryModel.CostSoFar);
                            dataSnapshot.getChildren().iterator().next().getRef().updateChildren(CostSoFar);

                            if ((dataSnapshot.getChildren().iterator().next()).hasChildren()) {
                                for (DataSnapshot ds : (dataSnapshot.getChildren().iterator().next()).getChildren()) {
                                    if (ds.getKey().equals(FirebaseConstant.ENDING_LOCATION)) {

                                        Map<String, Object> Longitude = new HashMap<>();
                                        Longitude.put(FirebaseConstant.LATITUDE, HistoryModel.EndingLocation.Latitude);
                                        ds.getRef().updateChildren(Longitude);

                                        Map<String, Object> Latitude = new HashMap<>();
                                        Latitude.put(FirebaseConstant.LONGITUDE, HistoryModel.EndingLocation.Longitude);
                                        ds.getRef().updateChildren(Latitude);
                                        callBackListener.OnFinishedRide(true);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callBackListener.OnFinishedRide(false);
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
