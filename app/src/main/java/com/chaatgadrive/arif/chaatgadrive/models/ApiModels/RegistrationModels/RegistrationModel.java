package com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RegistrationModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arif on 12/3/2017.
 */

public class RegistrationModel {

    public String getSuccess() {
        return success;
    }

    @SerializedName("success")
    private String success;

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("device_token")
    private String deviceToken;
    @SerializedName("gender")
    private String gender;

    @SerializedName("birthDate")
    private String birthDate;

    public RegistrationModel(String success,String firstName, String lastName, String email, String phoneNumber, String deviceToken, String gender, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.deviceToken = deviceToken;
        this.gender = gender;
        this.birthDate = birthDate;
        this.success=success;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthDate() {
        return birthDate;
    }


}
