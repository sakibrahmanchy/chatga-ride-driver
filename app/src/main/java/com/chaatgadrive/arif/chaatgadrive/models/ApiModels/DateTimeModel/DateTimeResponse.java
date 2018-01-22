package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.DateTimeModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 1/21/2018.
 */

public class DateTimeResponse {

    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private long data;

    public DateTimeResponse(boolean success, String message, long data) {
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

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }
}
