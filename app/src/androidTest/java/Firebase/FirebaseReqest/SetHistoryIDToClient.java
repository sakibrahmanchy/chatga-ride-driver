package Firebase.FirebaseReqest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import Firebase.Callbacklisteners.CallBackListener;
import Firebase.FirebaseModel.ClientModel;
import Firebase.FirebaseModel.CurrentRidingHistoryModel;
import Firebase.FirebaseUtility.FirebaseConstant;
import Firebase.FirebaseWrapper;

/**
 * Created by User on 11/23/2017.
 */

public class SetHistoryIDToClient {

    private CurrentRidingHistoryModel HistoryModel = null;
    private ClientModel Client = null;
    private CallBackListener callBackListener = null;

    public SetHistoryIDToClient(CurrentRidingHistoryModel HistoryModel, ClientModel Client, CallBackListener callBackListener){
        this.HistoryModel = HistoryModel;
        this.Client = Client;
        this.callBackListener = callBackListener;

        Request();
    }


    public void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.CLIENT).orderByChild(FirebaseConstant.CLIENT_ID).equalTo(Client.ClientID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, Object> UpdatedCost = new HashMap<>();
                UpdatedCost.put(FirebaseConstant.CURRENT_RIDING_HISTORY_ID, HistoryModel.HistoryID);

                if(dataSnapshot.getChildren().iterator().hasNext()) {
                    DataSnapshot snp = dataSnapshot.getChildren().iterator().next();
                    snp.getRef().updateChildren(UpdatedCost);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(FirebaseConstant.CURRENT_RIDING_HISTORY_ID, databaseError.toString());
            }
        });
    }
}
