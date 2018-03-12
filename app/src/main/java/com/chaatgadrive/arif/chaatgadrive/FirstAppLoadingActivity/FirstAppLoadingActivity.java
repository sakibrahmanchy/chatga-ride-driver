package com.chaatgadrive.arif.chaatgadrive.FirstAppLoadingActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.MainActivity;
import com.chaatgadrive.arif.chaatgadrive.OnLocationChange.UpdateLocationService;
import com.chaatgadrive.arif.chaatgadrive.OnrideMode.OnRideModeActivity;
import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginModel;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import ContactWithFirebase.Main;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstAppLoadingActivity extends AppCompatActivity {

    private UserInformation userInformation;
    private LoginData loginData;
    private Handler handler = new Handler();
    private Main main =new Main(this);
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private ProgressDialog dialog;
    private ApiInterface apiService ;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_app_loading);
        userInformation = new UserInformation(this);
        TAG = getApplicationContext().getApplicationInfo().className;
        loginData = userInformation.getuserInformation();
        if (loginData != null) {
            GetRiderAllInformations((loginData.getRiderId()));
        }
        else {
            Intent intent = new Intent(FirstAppLoadingActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    void InitializeApp() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (AppConstant.OnOffSwith != 2 && AppConstant.IS_RIDE != 2) {

                    if (AppConstant.IS_RIDE == 0) {
                        Intent intent = new Intent(FirstAppLoadingActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    if (AppConstant.IS_RIDE == 1) {

                        AppConstant.PREVIOUS_LATLONG = new LatLng(AppConstant.currentRidingHistoryModel.StartingLocation.Latitude
                                , AppConstant.currentRidingHistoryModel.StartingLocation.Longitude);
                        AppConstant.CLIENT_NAME = AppConstant.ClientModel.FullName;
                        AppConstant.PHONE_NUMBER = AppConstant.ClientModel.PhoneNumber;
                        Intent intent1 = new Intent(FirstAppLoadingActivity.this, UpdateLocationService.class);
                        startService(intent1);
                        Intent intent = new Intent(FirstAppLoadingActivity.this, OnRideModeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    main.UpdateNameImageAndRatting(loginData.getFirstName()+" "+loginData.getLastName(),loginData.getAvatar(),loginData.getRating()+"");
                } else {
                    handler.postDelayed(this, 1000);
                }

            }
        };
        handler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }
    }

    public void GetRiderAllInformations(String rider_id){

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String authHeader = "Bearer "+pref.getString("access_token",null);

        Call<LoginModel> call = apiService.getRiderAllInformations(authHeader,rider_id);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                int statusCode = response.code();
                switch(statusCode){
                    case 200:
                        LoginData newLoginData = response.body().getLoginData();
                        Gson gson = new Gson();
                        String json = gson.toJson(newLoginData);
                        editor.putString("userData",json);
                        editor.commit();

                        main.GetRiderStatus(Long.parseLong(userInformation.getuserInformation().getRiderId()));
                        InitializeApp();
                        break;
                    default:
                        Snackbar.make(findViewById(android.R.id.content), "Sorry, network error.",
                                Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

}
