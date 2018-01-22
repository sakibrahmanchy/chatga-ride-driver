package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sakib Rahman on 12/12/2017.
 */

public class LoginData {

    @SerializedName("first_name")
    public String firstName;

    @SerializedName("last_name")
    public String lastName;

    @SerializedName("device_token")
    public String deviceToken;

    @SerializedName("birth_date")
    public String birthDate;

    @SerializedName("gender")
    public String gender;

    @SerializedName("user_id")
    public String userId;

    @SerializedName("id")
    public String riderId;

    public LoginData(String firstName, String lastName, String deviceToken, String birthDate, String gender, String userId, String riderId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.deviceToken = deviceToken;
        this.birthDate = birthDate;
        this.gender = gender;
        this.userId = userId;
        this.riderId = riderId;
    }

    public LoginData(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }
}
