package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RiderEarnings;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 3/9/2018.
 */

public class RiderEarnings {
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private EarningData data;

    public RiderEarnings(boolean success, String message, EarningData data) {
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

    public EarningData getData() {
        return data;
    }

    public void setData(EarningData data) {
        this.data = data;
    }
}
