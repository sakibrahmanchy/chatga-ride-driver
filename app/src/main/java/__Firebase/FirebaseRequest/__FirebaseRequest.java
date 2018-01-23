package __Firebase.FirebaseRequest;

import android.support.v7.app.AppCompatActivity;
import android.util.Pair;

import __Firebase.ICallbacklisteners.CallBackListener;
import __Firebase.ICallbacklisteners.ICallbackMain;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;

/**
 * Created by User on 11/16/2017.
 */

public class __FirebaseRequest extends AppCompatActivity {

    private CallBackListener callBackListener = null;

    public __FirebaseRequest(){

    }

    public void IsRiderAlreadyCreated(final RiderModel Rider, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new IsRiderAlreadyCreated(Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void CreateRiderFirstTime(final RiderModel Rider, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new CreateRiderFirstTime(Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void SetRiderBusyOrFree(final RiderModel Rider, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new SetRiderBusyOrFree(Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void SetRiderOnRideOrFree(final RiderModel Rider, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new SetRiderOnRideOrFree(Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void SetRiderOnLineOrOffLine(final RiderModel Rider, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new SetRiderOnLineOrOffLine(Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void SentNotificationToClient(final RiderModel Rider, final String clientDeviceToken, final ICallbackMain callbackListener){

        SentNotificationToClient pendingTask = new SentNotificationToClient(this, callbackListener);
        pendingTask.execute(
                Long.toString(Rider.RiderID),
                Rider.FullName,
                Long.toString(Rider.PhoneNumber),
                clientDeviceToken
        );
        finish();
    }

    public void SetRiderOnlineBusyOnRider(final RiderModel Rider, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new SetRiderOnlineBusyOnRider(Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void SetDeviceTokenToRiderTable(final RiderModel Rider, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new SetDeviceTokenToRiderTable(Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void SetHistoryIDonRiderTable(final CurrentRidingHistoryModel HistoryModel, final RiderModel Rider, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new SetHistoryIDonRiderTable(HistoryModel, Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void CreateNewRideHistory(final CurrentRidingHistoryModel HistoryModel, final ICallbackMain callBackListener){

        Thread thread = new Thread(){

            @Override
            public void run(){
                new CreateNewRideHistory(HistoryModel, callBackListener);
            }
        };
        thread.start();
    }

    public void FinishedRide(final CurrentRidingHistoryModel HistoryModel, final ICallbackMain callBackListener){

        Thread thread = new Thread(){

            @Override
            public void run(){
                new FinishedRide(HistoryModel, callBackListener);
            }
        };
        thread.start();
    }

    public void ResetRiderStatus(final RiderModel Rider, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new ResetRiderStatus(Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void InitiallyCancelRide(RiderModel Rider){
        // Later, If needed
    }

    public void UpdateRiderLocation(final RiderModel Rider, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new UpdateRiderLocation(Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void FinalAcceptanceOfRide(final CurrentRidingHistoryModel HistoryModel, final RiderModel Rider, final ICallbackMain callBackListener){

        Thread thread = new Thread(){

            @Override
            public void run(){
                new FinalAcceptanceOfRide(HistoryModel, Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void InitialAcceptanceOfRide(final RiderModel Rider, final ICallbackMain callBackListener){

        /*
        * Get Client Model
        * Create History Model
        * Update Client Model
        * Update Rider Model
        */

        Thread thread = new Thread(){
            @Override
            public void run(){
                new InitialAcceptanceOfRide(Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void GetCurrentClient(final long ClientID, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new GetCurrentClient(ClientID, callBackListener);
            }
        };
        thread.start();
    }

    public void GetCurrentRider(final long RiderID, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new GetCurrentRider(RiderID, callBackListener);
            }
        };
        thread.start();
    }

    public void GetRecentHistory(final long HistoryID, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new GetRecentHistory(HistoryID, callBackListener);
            }
        };
        thread.start();
    }

    public void SetHistoryIDToClient(final CurrentRidingHistoryModel HistoryModel, final ClientModel Client, final long Time, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new SetHistoryIDToClient(HistoryModel, Client, Time, callBackListener);
            }
        };
        thread.start();
    }

    public void CancelRideByRider(/* Firebase HistoryModel, RiderModel */ final CurrentRidingHistoryModel HistoryModel, final RiderModel Rider, final ICallbackMain callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new CancelRideByRider(HistoryModel, Rider, callBackListener);
            }
        };
        thread.start();
    }
}
