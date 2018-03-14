package __Firebase.FirebaseModel;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import __Firebase.FirebaseUtility.FirebaseConstant;

/**
 * Created by User on 3/13/2018.
 */

public class AppSettingsModel {

    public int CurrentUpdateVersion;
    public int ForceUpdateInterval;
    public int ConsecutiveRequestInterval;
    public int NumberOfConsecutiveRequest;
    public int EachRequestBundleInterval;
    public String Message;
    public long ShortestDistance;
    public int ConsecutiveRequestAcceptInterval;

    public AppSettingsModel() {
    }

    public void LoadData(DataSnapshot snapshot) {
        try {
            AppSettingsModel appSettingsModel = snapshot.getValue(AppSettingsModel.class);
            this.CurrentUpdateVersion = appSettingsModel.CurrentUpdateVersion;
            this.ForceUpdateInterval = appSettingsModel.ForceUpdateInterval;
            this.NumberOfConsecutiveRequest = appSettingsModel.NumberOfConsecutiveRequest;
            this.EachRequestBundleInterval = appSettingsModel.EachRequestBundleInterval;
            this.ConsecutiveRequestInterval = appSettingsModel.ConsecutiveRequestInterval;
            this.Message = appSettingsModel.Message;
            this.ShortestDistance = appSettingsModel.ShortestDistance;
            this.ConsecutiveRequestAcceptInterval = appSettingsModel.ConsecutiveRequestAcceptInterval;
        } catch (Exception e) {
            Log.d(FirebaseConstant.APP_SETTINGS, e.getStackTrace().toString());
        }
    }
}
