package __Firebase.FirebaseRequest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.Callbacklisteners.ICallbackMain;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 12/8/2017.
 */

public class GetCurrentRider {

    private long RiderID = 0;
    private ICallbackMain callBackListener = null;

    public GetCurrentRider(long RiderID, ICallbackMain callBackListener){
        this.RiderID = RiderID;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        final FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(this.RiderID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    DataSnapshot snp = dataSnapshot.getChildren().iterator().next();
                    if(snp.exists()) {
                        firebaseWrapper.getRiderModelInstance().LoadData(snp);
                        Log.d(FirebaseConstant.RIDER_LOADED, FirebaseConstant.RIDER_LOADED);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBackListener.OnResponseGetRiderModel(false);
            }
        });
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(this.RiderID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    callBackListener.OnResponseGetRiderModel(true);
                }else {
                    callBackListener.OnResponseGetRiderModel(false);
                }
            }

            public void onCancelled(DatabaseError databaseError) {
                callBackListener.OnResponseGetRiderModel(false);
                Log.d(FirebaseConstant.RIDER_LOADED_ERROR, databaseError.toString());
            }
        });
    }
}