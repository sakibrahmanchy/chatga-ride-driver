package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RiderProfileStats;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 2/21/2018.
 */

public class RiderProfileStatsResponse {

    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private RiderProfileStats data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RiderProfileStats getData() {
        return data;
    }

    public void setData(RiderProfileStats data) {
        this.data = data;
    }
}
