package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory;

import com.google.gson.annotations.SerializedName;

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

}
