package com.chaatgadrive.arif.chaatgadrive.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;


import com.chaatgadrive.arif.chaatgadrive.models.ApiModels.LoginModels.LoginData;
import com.google.gson.Gson;

import __Firebase.FirebaseResponse.NotificationModel;

/**
 * Created by Arif on 12/16/2017.
 */

public class UserInformation {

    private Gson gson;
    private SharedPreferences sharedpreferences;
    private LoginData loginData;
    private NotificationModel notificationModel;
    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences.Editor editor;

    public UserInformation(Context context) {
        gson = new Gson();
        sharedpreferences = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
         editor = sharedpreferences.edit();
    }

   public LoginData getuserInformation() {

        String jsonString = sharedpreferences.getString("userData", null);
        loginData = gson.fromJson(jsonString, LoginData.class);
        return loginData;
    }

    public String getRiderPhoneNumber(){
        String phoneNumber = sharedpreferences.getString("phoneNumber", null);
        return phoneNumber;
    }

    public NotificationModel getuserNotification() {

        String jsonString = sharedpreferences.getString("notificationModel", null);
        notificationModel = gson.fromJson(jsonString, NotificationModel.class);
        return notificationModel;
    }
    public void RemoveNotification(){
            editor.remove("notificationModel");
            editor.commit();
    }
}
