package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideFinishModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 1/16/2018.
 */

public class RideFinishData {
    @SerializedName("history_id")
    private int historyId;
    @SerializedName("cost_before_discount")
    private double costBeforeDiscount;
    @SerializedName("cost_after_discount")
    private double costAfterDiscount;

    public RideFinishData(int historyId, double costBeforeDiscount, double costAfterDiscount) {
        this.historyId = historyId;
        this.costBeforeDiscount = costBeforeDiscount;
        this.costAfterDiscount = costAfterDiscount;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public double getCostBeforeDiscount() {
        return costBeforeDiscount;
    }

    public void setCostBeforeDiscount(double costBeforeDiscount) {
        this.costBeforeDiscount = costBeforeDiscount;
    }

    public double getCostAfterDiscount() {
        return costAfterDiscount;
    }

    public void setCostAfterDiscount(double costAfterDiscount) {
        this.costAfterDiscount = costAfterDiscount;
    }
}
