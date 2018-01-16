package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideFinishModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 1/16/2018.
 */

public class RideFinishResponse {

    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private RideFinishData data;

    public RideFinishResponse(boolean success, String message, RideFinishData data) {
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

    public RideFinishData getData() {
        return data;
    }

    public void setData(RideFinishData data) {
        this.data = data;
    }
}
