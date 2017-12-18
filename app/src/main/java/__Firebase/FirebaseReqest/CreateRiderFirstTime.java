package __Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

<<<<<<< Updated upstream:app/src/main/java/__Firebase/FirebaseReqest/CreateRiderFirstTime.java
import __Firebase.Callbacklisteners.CallBackListener;
import __Firebase.Callbacklisteners.ICallbackMain;
=======
import __Firebase.ICallbacklisteners.ICallbackMain;
>>>>>>> Stashed changes:app/src/main/java/__Firebase/FirebaseRequest/CreateRiderFirstTime.java
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/23/2017.
 */

public class CreateRiderFirstTime {

    private RiderModel Rider = null;
    private ICallbackMain callBackListener = null;

    public CreateRiderFirstTime(RiderModel Rider, ICallbackMain callBackListener){
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
                            callBackListener.OnResponseCreateNewRider(true);
                        }
                    });

        } catch (Exception e) {
            callBackListener.OnResponseCreateNewRider(false);
            e.printStackTrace();
            Log.d(FirebaseConstant.NEW_RIDER_ERROR, e.toString());
        }
    }
}
