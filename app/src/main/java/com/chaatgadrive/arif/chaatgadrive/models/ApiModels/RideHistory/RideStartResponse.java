package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 1/25/2018.
 */

public class RideStartResponse {

    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private int data;

    public RideStartResponse(boolean success, String message, int data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
