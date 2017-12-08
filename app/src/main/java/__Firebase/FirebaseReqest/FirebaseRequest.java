package __Firebase.FirebaseReqest;

import __Firebase.Callbacklisteners.CallBackListener;
import __Firebase.Callbacklisteners.ICallbackMain;
import __Firebase.FirebaseModel.ClientModel;
import __Firebase.FirebaseModel.CurrentRidingHistoryModel;
import __Firebase.FirebaseModel.RiderModel;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseRequest {

    private boolean IsRiderAlreadyCreated = false;
    private CallBackListener callBackListener = null;

    public FirebaseRequest(){
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

    public void SetRiderBusyOrFree(final RiderModel Rider, final int value, final CallBackListener callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new SetRiderBusyOrFree(Rider, value, callBackListener);
            }
        };
        thread.start();
    }

    public void SetRiderOnRideOrFree(final RiderModel Rider, final int value, final CallBackListener callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new SetRiderOnRideOrFree(Rider, value, callBackListener);
            }
        };
        thread.start();
    }

    public void SetRiderOnLineOrOffLine(final RiderModel Rider, final int value, final CallBackListener callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new SetRiderOnLineOrOffLine(Rider, value, callBackListener);
            }
        };
        thread.start();
    }

    public void SetRiderOnlineBusyOnRider(final RiderModel Rider, final int value, final CallBackListener callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new SetRiderOnlineBusyOnRider(Rider, value, callBackListener);
            }
        };
        thread.start();
    }

    public void SetHistoryIDonRiderTable(final CurrentRidingHistoryModel HistoryModel, final RiderModel Rider, final CallBackListener callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new SetHistoryIDonRiderTable(HistoryModel, Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void CreateNewRideHistory(final CurrentRidingHistoryModel HistoryModel, final CallBackListener callBackListener){

        Thread thread = new Thread(){

            @Override
            public void run(){
                new CreateNewRideHistory(HistoryModel, callBackListener);
            }
        };
        thread.start();
    }

    public void FinishedRide(final CurrentRidingHistoryModel HistoryModel, final RiderModel Rider, final CallBackListener callBackListener){

        Thread thread = new Thread(){

            @Override
            public void run(){
                new FinishedRide(HistoryModel, Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void ResetRiderStatus(final RiderModel Rider, final CallBackListener callBackListener){

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

    public void UpdateRiderLocation(final RiderModel Rider, final CallBackListener callBackListener){

        Thread thread = new Thread(){

            @Override
            public void run(){
                new UpdateRiderLocation(Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void FinalAcceptanceOfRide(final CurrentRidingHistoryModel HistoryModel, final RiderModel Rider, final CallBackListener callBackListener){

        Thread thread = new Thread(){

            @Override
            public void run(){
                new FinalAcceptanceOfRide(HistoryModel, Rider, callBackListener);
            }
        };
        thread.start();
    }

    public void InitialAcceptanceOfRide(final CurrentRidingHistoryModel HistoryModel, final RiderModel Rider, final long ClientID, final CallBackListener callBackListener){

        /*
        * Get Client Model
        * Create History Model
        * Update Client Model
        * Update Rider Model
        */

        Thread thread = new Thread(){
            @Override
            public void run(){
                new InitialAcceptanceOfRide(HistoryModel, Rider, ClientID, callBackListener);
            }
        };
        thread.start();
    }

    public void GetCurrentClient(final long ClientID, final CallBackListener callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new GetCurrentClient(ClientID, callBackListener);
            }
        };
        thread.start();
    }

    public void SetHistoryIDToClient(final CurrentRidingHistoryModel HistoryModel, final ClientModel Client, final CallBackListener callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new SetHistoryIDToClient(HistoryModel, Client, callBackListener);
            }
        };
        thread.start();
    }
}
