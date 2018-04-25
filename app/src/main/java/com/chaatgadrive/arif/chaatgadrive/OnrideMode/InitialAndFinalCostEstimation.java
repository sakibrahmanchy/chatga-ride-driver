package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.CostEstimation.CostEstimation;
import com.chaatgadrive.arif.chaatgadrive.FinishRideActivity.FinishRideActivity;
import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideFinishModel.RideFinishData;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideFinishModel.RideFinishResponse;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory.RideHistory;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory.RideHistoryResponse;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory.RideStartResponse;
import com.chaatgadrive.arif.chaatgadrive.models.HistoryModel.RiderHistory;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;
import com.google.android.gms.maps.model.LatLng;

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
import static com.chaatgadrive.arif.chaatgadrive.OnrideMode.OnRideModeActivity.Onridecontext;

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
    private RideFinishData rideFinishData;
    private SetNotificationWhenRideStart setNotificationWhenRideStart;
    private UserInformation userInformation;
    private Button finishRide;
    private Button startRide;
    private LinearLayout AccepAndReject;
    private FrameLayout StartAndFinish;
    private NestedScrollView bootmsheet;

    public InitialAndFinalCostEstimation(Context context) {

        apiService =   ApiClient.getClient().create(ApiInterface.class);
        this.mContext=context;
        dialog = new ProgressDialog(mContext);
        userInformation = new UserInformation(mContext);
        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        pref = this.mContext.getSharedPreferences("MyPref", 0);
        costEstimation = new CostEstimation();
        riderHistory = new RiderHistory();
        setNotificationWhenRideStart = new SetNotificationWhenRideStart(mContext);
        getDistanceFromMap = new GetDistanceFromMap();
        finishRide =(Button) ((Activity)context).findViewById(R.id.finishbtn);
        startRide =(Button) ((Activity)context).findViewById(R.id.startBtn);
        AccepAndReject =(LinearLayout)((Activity)context).findViewById(R.id.accep_reject_card_layout);
        bootmsheet =(NestedScrollView ) ((Activity)context).findViewById(R.id.bottom_sheet);
        StartAndFinish = (FrameLayout) ((Activity)context).findViewById(R.id.start_and_finish_layout);
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
        final String TotalCost = String.valueOf(costEstimation.TotalCost(20,(int)Currentdistance));
        String authHeader = "Bearer "+pref.getString("access_token",null);
        Call<RideHistoryResponse> call = apiService.createRideHistory(authHeader,(int)notificationModel.clientId,(int)notificationModel.riderId,currentDateandTime,
                TotalDuration,
                String.valueOf(notificationModel.sourceLatitude), String.valueOf(notificationModel.destinationLongitude),
                String.valueOf(notificationModel.destinationLatitude), String.valueOf(notificationModel.destinationLongitude),
                notificationModel.sourceName,notificationModel.destinationName,
                String.valueOf(notificationModel.totalCost),"0");

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
                            AppConstant.CURRENT_HISTORY_ID = (int) (riderHistory.HistoryID = history.getHistoryId());
                            riderHistory.RiderID = notificationModel.riderId;
                            riderHistory.DiscountID = notificationModel.discountID;
                            riderHistory.StartLocation = Source;
                            riderHistory.EndLocation = Destination;
                            riderHistory.Client_History = Long.toString(notificationModel.clientId) + FirebaseConstant.UNDER_SCORE + Long.toString(riderHistory.HistoryID);
                            riderHistory.Rider_History = Long.toString(notificationModel.riderId) + FirebaseConstant.UNDER_SCORE + Long.toString(riderHistory.HistoryID);
                            riderHistory.IsRideFinished = FirebaseConstant.RIDE_NOT_FINISHED;
                            riderHistory.IsRideStart = FirebaseConstant.RIDE_NOT_START;
                            riderHistory.StartingLocationName = notificationModel.sourceName;
                            riderHistory.EndingLocationName = notificationModel.destinationName;
                            main.CreateNewHistoryModelFirebase(riderHistory);
                            SendPushNotification();
                            AppConstant.SHOW_ACTIVITY_FOR_ACCEPT_AND_REJECT=false;
                            AccepAndReject.setVisibility(View.GONE);
                            StartAndFinish.setVisibility(View.VISIBLE);
                            bootmsheet.setVisibility(View.VISIBLE);
                            finishRide.setVisibility(View.INVISIBLE);
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
                Log.e(TAG, t.toString());
            }
        });
    }

    public void UpdateStartRide(int HistoryId){

        apiService = ApiClient.getClient().create(ApiInterface.class);
        dialog = new ProgressDialog(mContext);
        dialog.setMessage("Please Wait..");
        dialog.show();
        String authHeader = "Bearer "+pref.getString("access_token",null);
        Call<RideStartResponse> call = apiService.StartRide(authHeader, HistoryId);

        call.enqueue(new Callback<RideStartResponse>() {
            @Override
            public void onResponse(Call<RideStartResponse> call, Response<RideStartResponse> response) {

                int statusCode = response.code();
                dialog.dismiss();
                switch(statusCode){
                    case 200:


                        if(response.body().isSuccess()){
                            setNotificationWhenRideStart.Notification();
                            main.ForcedAcceptanceOfRide(FirebaseConstant.FINAL_ACCEPTANCE);
                            startRide.setVisibility(View.GONE);
                            finishRide.setVisibility(View.VISIBLE);
                            AppConstant.IS_RIDE=1;
//                            Intent intent = new Intent(Onridecontext,DistanceCalculationService.class);
//                            Onridecontext.startService(intent);
                        }
                        break;
                    case 500:

                        break;

                    default:

                        break;
                }
            }

            @Override
            public void onFailure(Call<RideStartResponse> call, Throwable t) {
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
                discountId,pickPointAddress, destinationAddress,
                String.valueOf(AppConstant.PREVIOUS_LATLONG.latitude), String.valueOf(AppConstant.PREVIOUS_LATLONG.longitude));

        call.enqueue(new Callback<RideFinishResponse>() {
            @Override
            public void onResponse(Call<RideFinishResponse> call, Response<RideFinishResponse> response) {
                int statusCode = response.code();
                dialog.dismiss();
                switch(statusCode){
                    case 200:
                        if(response.body().isSuccess()){
                            rideFinishData = response.body().getData();
//                            Intent intent = new Intent(Onridecontext,DistanceCalculationService.class);
//                            Onridecontext.stopService(intent);
                            final ProgressDialog progressDialog = new ProgressDialog(mContext);
                            progressDialog.setMessage("Finishing ride, please wait...");
                            progressDialog.show();

                            // start the time consuming task in a new thread
                            Thread thread = new Thread() {

                                public void run () {

                                    // this is the time consuming task (like, network call, database call)
                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.getLooper().prepare();
                                    ForceFinishedRide();


                                    // Now we are on a different thread than UI thread
                                    // and we would like to update our UI, as this task is completed

                                    handler.post(new Runnable() {
                                        @Override public void run() {

                                            // Update your UI or do any Post job after the time consuming task
                                            // remember to dismiss the progress dialog on UI thread
                                            AppConstant.TOTAL_RIDING_COST = (int)rideFinishData.getCostAfterDiscount();
                                            Intent Finishintent = new Intent(mContext, FinishRideActivity.class);
                                            mContext.startActivity(Finishintent);
                                            Onridecontext.finish();
                                            AppConstant.IS_RIDE=0;
                                            AppConstant.IS_RIDE_FINISH = true;
                                            progressDialog.dismiss();
                                        }
                                    });

                                }
                            };
                            thread.start();
                        }
                        break;
                    case 500:

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
    private void ForceFinishedRide(){
        Pair<Double, Double> finalDestination = Pair.create(AppConstant.PREVIOUS_LATLONG.latitude, AppConstant.PREVIOUS_LATLONG.longitude);
        long finalCost = (long)rideFinishData.getCostAfterDiscount();
        main.ForcedFinishedRide(finalCost, finalDestination);
    }

    private void SendPushNotification() {
        new Main(Onridecontext).ForcedAcceptanceOfRide(FirebaseConstant.INITIAL_ACCEPTANCE);
    }
}
