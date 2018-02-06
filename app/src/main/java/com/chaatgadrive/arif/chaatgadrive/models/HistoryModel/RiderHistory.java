package com.chaatgadrive.arif.chaatgadrive.models.HistoryModel;


import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Arif on 1/5/2018.
 */

public class RiderHistory {

    public long HistoryID;
    public long  ClientID;
    public long  RiderID;
    public long DiscountID;
    public String Client_History;
    public String Rider_History;
    public long CostSoFar;
    public int IsRideStart;
    public int IsRideFinished;
    public int RideCanceledByClient;
    public int RideCanceledByRider;
    public LatLng StartLocation;
    public LatLng EndLocation;
    public String StartingLocationName;
    public String EndingLocationName;
}
