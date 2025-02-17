package Firebase.FirebaseReqest;

import android.util.Pair;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import Firebase.Callbacklisteners.CallBackListener;
import Firebase.FirebaseModel.RiderModel;
import Firebase.FirebaseUtility.FirebaseConstant;
import Firebase.FirebaseWrapper;

/**
 * Created by User on 11/28/2017.
 */

public class UpdateRiderLocation {

    private RiderModel Rider = null;
    private Pair<Double, Double> NewLocation = null;
    private CallBackListener callBackListener = null;

    public UpdateRiderLocation(RiderModel Rider, CallBackListener callBackListener){
        this.Rider = Rider;
        this.callBackListener = callBackListener;

        Request();
    }

    public void Request(){

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        Query updateLocation = firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID);

        updateLocation.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildren().iterator().hasNext()) {
                    DataSnapshot dsp = dataSnapshot.getChildren().iterator().next();
                    if(dsp.getChildren().iterator().hasNext()) {

                        Map<String, Object> RequestForUpdateLocation = new HashMap<>();
                        RequestForUpdateLocation.put(FirebaseConstant.REQUEST_FOR_UPDATE_LOCATION, Rider.CurrentRiderLocation.RequestForUpdateLocation);
                        dsp.getChildren().iterator().next().getRef().updateChildren(RequestForUpdateLocation);

                        Map<String, Object> Longitude = new HashMap<>();
                        Longitude.put(FirebaseConstant.LATITUDE, Rider.CurrentRiderLocation.Latitude);
                        dsp.getChildren().iterator().next().getRef().updateChildren(Longitude);

                        Map<String, Object> Latitude = new HashMap<>();
                        Latitude.put(FirebaseConstant.LONGITUDE, Rider.CurrentRiderLocation.Longitude);
                        dsp.getChildren().iterator().next().getRef().updateChildren(Latitude);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
