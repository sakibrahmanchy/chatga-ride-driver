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
}
