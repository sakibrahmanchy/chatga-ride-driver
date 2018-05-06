package com.chaatgadrive.arif.chaatgadrive.FirstAppLoadingActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.chaatgadrive.arif.chaatgadrive.ActiveContext;
import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.FacebookAccountVerificationActivity;
import com.chaatgadrive.arif.chaatgadrive.LoginHelper;
import com.chaatgadrive.arif.chaatgadrive.MainActivity;
import com.chaatgadrive.arif.chaatgadrive.OnLocationChange.UpdateLocationService;
import com.chaatgadrive.arif.chaatgadrive.OnrideMode.OnRideModeActivity;
import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginModel;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiClient;
import com.chaatgadrive.arif.chaatgadrive.rest.ApiInterface;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import ContactWithFirebase.Main;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstAppLoadingActivity extends AppCompatActivity {

    private UserInformation userInformation;
    private LoginData loginData;
    private Handler handler = new Handler();
    private Main main;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private ProgressDialog dialog;
    private ApiInterface apiService ;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private String TAG;
    private LocationRequest locationRequest;
    private LoginHelper loginHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_first_app_loading);
        userInformation = new UserInformation(this);
        TAG = getApplicationContext().getApplicationInfo().className;
        loginData = userInformation.getuserInformation();
        loginHelper = new LoginHelper(this);
        new ActiveContext(this);
        main = new Main(this);

        if (loginData != null) {
            requestForSpecificPermission();

        }
        else {
            int MyVersion = Build.VERSION.SDK_INT;
            if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {


                if (!checkIfAlreadyhavePermission()) {
                    requestForSpecificPermission();
                }
                else{
                    Intent intent = new Intent(FirstAppLoadingActivity.this, FacebookAccountVerificationActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            else{
                Intent intent = new Intent(FirstAppLoadingActivity .this, FacebookAccountVerificationActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    void InitializeApp() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (AppConstant.OnOffSwith != 2 && AppConstant.IS_RIDE != 2) {

                    if (AppConstant.IS_RIDE == 0) {
                        main.UpdateNameImageAndRatting(loginData.getFirstName()+" "+loginData.getLastName(),loginData.getAvatar(),loginData.getRating()+"");
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
                        editor.apply();
                        loginHelper.updateDeviceToken(loginData.getPhone());
                        main = new Main(FirstAppLoadingActivity.this);
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

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.READ_SMS,
                android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.CALL_PHONE}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED &&
                        grantResults[2]==PackageManager.PERMISSION_GRANTED) {
                    if(loginData==null){
                        Intent intent = new Intent(FirstAppLoadingActivity.this, FacebookAccountVerificationActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        if(getLocationMode()==0 || getLocationMode()==2 ){
                            Toast.makeText(getApplicationContext(),"Location on with Higher Acuracy Mode",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                        else{
                            Intent intent = new Intent(FirstAppLoadingActivity.this, UpdateLocationService.class);
//{                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    startForegroundService(intent);
//                }
//                else
                            startService(intent);
                            //   }
                            GetRiderAllInformations((loginData.getRiderId()));
                        }

                    }
                } else {
                    finish();
                    Toast.makeText(getApplicationContext(),"Please Restart Application",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public int getLocationMode()
    {
        try {
           return Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -1;
        }

    }

}
