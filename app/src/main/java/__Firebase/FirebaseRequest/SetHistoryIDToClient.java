package __Firebase.FirebaseRequest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallbackMain;

/**
 * Created by User on 11/23/2017.
 */

public class SetHistoryIDToClient {

    private CurrentRidingHistoryModel HistoryModel = null;
    private ClientModel Client = null;
    private ICallbackMain callBackListener = null;
    private long Time;

    public SetHistoryIDToClient(CurrentRidingHistoryModel HistoryModel, ClientModel Client, long Time, ICallbackMain callBackListener) {
        this.HistoryModel = HistoryModel;
        this.Client = Client;
        this.callBackListener = callBackListener;
        this.Time = Time;
        Request();
    }


    public void Request() {

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT).orderByChild(FirebaseConstant.CLIENT_ID).equalTo(Client.ClientID).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        HashMap<String, Object> UpdateHistory = new HashMap<>();
                        UpdateHistory.put(FirebaseConstant.CURRENT_RIDING_HISTORY_ID, HistoryModel.HistoryID + (" ") + String.valueOf(Time));

                        if (dataSnapshot.getChildren().iterator().hasNext()) {
                            DataSnapshot snp = dataSnapshot.getChildren().iterator().next();
                            snp.getRef().updateChildren(UpdateHistory);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}
