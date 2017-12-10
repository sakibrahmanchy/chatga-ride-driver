package Firebase.FirebaseReqest;

import android.telecom.Call;
import android.util.Log;
import android.util.Pair;

import com.example.user.rider.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import Firebase.Callbacklisteners.CallBackListener;
import Firebase.FirebaseModel.ClientModel;
import Firebase.FirebaseModel.CurrentRidingHistoryModel;
import Firebase.FirebaseModel.RiderModel;
import Firebase.FirebaseUtility.FirebaseConstant;
import Firebase.FirebaseWrapper;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseRequest {

    private boolean IsRiderAlreadyCreated = false;
    private CallBackListener callBackListener = null;

    public FirebaseRequest(){
    }

    public void CreateRiderFirstTime(final RiderModel Rider, final CallBackListener callBackListener){

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
