package __Firebase.Notification;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.chaatgadrive.arif.chaatgadrive.MainActivity;

import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.RiderModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.ICallbacklisteners.INotificationListener;

/**
 * Created by User on 2/12/2018.
 */

public class NotificationWrapper extends AppCompatActivity implements INotificationListener {

    private static Context context = MainActivity.getContextOfApplication();

    public NotificationWrapper() {
    }

    public boolean SendInitialAcceptanceOfRide(final RiderModel Rider, final ClientModel Client, INotificationListener iNotificationListener) {

        if (iNotificationListener == null) iNotificationListener = this;

        if (Rider == null || Client == null || iNotificationListener == null) return false;
        if (Rider.RiderID < 1 || Client.ClientID < 1 || Client.DeviceToken.isEmpty()) return false;

        SendInitialAcceptanceOfRide pendingTask = new SendInitialAcceptanceOfRide(this, iNotificationListener);
        pendingTask.execute(
                Long.toString(Rider.RiderID),
                Client.DeviceToken
        );
        finish();
        return true;
    }

    public boolean SendFinalAcceptanceOfRide(final RiderModel Rider, final ClientModel Client, INotificationListener iNotificationListener) {

        if (iNotificationListener == null) iNotificationListener = this;

        if (Rider == null || Client == null || iNotificationListener == null) return false;
        if (Rider.RiderID < 1 || Client.ClientID < 1 || Client.DeviceToken.isEmpty()) return false;

        SendFinalAcceptanceOfRide pendingTask = new SendFinalAcceptanceOfRide(this, iNotificationListener);
        pendingTask.execute(
                Long.toString(Rider.RiderID),
                Client.DeviceToken
        );
        finish();
        return true;
    }

    public boolean SendFinishedRide(final RiderModel Rider, final ClientModel Client, long FinalCost, INotificationListener iNotificationListener) {

        if (iNotificationListener == null) iNotificationListener = this;

        if (Rider == null || Client == null || iNotificationListener == null) return false;
        if (Rider.RiderID < 1 || Client.ClientID < 1 || Client.DeviceToken.isEmpty()) return false;

        SendFinishedRide pendingTask = new SendFinishedRide(this, iNotificationListener);
        pendingTask.execute(
                Long.toString(Rider.RiderID),
                Client.DeviceToken,
                Long.toString(FinalCost)
        );
        finish();
        return true;
    }

    public boolean SendCancelRide(final RiderModel Rider, final ClientModel Client, INotificationListener iNotificationListener) {

        if (iNotificationListener == null) iNotificationListener = this;

        if (Rider == null || Client == null || iNotificationListener == null) return false;
        if (Rider.RiderID < 1 || Client.ClientID < 1 || Client.DeviceToken.isEmpty()) return false;

        SendCancelRide pendingTask = new SendCancelRide(this, iNotificationListener);
        pendingTask.execute(
                Long.toString(Rider.RiderID),
                Client.DeviceToken
        );
        finish();
        return true;
    }

    @Override
    public void OnNotificationResponse(boolean value, int action) {

        switch (action) {
            case FirebaseConstant.INT_CANCEL_RIDE_NOTIFY_CLIENT: {
                Log.d(FirebaseConstant.NOTIFICATION_RESPONSE, ("CANCEL_RIDE_NOTIFY_CLIENT"));
                break;
            }
            case FirebaseConstant.INT_FINISH_RIDE_NOTIFY_CLIENT: {
                Log.d(FirebaseConstant.NOTIFICATION_RESPONSE, ("FINISH_RIDE_NOTIFY_CLIENT"));
                break;
            }
            case FirebaseConstant.INT_FINAL_ACCEPTANCE_NOTIFY_CLIENT: {
                Log.d(FirebaseConstant.NOTIFICATION_RESPONSE, ("FINAL_ACCEPTANCE_NOTIFY_CLIENT"));
                break;
            }
            case FirebaseConstant.INT_INITIAL_ACCEPTANCE_NOTIFY_CLIENT: {
                Log.d(FirebaseConstant.NOTIFICATION_RESPONSE, ("INITIAL_ACCEPTANCE_NOTIFY_CLIENT"));
                break;
            }
        }
    }
}
