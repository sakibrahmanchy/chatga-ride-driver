package __Firebase.FirebaseRequest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.CallBackListener;
import __Firebase.ICallbacklisteners.ICallbackMain;

/**
 * Created by User on 11/23/2017.
 */

public class GetCurrentClient {

    private long ClientID = 0;
    private CallBackListener callBackListener = null;

    public GetCurrentClient(long ClientID, CallBackListener callBackListener) {
        this.ClientID = ClientID;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request() {

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT)
                .orderByChild(FirebaseConstant.CLIENT_ID)
                .equalTo(this.ClientID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (dataSnapshot.getChildren().iterator().hasNext()) {
                        DataSnapshot snp = dataSnapshot.getChildren().iterator().next();
                        if(snp.exists()) {
                            FirebaseWrapper.getInstance().getClientModelInstance().LoadData(snp);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(FirebaseConstant.CLIENT_LOADED_ERROR, databaseError.toString());
            }
        });
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT).orderByChild(FirebaseConstant.CLIENT_ID).equalTo(this.ClientID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    callBackListener.OnGetCurrentClient(true);
                    Log.d(FirebaseConstant.CLIENT_LOADED, FirebaseConstant.CLIENT_LOADED + FirebaseWrapper.getInstance().getClientModelInstance().FullName);
                } else {
                    callBackListener.OnGetCurrentClient(false);
                }
            }

            public void onCancelled(DatabaseError databaseError) {
                Log.d(FirebaseConstant.CLIENT_LOADED_ERROR, databaseError.toString());
                callBackListener.OnGetCurrentClient(false);
            }
        });
    }
}
