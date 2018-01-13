package __Firebase.ICallbacklisteners;

/**
 * Created by User on 12/8/2017.
 */

public interface ICallbackMain {
    void OnResponseCreateNewRider(boolean value);
    void OnResponseCreateNewHistory(boolean value);
    void OnResponseGetRiderModel(boolean value);
    void OnResponseSetRiderBusyOrFree(boolean value);
    void OnResponseSetRiderOnRideOrFree(boolean value);
    void OnResponseSetRiderOnLineOffLine(boolean value);
    void OnResponseSetRiderOnlineBusyOnRide(boolean value);
    void OnResponseGetHistoryModel(boolean value);
    void OnSetHistoryIDonRiderTable(boolean value);
    void OnGetCurrentClient(boolean value);
    void OnResetRiderStatus(boolean value);
    void OnOnIsRiderAlreadyCreated(boolean value);
    void OnSetDeviceTokenToRiderTable(boolean value);
    void OnSentNotificationToClient(boolean value);
    void OnFinalAcceptanceOfRide(boolean value);
    void OnFinishedRide(boolean value);
}
