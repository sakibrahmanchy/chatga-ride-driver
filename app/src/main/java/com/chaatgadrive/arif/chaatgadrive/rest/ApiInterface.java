package com.chaatgadrive.arif.chaatgadrive.rest;

/**
 * Created by Sakib Rahman on 11/18/2017.
 */

import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.AccessTokenModels.AuthToken;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.BasicEmptyResponse;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.DateTimeModel.DateTimeResponse;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.DeviceTokenModels.UpdateDeviceTokenData;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginModel;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.NotificationModels.NotificationResponse;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.Rating.RateClient;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.Rating.Rating;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RegistrationModels.RegistrationModel;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideFinishModel.RideFinishResponse;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory.RideHistoryResponse;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory.RideStartResponse;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory.RiderHistoryResponse;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RiderEarnings.RiderEarnings;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RiderProfileStats.RiderProfileStatsResponse;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.User;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.UserCheckResponse;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @POST("api/v1/driver/login")
    @FormUrlEncoded
    Call<LoginModel> loginUser(@Header("Authorization") String authHeader,
                               @Field("phone_number") String phoneNumber,
                               @Field("device_token") String deviceToken);

    @POST("api/v1/driver/device_token")
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
                                         @Field("motorbike_registration") String motorbikeRegistration);

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
                                                @Field("destination_address") String destinationAddress,
                                                @Field("ride_cost") String initialApproxCost,
                                                @Field("ride_distance") String initialApproxDistance);


    @POST("api/v1/ride/start")
    @FormUrlEncoded
    Call<RideStartResponse> StartRide(@Header("Authorization") String authHeader,
                                      @Field("history_id") int historyId);

    @POST("api/v1/ride/finish")
    @FormUrlEncoded
    Call<RideFinishResponse> createRideFinishHistory(@Header("Authorization") String authHeader,
                                                     @Field("history_id") int historyId,
                                                     @Field("duration_in_minutes") double durationInMinutes,
                                                     @Field("distance") double distance,
                                                     @Field("discount_id") int discountId,
                                                     @Field("pick_point_address") String pickPointAddress,
                                                     @Field("destination_address") String destinationAddress,
                                                     @Field("destination_lat") String destinationLat,
                                                     @Field("destination_lon") String destinationLon);

    @GET("api/v1/date_time")
    Call<DateTimeResponse> getDateTime();

    @POST("api/v1/rate/client")
    @FormUrlEncoded
    Call<RateClient>rateClient(@Header("Authorization") String authHeader,
                              @Field("history_id") int historyId,
                              @Field("rating") double ratingPoint);

    @GET("api/v1/rider/history")
    Call<RiderHistoryResponse>getRiderHistory(@Header("Authorization") String authHeader,
                                               @Query("rider_id") String rider_id);

    @GET("api/v1/rider/profile_stats")
    Call<RiderProfileStatsResponse>getRiderProfileStats(@Header("Authorization") String authHeader,
                                                        @Query("rider_id") int riderId);

    @POST("api/v1/rider/rating")
    @FormUrlEncoded
    Call<Rating>getRiderRating(@Header("Authorization") String authHeader,
                               @Field("rider_id") int riderId);



    @POST("api/v1/client/rating")
    @FormUrlEncoded
    Call<Rating>getClientRating(@Header("Authorization") String authHeader,
                                @Field("client_id") int clientId);

    @GET("api/v1/rider/notifications")
    Call<NotificationResponse> getRiderNotifications(@Header("Authorization") String authHeader);

    @POST("api/v1/rider/update_profile")
    @Multipart
    Call<LoginModel> updateRiderProfile(@Header("Authorization") String authHeader,
                                         @Part("rider_id") RequestBody client_id,
                                         @Part("first_name") RequestBody first_name,
                                         @Part("last_name") RequestBody last_name,
                                         @Part("gender") RequestBody gender,
                                         @Part("email") RequestBody email,
                                         @Part MultipartBody.Part avatar,
                                         @Part("phone") RequestBody phone);

    @POST("api/v1/rider/update_documents")
    @Multipart
    Call<LoginModel> updateRiderDocuments(@Header("Authorization") String authHeader,
                                                  @Part("rider_id") RequestBody rider_id,
                                                  @Part MultipartBody.Part nid,
                                                  @Part MultipartBody.Part motorbikeRegistration,
                                                  @Part MultipartBody.Part drivingLicense);

    @GET("api/v1/rider/all_informations")
    Call<LoginModel> getRiderAllInformations(@Header("Authorization") String authHeader,
                                             @Query("rider_id") String riderId);

    @GET("api/v1/rider/earnings")
    Call<RiderEarnings> getRiderEarnings(@Header("Authorization") String authHeader,
                                         @Query("rider_id") String rider_id);

}