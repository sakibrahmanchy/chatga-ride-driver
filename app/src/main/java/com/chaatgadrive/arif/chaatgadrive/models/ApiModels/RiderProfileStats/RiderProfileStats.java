package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RiderProfileStats;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 2/21/2018.
 */

public class RiderProfileStats {

    @SerializedName("trips")
    private String trips;
    @SerializedName("earnings")
    private String earnings;
    @SerializedName("rating")
    private String rating;

    public String getTrips() {
        return trips;
    }

    public void setTrips(String trips) {
        this.trips = trips;
    }

    public String getEarnings() {
        return earnings;
    }

    public void setEarnings(String earnings) {
        this.earnings = earnings;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
