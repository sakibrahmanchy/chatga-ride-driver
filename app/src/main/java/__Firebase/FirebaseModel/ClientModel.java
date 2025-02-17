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
    public String CurrentRidingHistoryID;
    public String ImageUrl;
    public String Ratting;

    public ClientModel(){

    }

    public ClientModel(long _ClientID, long _PhoneNumber, String _DeviceToken, String _FullName, long _CostOfCurrentRide, int _IsSearchingOrOnRide, String _CurrentRidingHistoryID, String _ImageUrl, String _Ratting){
        this.ClientID = _ClientID;
        this.PhoneNumber = _PhoneNumber;
        this.DeviceToken = _DeviceToken;
        this.FullName = _FullName;
        this.CostOfCurrentRide = _CostOfCurrentRide;
        this.IsSearchingOrOnRide = _IsSearchingOrOnRide;
        this.CurrentRidingHistoryID = _CurrentRidingHistoryID;
        this.ImageUrl = _ImageUrl;
        this.Ratting = _Ratting;
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
            this.ImageUrl = clientModel.ImageUrl;
            this.Ratting = clientModel.Ratting;
        }
        catch (Exception e){
            Log.d(FirebaseConstant.CLIENT_LOADED_ERROR, e.getStackTrace().toString());
        }
    }

    public void ClearData(){
        this.ClientID = FirebaseConstant.UNKNOWN;
        this.PhoneNumber = FirebaseConstant.UNKNOWN;
        this.DeviceToken = FirebaseConstant.Empty;
        this.FullName = FirebaseConstant.Empty;
        this.CostOfCurrentRide = FirebaseConstant.UNKNOWN;
        this.IsSearchingOrOnRide = FirebaseConstant.UNKNOWN;
        this.CurrentRidingHistoryID = FirebaseConstant.Empty;
        this.ImageUrl = FirebaseConstant.Empty;
        this.Ratting = FirebaseConstant.Empty;
    }
}
