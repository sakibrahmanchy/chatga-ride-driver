package com.chaatgadrive.arif.chaatgadrive.CurrentDateTimeFromServer;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;


import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.DateTimeModel.DateTimeResponse;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * Created by Arif on 1/21/2018.
 */

public class CurrentDateTime {

    private ProgressDialog dialog;
    private ApiInterface apiService ;
    private  Context mContext;
    private Long time;
    public CurrentDateTime(Context context) {
        this.mContext =context;
    }

    public  long getCurrentDateTime(){
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        dialog = new ProgressDialog(mContext);
        dialog.setMessage("Please Wait..");
        dialog.show();

        Call<DateTimeResponse> call = apiService.getDateTime();

        call.enqueue(new Callback<DateTimeResponse>() {
            @Override
            public void onResponse(Call<DateTimeResponse> call, Response<DateTimeResponse> response) {

                int statusCode = response.code();
                dialog.dismiss();
                switch(statusCode){
                    case 200:

                        break;
                    case 500:

                        break;

                    default:
                         time= System.currentTimeMillis();
                        break;
                }
            }

            @Override
            public void onFailure(Call<DateTimeResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("DateTime", t.toString());
            }
        });
        return System.currentTimeMillis();
    }
}
