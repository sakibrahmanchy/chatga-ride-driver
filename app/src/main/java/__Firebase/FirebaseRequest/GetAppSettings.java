package __Firebase.FirebaseRequest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import __Firebase.ICallbacklisteners.ICallbackMain;

/**
 * Created by User on 3/13/2018.
 */

public class GetAppSettings {

    private ICallbackMain iCallbackMain;

    public GetAppSettings(ICallbackMain iCallbackMain) {
        this.iCallbackMain = iCallbackMain;
        this.Request();
    }

    public void Request() {

        final FirebaseWrapper firebaseWrapper = FirebaseWrapper.getInstance();
        firebaseWrapper.FirebaseRootReference.child(FirebaseConstant.APP_SETTINGS_INFO).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    DataSnapshot snp = dataSnapshot;
                    if (snp.exists()) {
                        firebaseWrapper.getAppSettingsModelInstance().LoadData(snp);
                        Log.d(FirebaseConstant.APP_SETTINGS_LOADED, FirebaseConstant.APP_SETTINGS_LOADED);
                        iCallbackMain.OnAppSettingsLoaded(true);
                    }
                } else {
                    iCallbackMain.OnAppSettingsLoaded(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                iCallbackMain.OnAppSettingsLoaded(false);
            }
        });
    }
}
