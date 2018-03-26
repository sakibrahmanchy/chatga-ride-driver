package __Firebase.FirebaseRequest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import __Firebase.Exception.FabricExceptionLog;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallbackMain;

/**
 * Created by User on 3/8/2018.
 */

public class UpdateNameImageAndRatting {

    private RiderModel Rider;
    private ICallbackMain iCallbackMain;

    public UpdateNameImageAndRatting(RiderModel Rider, ICallbackMain iCallbackMain) {
        this.Rider = Rider;
        this.iCallbackMain = iCallbackMain;
        this.Request();
    }

    private void Request() {

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        try {
            firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.RIDER).orderByChild(FirebaseConstant.RIDER_ID).equalTo(Rider.RiderID).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        if (dataSnapshot.getChildren().iterator().hasNext()) {

                            Map<String, Object> updateName = new HashMap<>();
                            updateName.put(FirebaseConstant.FULL_NAME, Rider.FullName);
                            dataSnapshot.getChildren().iterator().next().getRef().updateChildren(updateName);

                            Map<String, Object> updateImage = new HashMap<>();
                            updateImage.put(FirebaseConstant.IMAGE_URL, Rider.ImageUrl);
                            dataSnapshot.getChildren().iterator().next().getRef().updateChildren(updateImage);

                            Map<String, Object> updateRatting = new HashMap<>();
                            updateRatting.put(FirebaseConstant.RATTING, Rider.Ratting);
                            dataSnapshot.getChildren().iterator().next().getRef().updateChildren(updateRatting);

                            FabricExceptionLog.printLog(this.getClass().getSimpleName(), FirebaseConstant.UPDATE_NAME_IMAGE_URL);
                            iCallbackMain.OnUpdateNameAndImage(true);
                        } else {
                            iCallbackMain.OnUpdateNameAndImage(false);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    iCallbackMain.OnUpdateNameAndImage(false);
                    FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), databaseError.toString());
                }
            });
        } catch (Exception e) {
            FabricExceptionLog.sendLogToFabric(true, this.getClass().getSimpleName(), e.toString());
        }
    }
}

