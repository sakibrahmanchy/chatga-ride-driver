package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.chaatgadrive.arif.chaatgadrive.LoginHelper;
import com.chaatgadrive.arif.chaatgadrive.PhoneVerificationActivity;
import com.chaatgadrive.arif.chaatgadrive.UserCheckActivity;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory.RideHistoryResponse;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.UserCheckResponse;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import __Firebase.FirebaseResponse.NotificationModel;
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

    public InitialCostEstimation(Context context) {

        apiService =   ApiClient.getClient().create(ApiInterface.class);
        this.mContext=context;
        dialog = new ProgressDialog(mContext);
        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();

    }

    public void CreateInitialHistory(){


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        dialog = new ProgressDialog(mContext);
        dialog.setMessage("Please Wait..");
        dialog.show();

        Call<RideHistoryResponse> call = apiService.createRideHistory((int)notificationModel.clientId,1,currentDateandTime,"5",
                notificationModel.sourceLatitude+"",notificationModel.sourceLatitude+"",
                notificationModel.destinationLatitude+"",notificationModel.destinationLongitude+"",150+"");

        call.enqueue(new Callback<RideHistoryResponse>() {
            @Override
            public void onResponse(Call<RideHistoryResponse> call, Response<RideHistoryResponse> response) {

                int statusCode = response.code();
                dialog.dismiss();
                switch(statusCode){
                    case 200:
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
