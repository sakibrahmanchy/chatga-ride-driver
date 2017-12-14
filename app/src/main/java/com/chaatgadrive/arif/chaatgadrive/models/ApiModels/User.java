package com.chaatgadrive.arif.chaatgadrive.models.ApiModels;

/**
 * Created by Sakib Rahman on 11/18/2017.
 */

import com.google.gson.annotations.SerializedName;


public class User {
    @SerializedName("id")
    private String userId;



    public User(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}