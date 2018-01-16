package __Firebase.FirebaseResponse;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.CallBackListener;
import __Firebase.FirebaseModel.RiderModel;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseResponse {

    private static long HistoryID = 0;
    public FirebaseResponse(){
    }

    public static void RiderCanceledByRiderResponse(long historyID) {

        HistoryID = historyID;
        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        Query pendingTask = firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.HISTORY).orderByChild(FirebaseConstant.HISTORY_ID).equalTo(HistoryID);

        pendingTask.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {

                        DataSnapshot dsp = dataSnapshot.getChildren().iterator().next();
                        dsp.getRef().child(FirebaseConstant.RIDE_CANCEL_BY_CLIENT).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    int data = Integer.parseInt(dataSnapshot.getValue().toString());
                                    if (data != 1) {
                                        new RiderCanceledByClient();
                                    }
                                }
                                Log.d(FirebaseConstant.RIDE_CANCELED, ":: " + dataSnapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
