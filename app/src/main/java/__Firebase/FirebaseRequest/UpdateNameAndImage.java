package __Firebase.FirebaseRequest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallbackMain;

/**
 * Created by User on 3/8/2018.
 */

public class UpdateNameAndImage {

    private RiderModel Rider;
    private ICallbackMain iCallbackMain;

    public UpdateNameAndImage(RiderModel Rider, ICallbackMain iCallbackMain) {
        this.Rider = Rider;
        this.iCallbackMain = iCallbackMain;
        this.Request();
    }

    private void Request() {

        FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
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

                        Log.d(FirebaseConstant.UPDATE_NAME_IMAGE_URL, FirebaseConstant.UPDATE_NAME_IMAGE_URL);
                        iCallbackMain.OnUpdateNameAndImage(true);
                    } else {
                        iCallbackMain.OnUpdateNameAndImage(false);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                iCallbackMain.OnUpdateNameAndImage(false);
            }
        });
    }
}
