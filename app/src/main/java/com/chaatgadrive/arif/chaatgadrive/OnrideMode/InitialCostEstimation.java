package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.chaatgadrive.arif.chaatgadrive.CostEstimation.CostEstimation;
import com.chaatgadrive.arif.chaatgadrive.LoginHelper;
import com.chaatgadrive.arif.chaatgadrive.PhoneVerificationActivity;
import com.chaatgadrive.arif.chaatgadrive.UserCheckActivity;
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

public class InitialCostEstimation {


    private ApiInterface apiService ;
    private Context mContext;
    private ProgressDialog dialog;
    private NotificationModel notificationModel;
    private SimpleDateFormat dateFormatter;
    private SharedPreferences pref;
    private CostEstimation costEstimation;
    private RiderHistory riderHistory;
    private Main main;


    public InitialCostEstimation(Context context) {

        apiService =   ApiClient.getClient().create(ApiInterface.class);
        this.mContext=context;
        dialog = new ProgressDialog(mContext);
        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        pref = this.mContext.getSharedPreferences("MyPref", 0);
        costEstimation = new CostEstimation();
        riderHistory = new RiderHistory();
        main = new Main(context);


    }

    public void CreateInitialHistory(){


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        apiService = ApiClient.getClient().create(ApiInterface.class);

        dialog = new ProgressDialog(mContext);
        dialog.setMessage("Please Wait..");
        dialog.show();

        String authHeader = "Bearer "+pref.getString("access_token",null);
        Call<RideHistoryResponse> call = apiService.createRideHistory(authHeader,(int)notificationModel.clientId,(int)notificationModel.riderId,currentDateandTime,"5",
                String.valueOf(notificationModel.sourceLatitude), String.valueOf(notificationModel.sourceLatitude),
                String.valueOf(notificationModel.destinationLatitude), String.valueOf(notificationModel.destinationLongitude),150+"");

        call.enqueue(new Callback<RideHistoryResponse>() {
            @Override
            public void onResponse(Call<RideHistoryResponse> call, Response<RideHistoryResponse> response) {

                int statusCode = response.code();
                dialog.dismiss();
                switch(statusCode){
                    case 200:

                        if(response.body().isSuccess()){
                            RideHistory history = response.body().getHistory();

                            LatLng Source = new LatLng(notificationModel.sourceLatitude,notificationModel.sourceLongitude);
                            LatLng Destination = new LatLng(notificationModel.destinationLatitude,notificationModel.destinationLongitude);
                            riderHistory.ClientID = notificationModel.clientId;
                            riderHistory.CostSoFar = (long) costEstimation.getTotalCost(notificationModel.shortestDistance,notificationModel.shortestTime);
                            riderHistory.HistoryID=history.getClientId();
                            riderHistory.RiderID = notificationModel.riderId;
                            riderHistory.StartLocation =Source;
                            riderHistory.EndLocation=Destination;
                            riderHistory.Client_History = notificationModel.destinationName;
                            riderHistory.Rider_History=notificationModel.destinationName;
                            riderHistory.IsRideFinished= FirebaseConstant.RIDE_NOT_FINISHED;
                            riderHistory.IsRideStart=FirebaseConstant.RIDE_NOT_START;

                            main.CreateNewHistoryModelFirebase(riderHistory);
                        }
                        break;
                    case 500:
                        break;

                    default:

                        break;
                }
            }

            @Override
            public void onFailure(Call<RideHistoryResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }


}
