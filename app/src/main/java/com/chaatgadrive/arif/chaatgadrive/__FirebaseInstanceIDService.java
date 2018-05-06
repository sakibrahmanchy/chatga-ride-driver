package com.chaatgadrive.arif.chaatgadrive;

import android.content.SharedPreferences;
import android.util.Log;

import com.chaatgadrive.arif.chaatgadrive.SharedPreferences.UserInformation;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import ContactWithFirebase.Main;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 12/2/2017.
 */

public class __FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static String recentToken = null;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    public void onTokenRefresh() {
        UserInformation userInformation = new UserInformation(this);
        pref = getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        this.recentToken = FirebaseInstanceId.getInstance().getToken();
        editor.putString("DeviceToken",recentToken);
        editor.apply();

        if(userInformation.getuserInformation() !=null){

            new Main(this).SetDeviceTokenToRiderTable(
                    FirebaseWrapper.getInstance().getRiderModelInstance(),
                    recentToken
            );
            Log.d(FirebaseConstant.FIREBASE_TOKEN, recentToken);
        }


    }

    public static String getRecentToken(){
        recentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(FirebaseConstant.FIREBASE_TOKEN, recentToken);
        return recentToken;
    }
}
