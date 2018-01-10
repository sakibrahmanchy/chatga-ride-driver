package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by SakibRahman on 1/10/2018.
 */

public class RideHistoryResponse {

    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private RideHistory history;

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

    public RideHistory getHistory() {
        return history;
    }

    public void setHistory(RideHistory history) {
        this.history = history;
    }
}
