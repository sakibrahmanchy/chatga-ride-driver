package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.Rating;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 1/29/2018.
 */

public class Rating {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private double data;

    public Rating(boolean success, String message, double data) {
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

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }
}
