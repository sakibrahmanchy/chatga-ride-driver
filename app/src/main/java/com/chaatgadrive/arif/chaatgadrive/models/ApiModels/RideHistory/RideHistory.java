package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 1/10/2018.
 */

public class RideHistory {

    @SerializedName("client_id")
    private int clientId;
    @SerializedName("rider_id")
    private int riderId;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("pick_point_lat")
    private String pickPointLat;
    @SerializedName("pick_point_lon")
    private String pickPoinLon;
    @SerializedName("destination_point_lat")
    private String destinationPointLat;
    @SerializedName("destination_point_lon")
    private String destinationPointLon;
    @SerializedName("initial_approx_cost")
    private String initialApproxCost;


    public RideHistory(int clientId, int riderId, String startTime, String endTime, String pickPointLat, String pickPoinLon, String destinationPointLat, String destinationPointLon, String initialApproxCost) {

        this.clientId = clientId;
        this.riderId = riderId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pickPointLat = pickPointLat;
        this.pickPoinLon = pickPoinLon;
        this.destinationPointLat = destinationPointLat;
        this.destinationPointLon = destinationPointLon;
        this.initialApproxCost = initialApproxCost;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getRiderId() {
        return riderId;
    }

    public void setRiderId(int riderId) {
        this.riderId = riderId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPickPointLat() {
        return pickPointLat;
    }

    public void setPickPointLat(String pickPointLat) {
        this.pickPointLat = pickPointLat;
    }

    public String getPickPoinLon() {
        return pickPoinLon;
    }

    public void setPickPoinLon(String pickPoinLon) {
        this.pickPoinLon = pickPoinLon;
    }

    public String getDestinationPointLat() {
        return destinationPointLat;
    }

    public void setDestinationPointLat(String destinationPointLat) {
        this.destinationPointLat = destinationPointLat;
    }

    public String getDestinationPointLon() {
        return destinationPointLon;
    }

    public void setDestinationPointLon(String destinationPointLon) {
        this.destinationPointLon = destinationPointLon;
    }

    public String getInitialApproxCost() {
        return initialApproxCost;
    }

    public void setInitialApproxCost(String initialApproxCost) {
        this.initialApproxCost = initialApproxCost;
    }
}
