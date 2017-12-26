package __Firebase.ICallbacklisteners;

import __Firebase.FirebaseResponse.NotificationModel;

/**
 * Created by User on 11/21/2017.
 */

public interface CallBackListener {
    void onRequestCompletion(boolean value);
    void onRiderCreatedFirstTime(boolean value);
    void  onSetRiderBusyOrFree(boolean value);
    void onSetRiderOnRideOrFree(boolean value);
    void onSetRiderOnLineOrOffLine(boolean value);
    void onSetRiderOnlineBusyOnRider(boolean value);
    void onSetHistoryIDonRiderTable(boolean value);
    void onCreateNewRideHistory(boolean value);
    void onGetCurrentClient(boolean value);
    void onGetNotificationModel(NotificationModel notificationModel);
}
