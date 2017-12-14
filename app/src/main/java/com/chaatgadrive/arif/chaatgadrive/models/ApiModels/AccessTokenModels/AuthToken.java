package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.AccessTokenModels;

/**
 * Created by Sakib Rahman on 11/18/2017.
 */

import android.content.Context;

import com.google.gson.annotations.SerializedName;


public class AuthToken {


    @SerializedName("status")
    private String status;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("message")
    private String message;


    public AuthToken(Context context, String accessToken, String status, String message) {

        this.accessToken = accessToken;
        this.status = status;
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}


