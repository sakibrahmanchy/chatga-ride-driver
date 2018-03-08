package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RiderEarnings;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 3/9/2018.
 */

public class EarningData {
    @SerializedName("earning_by_day")
    EarningByDay earningByDay;
    @SerializedName("trips_completed")
    private String tripsCompleted;
    @SerializedName("ride_requests")
    private String rideRequests;
    @SerializedName("earnings")
    private String earnings;
    @SerializedName("total_due")
    private String totalDue;
    @SerializedName("total_paid")
    private String totalPaid;
    @SerializedName("completion_rate")
    private String completionRate;

    public EarningData(EarningByDay earningByDay, String tripsCompleted, String rideRequests, String earnings, String totalDue, String totalPaid, String completionRate) {
        this.earningByDay = earningByDay;
        this.tripsCompleted = tripsCompleted;
        this.rideRequests = rideRequests;
        this.earnings = earnings;
        this.totalDue = totalDue;
        this.totalPaid = totalPaid;
        this.completionRate = completionRate;
    }

    public EarningByDay getEarningByDay() {
        return earningByDay;
    }

    public void setEarningByDay(EarningByDay earningByDay) {
        this.earningByDay = earningByDay;
    }

    public String getTripsCompleted() {
        return tripsCompleted;
    }

    public void setTripsCompleted(String tripsCompleted) {
        this.tripsCompleted = tripsCompleted;
    }

    public String getRideRequests() {
        return rideRequests;
    }

    public void setRideRequests(String rideRequests) {
        this.rideRequests = rideRequests;
    }

    public String getEarnings() {
        return earnings;
    }

    public void setEarnings(String earnings) {
        this.earnings = earnings;
    }

    public String getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(String totalDue) {
        this.totalDue = totalDue;
    }

    public String getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(String completionRate) {
        this.completionRate = completionRate;
    }
}
