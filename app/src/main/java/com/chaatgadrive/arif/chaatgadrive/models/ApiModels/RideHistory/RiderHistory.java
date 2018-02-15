package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 1/21/2018.
 */

public class RiderHistory {

    @SerializedName("date_time")
    private String dateTime;
    @SerializedName("distance_time")
    private String distanceTime;
    @SerializedName("pick_point_address")
    private String pickPointAddress;
    @SerializedName("destination_address")
    private String destinationAddress;
    @SerializedName("total_fare")
    private String totalFare;
    @SerializedName("rider_id")
    private int riderId;
    @SerializedName("client_name")
    private String clientName;
    @SerializedName("client_avatar")
    private String riderAvatar;
    @SerializedName("promotion")
    private String promotion;

    public RiderHistory(String dateTime, String distanceTime, String pickPointAddress, String destinationAddress, String totalFare, int riderId,
                        String riderName, String riderAvatar, String promotion) {
        this.dateTime = dateTime;
        this.distanceTime = distanceTime;
        this.pickPointAddress = pickPointAddress;
        this.destinationAddress = destinationAddress;
        this.totalFare = totalFare;
        this.riderId = riderId;
        this.clientName = riderName;
        this.riderAvatar = riderAvatar;
        this.promotion = promotion;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDistanceTime() {
        return distanceTime;
    }

    public void setDistanceTime(String distanceTime) {
        this.distanceTime = distanceTime;
    }

    public String getPickPointAddress() {
        return pickPointAddress;
    }

    public void setPickPointAddress(String pickPointAddress) {
        this.pickPointAddress = pickPointAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }

    public int getRiderId() {
        return riderId;
    }

    public void setRiderId(int riderId) {
        this.riderId = riderId;
    }

    public String getRiderName() {
        return clientName;
    }

    public void setRiderName(String riderName) {
        this.clientName = riderName;
    }

    public String getRiderAvatar() {
        return riderAvatar;
    }

    public void setRiderAvatar(String riderAvatar) {
        this.riderAvatar = riderAvatar;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }
}
