package __Firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import __Firebase.FirebaseModel.AppSettingsModel;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseRequest.__FirebaseRequest;
import __Firebase.FirebaseResponse.FirebaseResponse;
import __Firebase.FirebaseResponse.NotificationModel;
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
    private NotificationModel NotificationModel;
    private AppSettingsModel AppSettingsModel;

    private FirebaseWrapper() {
        FirebaseRootReference = FirebaseDatabase.getInstance().getReference();
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
        if (FirebaseRequestInstance == null) {
            synchronized (FirebaseWrapper.class) {
                if (FirebaseRequestInstance == null)
                    FirebaseRequestInstance = new __FirebaseRequest();
            }
        }
        return FirebaseRequestInstance;
    }

    public FirebaseResponse getFirebaseResponseInstance() {
        return FirebaseResponseInstance;
    }

    public RiderViewModel getRiderViewModelInstance() {
        if (RiderViewModelInstance == null) {
            synchronized (FirebaseWrapper.class) {
                if (RiderViewModelInstance == null)
                    RiderViewModelInstance = new RiderViewModel();
            }
        }
        return RiderViewModelInstance;
    }

    public ClientModel getClientModelInstance() {
        if (ClientModel == null) {
            synchronized (FirebaseWrapper.class) {
                if (ClientModel == null)
                    ClientModel = new ClientModel();
            }
        }
        return ClientModel;
    }

    public RiderModel getRiderModelInstance() {
        if (RiderModel == null) {
            synchronized (FirebaseWrapper.class) {
                if (RiderModel == null)
                    RiderModel = new RiderModel();
            }
        }
        return RiderModel;
    }

    public CurrentRidingHistoryModel getRidingHistoryModelModelInstance() {
        if (CurrentRidingHistoryModel == null) {
            synchronized (FirebaseWrapper.class) {
                if (CurrentRidingHistoryModel == null)
                    CurrentRidingHistoryModel = new CurrentRidingHistoryModel();
            }
        }
        return CurrentRidingHistoryModel;
    }

    public NotificationModel getNotificationModelInstance() {
        if (NotificationModel == null) {
            synchronized (FirebaseWrapper.class) {
                if (NotificationModel == null)
                    NotificationModel = new NotificationModel();
            }
        }
        return NotificationModel;
    }

    public AppSettingsModel getAppSettingsModelInstance() {
        if (AppSettingsModel == null) {
            synchronized (FirebaseWrapper.class) {
                if (AppSettingsModel == null)
                    AppSettingsModel = new AppSettingsModel();
            }
        }
        return AppSettingsModel;
    }

    public static String getDeviceToken() {
        Log.d(FirebaseConstant.FIREBASE_TOKEN, FirebaseInstanceId.getInstance().getToken().toString());
        if(FirebaseInstanceId.getInstance() != null) {
            return FirebaseInstanceId.getInstance().getToken().toString();
        }else{
            return FirebaseConstant.FIREBASE_NOT_INITIALIZE;
        }
    }
}
