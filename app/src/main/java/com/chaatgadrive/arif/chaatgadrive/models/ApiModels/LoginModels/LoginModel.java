package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arif on 12/3/2017.
 */

public class LoginModel {



    @SerializedName("success")
    private String success;

    @SerializedName("response_code")
    private String responseCode;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private LoginData loginData;

    public LoginModel(String success, String responseCode, String message, LoginData loginData) {
        this.success = success;
        this.responseCode = responseCode;
        this.message = message;
        this.loginData = loginData;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }
}
