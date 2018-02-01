package com.chaatgadrive.arif.chaatgadrive.Rating;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.Rating.RateClient;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.Rating.Rating;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.RideHistory.RideStartResponse;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Arif on 1/29/2018.
 */

public class UserRating {

    private Context mContext;
    private SharedPreferences pref;
    private ApiInterface apiService ;
    public UserRating(Context context) {
        this.mContext = context;
        apiService =   ApiClient.getClient().create(ApiInterface.class);
        pref = this.mContext.getSharedPreferences("MyPref", 0);
    }

    public void RatingClient(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        String authHeader = "Bearer "+pref.getString("access_token",null);
        Call<RateClient> call = apiService.rateClient(authHeader, AppConstant.CURRENT_HISTORY_ID,AppConstant.RATING);

        call.enqueue(new Callback<RateClient>() {
            @Override
            public void onResponse(Call<RateClient> call, Response<RateClient> response) {

                int statusCode = response.code();

                switch(statusCode){
                    case 200:

                        if(response.body().isSuccess()){
                         double data = response.body().getData();
                        }
                        break;
                    case 500:

                        break;

                    default:

                        break;
                }
            }

            @Override
            public void onFailure(Call<RateClient> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
