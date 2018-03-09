package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RiderEarnings;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 3/9/2018.
 */

public class EarningByDay {

    @SerializedName("Fri")
    private String friDay;
    @SerializedName("Sat")
    private String satDay;
    @SerializedName("Sun")
    private String sunDay;
    @SerializedName("Mon")
    private String monDay;
    @SerializedName("Tue")
    private String tueDay;
    @SerializedName("Wed")
    private String wedDay;
    @SerializedName("Thu")
    private String thuDay;

    public EarningByDay(String friDay, String satDay, String sunDay, String monDay, String tueDay, String wedDay, String thuDay) {
        this.friDay = friDay;
        this.satDay = satDay;
        this.sunDay = sunDay;
        this.monDay = monDay;
        this.tueDay = tueDay;
        this.wedDay = wedDay;
        this.thuDay = thuDay;
    }

    public String getFriDay() {
        return friDay;
    }

    public void setFriDay(String friDay) {
        this.friDay = friDay;
    }

    public String getSatDay() {
        return satDay;
    }

    public void setSatDay(String satDay) {
        this.satDay = satDay;
    }

    public String getSunDay() {
        return sunDay;
    }

    public void setSunDay(String sunDay) {
        this.sunDay = sunDay;
    }

    public String getMonDay() {
        return monDay;
    }

    public void setMonDay(String monDay) {
        this.monDay = monDay;
    }

    public String getTueDay() {
        return tueDay;
    }

    public void setTueDay(String tueDay) {
        this.tueDay = tueDay;
    }

    public String getWedDay() {
        return wedDay;
    }

    public void setWedDay(String wedDay) {
        this.wedDay = wedDay;
    }

    public String getThuDay() {
        return thuDay;
    }

    public void setThuDay(String thuDay) {
        this.thuDay = thuDay;
    }
}
