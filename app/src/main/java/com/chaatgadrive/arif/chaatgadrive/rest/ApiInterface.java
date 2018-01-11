package com.chaatgadrive.arif.chaatgadrive.rest;

/**
 * Created by Sakib Rahman on 11/18/2017.
 */

import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.AccessTokenModels.AuthToken;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.DeviceTokenModels.UpdateDeviceTokenData;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginModel;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RegistrationModels.RegistrationModel;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory.RideHistoryResponse;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.User;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.UserCheckResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("chaatga_rider/api/v1/users/")
    Call<ArrayList<User>> getAllUsers(@Header("Authorization") String authHeader, @Header("Accept") String authType);

    @GET("api/v1/driver_exists")
    Call<UserCheckResponse> checkUser(@Query("phoneNumber") String phoneNumber);


    @GET("access_token")
    Call<AuthToken> getAccessToken(@Query("phone_number") String phoneNumber,
                                   @Query("client_id") String clientId,
                                   @Query("client_secret") String clientSecret);

    @POST("api/v1/login")
    @FormUrlEncoded
    Call<LoginModel> loginUser(@Header("Authorization") String authHeader,
                               @Field("phone_number") String phoneNumber,
                               @Field("device_token") String deviceToken);

    @POST("api/v1/user/device_token")
    @FormUrlEncoded
    Call<UpdateDeviceTokenData> updateDeviceToken(@Header("Authorization") String authHeader,
                                                  @Field("phone_number") String phoneNumber,
                                                  @Field("device_token") String deviceToken);

    @POST("api/v1/rider")
    @FormUrlEncoded
    Call<RegistrationModel> signUpRider(@Field("first_name") String firstName,
                                         @Field("last_name") String lastName,
                                         @Field("email") String email,
                                         @Field("phone_number") String phoneNumber,
                                         @Field("device_token") String deviceToken,
                                         @Field("birth_date") String birthDate,
                                         @Field("gender") String gender,
                                         @Field("nid") String nid,
                                         @Field("driving_license") String drivingLicense,
                                         @Field("destination_address") String motorbikeRegistration);

    @POST("api/v1/ride/history")
    @FormUrlEncoded
    Call<RideHistoryResponse> createRideHistory(@Header("Authorization") String authHeader,
                                                @Field("client_id") int clientId,
                                                @Field("rider_id") int riderId,
                                                @Field("start_time") String startTime,
                                                @Field("end_time") String endTime,
                                                @Field("pick_point_latitude") String pickPointLat,
                                                @Field("pick_point_longitude") String pickPointLon,
                                                @Field("destination_point_latitude") String destinationPointLat,
                                                @Field("destination_point_longitude") String destinationPointLon,
                                                @Field("pick_point_address") String pickPointAddress,
                                                @Field("destination_point_longitude") String destinationAddress,
                                                @Field("initial_approx_cost") String initialApproxCost);


}