package __Firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import __Firebase.FirebaseModel.*;
import __Firebase.FirebaseRequest.*;
import __Firebase.FirebaseResponse.*;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.ViewModel.RiderViewModel;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseWrapper {

    private static FirebaseWrapper Instance = null;

    public DatabaseReference FirebaseRootReference;
    private __FirebaseRequest FirebaseRequestInstance;
    private FirebaseResponse FirebaseResponseInstance;
    private RiderViewModel RiderViewModelInstance;
    private ClientModel ClientModel;
    private RiderModel RiderModel;
    private CurrentRidingHistoryModel CurrentRidingHistoryModel;

    private FirebaseWrapper(){
        FirebaseRootReference = FirebaseDatabase.getInstance().getReference();
        FirebaseRequestInstance = new __FirebaseRequest();
        FirebaseResponseInstance = new FirebaseResponse();
        //RiderViewModelInstance = new RiderViewModel();
        ClientModel = new ClientModel();
        RiderModel = new RiderModel();
        CurrentRidingHistoryModel = new CurrentRidingHistoryModel();
    }

    public static FirebaseWrapper getInstance() {
        if (Instance == null) {
            synchronized (FirebaseWrapper.class) {
                if (Instance == null)
                    Instance = new FirebaseWrapper();
            }
        }
        return Instance;
    }

    public __FirebaseRequest getFirebaseRequestInstance() {
        return FirebaseRequestInstance;
    }

    public FirebaseResponse getFirebaseResponseInstance() {
        return FirebaseResponseInstance;
    }

    public RiderViewModel getRiderViewModelInstance() {
        return RiderViewModelInstance;
    }

    public ClientModel getClientModelInstance() {
        return ClientModel;
    }

    public RiderModel getRiderModelInstance() {
        return RiderModel;
    }

    public CurrentRidingHistoryModel getRidingHistoryModelModelInstance() {
        return CurrentRidingHistoryModel;
    }

    public static String getDeviceToken(){
        Log.d(FirebaseConstant.FIREBASE_TOKEN, FirebaseInstanceId.getInstance().getToken().toString());
        return  FirebaseInstanceId.getInstance().getToken().toString();
    }

    public void ResponseUpdateLocation(RiderModel Rider){

        Query pendingTask = FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID);
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
                                    Log.d(FirebaseConstant.REQUEST_FOR_ADD_CHILD, ": " + dataSnapshot.getValue().toString());
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
