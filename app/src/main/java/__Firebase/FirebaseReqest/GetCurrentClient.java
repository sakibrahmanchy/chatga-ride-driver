package __Firebase.FirebaseReqest;

import android.util.Log;

import com.chaatgadrive.arif.chaatgadrive.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.Callbacklisteners.CallBackListener;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/23/2017.
 */

public class GetCurrentClient {

    private long ClientID = 0;
    private CallBackListener callBackListener = null;

    public GetCurrentClient(long ClientID, CallBackListener callBackListener){
        this.ClientID = ClientID;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT)
                .orderByChild(FirebaseConstant.CLIENT_ID).equalTo(this.ClientID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snp = dataSnapshot.getChildren().iterator().next();
                //MainActivity.firebaseWrapper.getClientModelInstance().LoadData(snp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT)
                .orderByChild(FirebaseConstant.CLIENT_ID).equalTo(this.ClientID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callBackListener.onGetCurrentClient(true);
                //Log.d(FirebaseConstant.CLIENT_LOADED, MainActivity.firebaseWrapper.getClientModelInstance().FullName);
            }

            public void onCancelled(DatabaseError databaseError) {
                Log.d(FirebaseConstant.CLIENT_LOADED_ERROR, databaseError.toString());
            }
        });
    }
}
