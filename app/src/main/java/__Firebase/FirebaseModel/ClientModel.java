package __Firebase.FirebaseModel;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DataSnapshot;

import __Firebase.FirebaseUtility.FirebaseConstant;

/**
 * Created by User on 11/16/2017.
 */

public class ClientModel {

    public long ClientID;
    public long PhoneNumber;
    public long CostOfCurrentRide;
    public String DeviceToken;
    public String FullName;
    public int IsSearchingOrOnRide;
    public long CurrentRidingHistoryID;

    public ClientModel(){

    }

    public ClientModel(long _ClientID, long _PhoneNumber, String _DeviceToken, String _FullName, long _CostOfCurrentRide, int _IsSearchingOrOnRide, int _CurrentRidingHistoryID){
        this.ClientID = _ClientID;
        this.PhoneNumber = _PhoneNumber;
        this.DeviceToken = _DeviceToken;
        this.FullName = _FullName;
        this.CostOfCurrentRide = _CostOfCurrentRide;
        this.IsSearchingOrOnRide = _IsSearchingOrOnRide;
        this.CurrentRidingHistoryID = _CurrentRidingHistoryID;
    }

    public void LoadData(DataSnapshot snapshot){
        try{
            ClientModel clientModel = snapshot.getValue(ClientModel.class);
            this.ClientID = clientModel.ClientID;
            this.PhoneNumber = clientModel.PhoneNumber;
            this.DeviceToken = clientModel.DeviceToken;
            this.FullName = clientModel.FullName;
            this.CostOfCurrentRide = clientModel.CostOfCurrentRide;
            this.IsSearchingOrOnRide = clientModel.IsSearchingOrOnRide;
            this.CurrentRidingHistoryID = clientModel.CurrentRidingHistoryID;
        }
        catch (Exception e){
            Log.d(FirebaseConstant.CLIENT_LOADED_ERROR, e.getStackTrace().toString());
        }
    }
}
