package com.chaatgadrive.arif.chaatgadrive.models.ApiModels;


/**
 * Created by Sakib Rahman on 11/18/2017.
 */

        import com.google.gson.annotations.SerializedName;

public class UserCheckResponse {
    @SerializedName("success")
    private String success;

    @SerializedName("response_code")
    private String responseCode;

    public UserCheckResponse(String success, String responseCode) {
        this.success = success;
        this.responseCode = responseCode;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}

