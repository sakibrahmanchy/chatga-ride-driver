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

    @SerializedName("rider_id")
    public String riderId;

    @SerializedName("email")
    public String email;

    @SerializedName("phone")
    public String phone;

    @SerializedName("avatar")
    public String avatar;

    @SerializedName("nid")
    public String nid;

    @SerializedName("driving_license")
    public String drivingLicense;

    @SerializedName("motorbike_registration")
    public String motorbikeRegistration;

    @SerializedName("nid_image")
    public String nidImage;

    @SerializedName("driving_license_image")
    public String drivingLicenseImage;

    @SerializedName("motorbike_registration_image")
    public String motorbikeRegistrationImage;

    @SerializedName("is_verified")
    public int isVerified;

    @SerializedName("rating")
    public float rating;

    @SerializedName("referral_code")
    public String referralCode;

    public LoginData(String firstName, String lastName, String deviceToken,
                     String birthDate, String gender, String userId, String riderId,
                     String email, String phone, String avatar, String nid, String drivingLicense, String motorbikeRegistration,
                     String nidImage, String drivingLicenseImage, String motorbikeRegistrationImage, int isVerified, float rating,
                     String referralCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.deviceToken = deviceToken;
        this.birthDate = birthDate;
        this.gender = gender;
        this.userId = userId;
        this.riderId = riderId;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.nid = nid;
        this.drivingLicense = drivingLicense;
        this.motorbikeRegistration = motorbikeRegistration;
        this.nidImage = nidImage;
        this.drivingLicenseImage = drivingLicenseImage;
        this.motorbikeRegistrationImage = motorbikeRegistrationImage;
        this.isVerified = isVerified;
        this.rating = rating;
        this.referralCode = referralCode;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getMotorbikeRegistration() {
        return motorbikeRegistration;
    }

    public void setMotorbikeRegistration(String motorbikeRegistration) {
        this.motorbikeRegistration = motorbikeRegistration;
    }

    public String getNidImage() {
        return nidImage;
    }

    public void setNidImage(String nidImage) {
        this.nidImage = nidImage;
    }

    public String getDrivingLicenseImage() {
        return drivingLicenseImage;
    }

    public void setDrivingLicenseImage(String drivingLicenseImage) {
        this.drivingLicenseImage = drivingLicenseImage;
    }

    public String getMotorbikeRegistrationImage() {
        return motorbikeRegistrationImage;
    }

    public void setMotorbikeRegistrationImage(String motorbikeRegistrationImage) {
        this.motorbikeRegistrationImage = motorbikeRegistrationImage;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }
}
