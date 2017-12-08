package __Firebase.Callbacklisteners;

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
}
