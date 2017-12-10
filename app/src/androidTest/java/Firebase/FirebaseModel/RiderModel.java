package Firebase.FirebaseModel;

import android.content.Context;
import android.util.Pair;

import com.google.firebase.database.DataSnapshot;

import java.util.Random;

/**
 * Created by User on 11/16/2017.
 */

public class RiderModel {

    public static class RiderLocation{
        public double Latitude;
        public double Longitude;
        public int RequestForUpdateLocation;

        public RiderLocation(){
        }

        public RiderLocation(double _Latitude, double _Longitude, int _RequestForUpdateLocation){
            this.Latitude = _Latitude;
            this.Longitude = _Longitude;
            this.RequestForUpdateLocation = _RequestForUpdateLocation;
        }
    }

    public long RiderID;
    public long PhoneNumber;
    public String DeviceToken;
    public String FullName;
    public int IsRiderOnline;
    public int IsRiderBusy;
    public int IsRiderOnRide;
    public long CurrentRidingHistoryID;
    public int OnlineBusyOnRide;
    public RiderLocation CurrentRiderLocation;
    public long DistanceFromClient;

    public RiderModel(){
        CurrentRiderLocation = new RiderLocation();
    }

    public RiderModel(long _RiderID, long _PhoneNumber, String _DeviceToken, String _FullName, int _IsRiderOnline, int _IsRiderBusy, int _IsRiderOnRide, long _CurrentRidingHistoryID, int _OnlineBusyOnRide, RiderLocation _RiderLocation){
        this.RiderID = _RiderID;
        this.PhoneNumber = _PhoneNumber;
        this.DeviceToken = _DeviceToken;
        this.FullName = _FullName;
        this.IsRiderOnline = _IsRiderOnline;
        this.IsRiderBusy = _IsRiderBusy;
        this.IsRiderOnRide = _IsRiderOnRide;
        this.CurrentRidingHistoryID = _CurrentRidingHistoryID;
        this.OnlineBusyOnRide = _OnlineBusyOnRide;
        this.CurrentRiderLocation = _RiderLocation;
    }

    public void LoadData(DataSnapshot snapshot){

        RiderModel riderModel = snapshot.getValue(RiderModel.class);
        this.RiderID = riderModel.RiderID;
        this.PhoneNumber = riderModel.PhoneNumber;
        this.DeviceToken = riderModel.DeviceToken;
        this.FullName = riderModel.FullName;
        this.IsRiderOnline = riderModel.IsRiderOnline;
        this.IsRiderBusy = riderModel.IsRiderBusy;
        this.IsRiderOnRide = riderModel.IsRiderOnRide;
        this.CurrentRidingHistoryID = riderModel.CurrentRidingHistoryID;
        this.OnlineBusyOnRide = riderModel.OnlineBusyOnRide;
        this.CurrentRiderLocation = riderModel.CurrentRiderLocation;

        this.DistanceFromClient = this.DistanceBetweenTwoPoint(null, null);
    }

    long DistanceBetweenTwoPoint(Pair<Double, Double> source, Pair<Double, Double> destination){
        Random rm = new Random();
        return rm.nextInt(100);
    }
}
