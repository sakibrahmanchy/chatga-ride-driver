package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.NotificationModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SakibRahman on 2/7/2018.
 */

public class Notification {
    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("start_time")
    public String startTime;

    public Notification(String title, String description, String startTime) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
