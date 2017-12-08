package __Firebase.FirebaseResponse;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import __Firebase.Callbacklisteners.CallBackListener;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 12/5/2017.
 */

public class ResponseUpdateLocation {

    private RiderModel Rider = null;
    private CallBackListener callBackListener = null;

    public ResponseUpdateLocation(RiderModel riderModel, CallBackListener callBackListener){
        this.Rider = riderModel;
        this.callBackListener = callBackListener;

        Response();
    }

    public void Response(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        Query pendingTask =  firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID);

        pendingTask.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {

                        DataSnapshot dsp = dataSnapshot.getChildren().iterator().next();
                        dsp.getRef().addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Log.d(FirebaseConstant.REQUEST_FOR_ADD_CHILD, ":: " + dataSnapshot.getValue().toString());
                                }
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
