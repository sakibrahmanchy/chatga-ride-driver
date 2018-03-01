package com.chaatgadrive.arif.chaatgadrive.FirstAppLoadingActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.MainActivity;
import com.chaatgadrive.arif.chaatgadrive.OnLocationChange.UpdateLocationService;
import com.chaatgadrive.arif.chaatgadrive.OnrideMode.OnRideModeActivity;
import com.chaatgadrive.arif.chaatgadrive.R;
import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.chaatgadrive.arif.chaatgadrive.UserCheckActivity;
import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.google.android.gms.maps.model.LatLng;

import ContactWithFirebase.Main;

public class FirstAppLoadingActivity extends AppCompatActivity {

    private UserInformation userInformation;
    private LoginData loginData;
    private Handler handler = new Handler();
    private Main main =new Main(this);
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_app_loading);
        userInformation = new UserInformation(this);
        loginData = userInformation.getuserInformation();
        if (loginData != null) {
            main.GetRiderStatus(Long.parseLong(userInformation.getuserInformation().getRiderId()));
            InitializeApp();
        } else {
            getLocationPermission();
            Intent intent = new Intent(FirstAppLoadingActivity.this, MainActivity.class);
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

    private void getLocationPermission() {
        Log.d("", "getLocationPermission: getting location permissions");
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            } else {
                Intent intent = new Intent(this, UserCheckActivity.class);
                startActivity(intent);
                ActivityCompat.requestPermissions(this,
                        permissions,LOCATION_PERMISSION_REQUEST_CODE);

            }
        } else {

            Intent intent = new Intent(this, UserCheckActivity.class);
            startActivity(intent);
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
}
