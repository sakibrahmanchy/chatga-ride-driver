package __Firebase.FirebaseRequest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.Callbacklisteners.ICallbackMain;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 12/9/2017.
 */

public class GetRecentHistory {

    private long HistoryID = 0;
    private ICallbackMain callBackListener = null;

    public GetRecentHistory(long RiderID, ICallbackMain callBackListener){
        this.HistoryID = RiderID;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        final FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).orderByChild(FirebaseConstant.HISTORY_ID).equalTo(this.HistoryID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    DataSnapshot snp = dataSnapshot.getChildren().iterator().next();
                    if(snp.exists()) {
                        firebaseWrapper.getRidingHistoryModelModelInstance().LoadData(snp);
                        Log.d(FirebaseConstant.HISTORY_LOADED, FirebaseConstant.HISTORY_LOADED);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBackListener.OnResponseGetHistoryModel(false);
            }
        });
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(this.HistoryID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    callBackListener.OnResponseGetHistoryModel(true);
                }else {
                    callBackListener.OnResponseGetHistoryModel(false);
                }
            }

            public void onCancelled(DatabaseError databaseError) {
                callBackListener.OnResponseGetHistoryModel(false);
                Log.d(FirebaseConstant.HISTORY_LOADED_ERROR, databaseError.toString());
            }
        });
    }
}