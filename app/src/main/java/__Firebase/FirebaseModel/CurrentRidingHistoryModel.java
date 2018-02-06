package __Firebase.FirebaseModel;

import android.util.Pair;

import com.google.firebase.database.DataSnapshot;

import __Firebase.FirebaseUtility.FirebaseConstant;

/**
 * Created by User on 11/16/2017.
 */

public class CurrentRidingHistoryModel {

    public static class Location{
        public double Latitude;
        public double Longitude;
        public String LocationName;

        public Location(){
        }

        public Location(double _Latitude, double _Longitude, String _LocationName){
            this.Latitude = _Latitude;
            this.Longitude = _Longitude;
            this.LocationName = _LocationName;
        }
    }

    public long HistoryID;
    public long  ClientID;
    public long  RiderID;
    public long DiscountID;
    public String Client_History;
    public String Rider_History;
    public Location StartingLocation;
    public Location EndingLocation;
    public long CostSoFar;
    public long IsRideStart;
    public long IsRideFinished;
    public long RideCanceledByClient;
    public long RideCanceledByRider;

    public CurrentRidingHistoryModel(){
    }

    public CurrentRidingHistoryModel(long _HistoryID, long _ClientID, long  _RiderID, long _DiscountID, String _Client_History, String _Rider_History, Pair<Double, Double> _StartingLocation, Pair<Double, Double> _EndingLocation, String _StartingLocationName, String _EndingLocationName, long _CostSoFar, long _IsRideStart, long _IsRideFinished, long _RideCanceledByClient, long _RideCanceledByRider){

        this.HistoryID = _HistoryID;
        this.ClientID = _ClientID;
        this.RiderID = _RiderID;
        this.DiscountID = _DiscountID;
        this.Client_History = _Client_History;
        this.Rider_History = _Rider_History;
        this.StartingLocation = new Location(_StartingLocation.first, _StartingLocation.second, _StartingLocationName);
        this.EndingLocation = new Location(_EndingLocation.first, _EndingLocation.second, _EndingLocationName);
        this.CostSoFar = _CostSoFar;
        this.IsRideStart = _IsRideStart;
        this.IsRideFinished = _IsRideFinished;
        this.RideCanceledByClient = _RideCanceledByClient;
        this.RideCanceledByRider = _RideCanceledByRider;
    }

    public void LoadData(DataSnapshot snapshot){

        CurrentRidingHistoryModel currentRidingHistoryModel = snapshot.getValue(CurrentRidingHistoryModel.class);

        this.HistoryID = currentRidingHistoryModel.HistoryID;
        this.ClientID = currentRidingHistoryModel.ClientID;
        this.RiderID = currentRidingHistoryModel.RiderID;
        this.DiscountID = currentRidingHistoryModel.DiscountID;
        this.Client_History = currentRidingHistoryModel.Client_History;
        this.Rider_History = currentRidingHistoryModel.Rider_History;
        this.StartingLocation = new Location(currentRidingHistoryModel.StartingLocation.Latitude, currentRidingHistoryModel.StartingLocation.Longitude, currentRidingHistoryModel.StartingLocation.LocationName);
        this.EndingLocation = new Location(currentRidingHistoryModel.EndingLocation.Latitude, currentRidingHistoryModel.EndingLocation.Longitude, currentRidingHistoryModel.EndingLocation.LocationName);
        this.CostSoFar = currentRidingHistoryModel.CostSoFar;
        this.IsRideStart = currentRidingHistoryModel.IsRideStart;
        this.IsRideFinished = currentRidingHistoryModel.IsRideFinished;
        this.RideCanceledByClient = currentRidingHistoryModel.RideCanceledByClient;
        this.RideCanceledByRider = currentRidingHistoryModel.RideCanceledByRider;
    }

    public void ClearData(){
        this.HistoryID = FirebaseConstant.UNKNOWN;
        this.ClientID = FirebaseConstant.UNKNOWN;
        this.RiderID = FirebaseConstant.UNKNOWN;
        this.DiscountID = FirebaseConstant.UNKNOWN;
        this.Client_History = FirebaseConstant.Empty;
        this.Rider_History = FirebaseConstant.Empty;
        this.StartingLocation.Latitude = FirebaseConstant.UNKNOWN;
        this.StartingLocation.Longitude = FirebaseConstant.UNKNOWN;
        this.StartingLocation.LocationName = FirebaseConstant.Empty;
        this.EndingLocation.Latitude = FirebaseConstant.UNKNOWN;
        this.EndingLocation.Longitude = FirebaseConstant.UNKNOWN;
        this.EndingLocation.LocationName = FirebaseConstant.Empty;
        this.CostSoFar = FirebaseConstant.UNKNOWN;
        this.IsRideStart = FirebaseConstant.UNKNOWN;
        this.IsRideFinished = FirebaseConstant.UNKNOWN;
        this.RideCanceledByClient = FirebaseConstant.UNKNOWN;
        this.RideCanceledByRider = FirebaseConstant.UNKNOWN;
    }
}
