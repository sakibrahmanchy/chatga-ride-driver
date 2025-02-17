package __Firebase.FirebaseRequest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.CallBackListener;

/**
 * Created by User on 12/9/2017.
 */

public class GetRecentHistory {

    private long HistoryID = 0;
    private CallBackListener callBackListener = null;

    public GetRecentHistory(long RiderID, CallBackListener callBackListener) {
        this.HistoryID = RiderID;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request() {

        final FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).orderByChild(FirebaseConstant.HISTORY_ID).equalTo(this.HistoryID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        DataSnapshot snp = dataSnapshot.getChildren().iterator().next();
                        if (snp.exists()) {
                            firebaseWrapper.getRidingHistoryModelModelInstance().LoadData(snp);
                            FabricExceptionLog.printLog(this.getClass().getSimpleName(), FirebaseConstant.HISTORY_LOADED);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callBackListener.OnGetHistoryModel(false);
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }

        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).orderByChild(FirebaseConstant.HISTORY_ID).equalTo(this.HistoryID).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        callBackListener.OnGetHistoryModel(true);
                    } else {
                        callBackListener.OnGetHistoryModel(false);
                    }
                }

                public void onCancelled(DatabaseError databaseError) {
                    callBackListener.OnGetHistoryModel(false);
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}