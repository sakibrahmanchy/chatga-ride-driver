package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.DeviceTokenModels;

import com.google.gson.annotations.SerializedName;

public class UpdateDeviceTokenData {

    @SerializedName("success")
    private boolean status;

    @SerializedName("message")
    private String message;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}