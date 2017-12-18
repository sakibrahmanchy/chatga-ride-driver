package __Firebase.FirebaseRequest;

import android.util.Pair;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import __Firebase.Callbacklisteners.ICallbackMain;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 11/28/2017.
 */

public class UpdateRiderLocation {

    private RiderModel Rider = null;
    private Pair<Double, Double> NewLocation = null;
    private ICallbackMain callBackListener = null;

    public UpdateRiderLocation(RiderModel Rider, ICallbackMain callBackListener){
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

                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if(dataSnapshot.getChildren().iterator().hasNext()) {
                        DataSnapshot dsp = dataSnapshot.getChildren().iterator().next();
                        if(dsp.exists() && dsp.hasChildren()) {
                            if (dsp.getChildren().iterator().hasNext()) {

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
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
