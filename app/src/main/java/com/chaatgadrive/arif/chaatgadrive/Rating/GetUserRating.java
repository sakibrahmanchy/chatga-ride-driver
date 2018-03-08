package com.chaatgadrive.arif.chaatgadrive.Rating;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.Rating.Rating;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;

import ContactWithFirebase.Main;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Arif on 3/8/2018.
 */

public class GetUserRating {
    private Context mContext;
    private SharedPreferences pref;
    private ApiInterface apiService ;
    private UserInformation userInformation;
    private Main main;
    public GetUserRating(Context context) {
        this.mContext = context;
        apiService =   ApiClient.getClient().create(ApiInterface.class);
        pref = this.mContext.getSharedPreferences("MyPref", 0);
        userInformation = new UserInformation(mContext);
        main =new Main(mContext);
    }

    public void GetRatingForRider(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        String authHeader = "Bearer "+pref.getString("access_token",null);
        Call<Rating> call = apiService.getRiderRating(authHeader,Integer.parseInt(userInformation.getuserInformation().getRiderId()));

        call.enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(Call<Rating> call, Response<Rating> response) {

                int statusCode = response.code();

                switch(statusCode){
                    case 200:

                        if(response.body().isSuccess()){
                            double data = response.body().getData();
                            main.UpdateNameImageAndRatting(null,null,data+"");

                        }
                        break;
                    case 500:

                        break;

                    default:

                        break;
                }
            }

            @Override
            public void onFailure(Call<Rating> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
