package Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import Firebase.Callbacklisteners.CallBackListener;
import Firebase.FirebaseModel.RiderModel;
import Firebase.FirebaseUtility.FirebaseConstant;
import Firebase.FirebaseWrapper;

/**
 * Created by User on 11/23/2017.
 */

public class CreateRiderFirstTime {

    private RiderModel Rider = null;
    private CallBackListener callBackListener = null;

    public CreateRiderFirstTime(RiderModel Rider, CallBackListener callBackListener){
        this.Rider = Rider;
        this.callBackListener = callBackListener;

        Request();
    }

    private void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).push()
                    .setValue(Rider, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            callBackListener.onRiderCreatedFirstTime(true);
                        }
                    });

        } catch (Exception e) {
            callBackListener.onRiderCreatedFirstTime(false);
            e.printStackTrace();
            Log.d(FirebaseConstant.NEW_RIDER_ERROR, e.toString());
        }
    }
}
