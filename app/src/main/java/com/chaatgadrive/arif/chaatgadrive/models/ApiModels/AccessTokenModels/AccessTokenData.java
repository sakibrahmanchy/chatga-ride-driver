package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.AccessTokenModels;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sakib Rahman on 12/8/2017.
 */

public class AccessTokenData {

    @SerializedName("accessToken")
    public String accessToken;

    public AccessTokenData(String accessToken, Context context) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
