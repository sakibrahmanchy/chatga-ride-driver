package com.chaatgadrive.arif.chaatgadrive;

import android.util.Log;

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

    @Override
    public void onTokenRefresh() {
        this.recentToken = FirebaseInstanceId.getInstance().getToken();
        new Main(this).SetDeviceTokenToRiderTable(
                FirebaseWrapper.getInstance().getRiderModelInstance(),
                recentToken
        );
        Log.d(FirebaseConstant.FIREBASE_TOKEN, recentToken);
    }

    public static String getRecentToken(){
        recentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(FirebaseConstant.FIREBASE_TOKEN, recentToken);
        return recentToken;
    }
}
