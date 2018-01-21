package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.CostEstimation.CostEstimation;
import com.chaatgadrive.arif.chaatgadrive.Dailog.RideFinishDailog;
import com.chaatgadrive.arif.chaatgadrive.Dailog.RiderDailog;
import com.chaatgadrive.arif.chaatgadrive.LoginHelper;
import com.chaatgadrive.arif.chaatgadrive.MainActivity;
import com.chaatgadrive.arif.chaatgadrive.PhoneVerificationActivity;
import com.chaatgadrive.arif.chaatgadrive.UserCheckActivity;
import com.chaatgadrive.arif.chaatgadrive.chaatgamap.GetCurrentLocation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideFinishModel.RideFinishData;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideFinishModel.RideFinishResponse;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory.RideHistory;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory.RideHistoryResponse;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.UserCheckResponse;
import com.chaatgadrive.arif.chaatgadrive.models.HistoryModel.RiderHistory;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ContactWithFirebase.Main;
import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Arif on 1/10/2018.
 */

public class InitialAndFinalCostEstimation {

    private ApiInterface apiService ;
    private Context mContext;
    private ProgressDialog dialog;
    private NotificationModel notificationModel;
    private SimpleDateFormat dateFormatter;
    private SharedPreferences pref;
    private CostEstimation costEstimation;
    private RiderHistory riderHistory;
    private GetDistanceFromMap getDistanceFromMap;
    private Main main;

    public InitialAndFinalCostEstimation(Context context) {

        apiService =   ApiClient.getClient().create(ApiInterface.class);
        this.mContext=context;
        dialog = new ProgressDialog(mContext);
        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        pref = this.mContext.getSharedPreferences("MyPref", 0);
        costEstimation = new CostEstimation();
        riderHistory = new RiderHistory();
        getDistanceFromMap = new GetDistanceFromMap();
        main = new Main(context);

    }

    public void CreateInitialHistory(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        String TotalDuration = String.valueOf(costEstimation.getDuration(AppConstant.DURATION));
        apiService = ApiClient.getClient().create(ApiInterface.class);
        dialog = new ProgressDialog(mContext);
        dialog.setMessage("Please Wait..");
        dialog.show();
        double Currentdistance= getDistanceFromMap.getDistance(new LatLng(notificationModel.sourceLatitude,notificationModel.sourceLongitude),
                new LatLng(notificationModel.destinationLatitude,notificationModel.destinationLongitude));

        Currentdistance = Currentdistance/1000.0;
        final String TotalCost = String.valueOf(costEstimation.TotalCost((int)Currentdistance,20));
        String authHeader = "Bearer "+pref.getString("access_token",null);
        Call<RideHistoryResponse> call = apiService.createRideHistory(authHeader,(int)notificationModel.clientId,(int)notificationModel.riderId,currentDateandTime,
                TotalDuration,
                String.valueOf(notificationModel.sourceLatitude), String.valueOf(notificationModel.destinationLongitude),
                String.valueOf(notificationModel.destinationLatitude), String.valueOf(notificationModel.destinationLongitude),
                notificationModel.sourceName,notificationModel.destinationName,
                TotalCost);

        call.enqueue(new Callback<RideHistoryResponse>() {
            @Override
            public void onResponse(Call<RideHistoryResponse> call, Response<RideHistoryResponse> response) {

                int statusCode = response.code();
                dialog.dismiss();
                switch(statusCode){
                    case 200:

                        if(response.body().isSuccess()){
                            RideHistory history = response.body().getHistory();
                            LatLng Source = new LatLng(notificationModel.sourceLatitude, notificationModel.sourceLongitude);
                            LatLng Destination = new LatLng(notificationModel.destinationLatitude, notificationModel.destinationLongitude);
                            riderHistory.ClientID = notificationModel.clientId;
                            riderHistory.CostSoFar = (long)Double.parseDouble(TotalCost);
                            AppConstant.CURRENT_HISTORY_ID= (int) (riderHistory.HistoryID = history.getHistoryId());
                            riderHistory.RiderID = notificationModel.riderId;
                            riderHistory.StartLocation = Source;
                            riderHistory.EndLocation = Destination;
                            riderHistory.Client_History = Long.toString(notificationModel.clientId) + FirebaseConstant.UNDER_SCORE + Long.toString(riderHistory.HistoryID);
                            riderHistory.Rider_History = Long.toString(notificationModel.riderId) + FirebaseConstant.UNDER_SCORE + Long.toString(riderHistory.HistoryID);
                            riderHistory.IsRideFinished = FirebaseConstant.RIDE_NOT_FINISHED;
                            riderHistory.IsRideStart = FirebaseConstant.RIDE_NOT_START;
                            main.CreateNewHistoryModelFirebase(riderHistory);
                        }
                        break;
                    case 500:

                        Log.d("Onride",response.errorBody().toString());
                        break;

                    default:

                        break;
                }
            }

            @Override
            public void onFailure(Call<RideHistoryResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }


    public void UpdateFinalHistory(int HistoryId,double durationInMinutes, double distance, int discountId, String pickPointAddress, String destinationAddress){

        apiService = ApiClient.getClient().create(ApiInterface.class);
        dialog = new ProgressDialog(mContext);
        dialog.setMessage("Please Wait..");
        dialog.show();
        String authHeader = "Bearer "+pref.getString("access_token",null);
        Call<RideFinishResponse> call = apiService.createRideFinishHistory(authHeader,HistoryId,durationInMinutes,distance,
                discountId,pickPointAddress, destinationAddress);

        call.enqueue(new Callback<RideFinishResponse>() {
            @Override
            public void onResponse(Call<RideFinishResponse> call, Response<RideFinishResponse> response) {

                int statusCode = response.code();
                dialog.dismiss();
                switch(statusCode){
                    case 200:
                        if(response.body().isSuccess()){

                            RideFinishData rideFinishData = response.body().getData();
                            AppConstant.TOTAL_RIDING_COST = (int)rideFinishData.getCostAfterDiscount();
                            RideFinishDailog rideFinishDailog = new RideFinishDailog((Activity) mContext);
                            rideFinishDailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            rideFinishDailog.show();
                            /*
                            Intent intent = new Intent(mContext, MainActivity.class);
                            mContext.startActivity(intent);
                            ((Activity)mContext).finish();
                            */
                        }
                        break;
                    case 500:

                        Log.d("Onride",response.errorBody().toString());
                        break;

                    default:

                        break;
                }
            }

            @Override
            public void onFailure(Call<RideFinishResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }



}
