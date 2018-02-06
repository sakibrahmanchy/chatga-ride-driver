package com.chaatgadrive.arif.chaatgadrive.OnrideMode;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.chaatgadrive.arif.chaatgadrive.FirstAppLoadingActivity.FirstAppLoadingActivity;
import com.chaatgadrive.arif.chaatgadrive.MainActivity;
import com.chaatgadrive.arif.chaatgadrive.R;
import com.google.android.gms.maps.model.LatLng;

import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseWrapper;

import static android.content.Context.NOTIFICATION_SERVICE;



/**
 * Created by Arif on 1/13/2018.
 */

public class SetNotificationWhenRideStart {


    private NotificationCompat.Builder notification;
    private Notification note;
    private NotificationManager notificationManager;
    private Context mContext;
    private NotificationModel notificationModel;
    public SetNotificationWhenRideStart(Context context) {
        this.mContext = context;
        notification = new NotificationCompat.Builder(mContext);
        notification.setAutoCancel(false);
        notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
    }

    void Notification(){
        notification.setSmallIcon(R.drawable.logo);
        notification.setTicker("this Chaadga Ride");
        notification.setContentTitle("You are in Ride");
        notification.setOnlyAlertOnce(true);
        notification.setContentText(AppConstant.SOURCE_NAME + "  To "+AppConstant.DESTINATION_NAME);
        notification.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
        notification.setAutoCancel(true);
        Intent intent = new Intent(mContext,FirstAppLoadingActivity.class);
        PendingIntent pendingIntent =PendingIntent.getActivity(mContext,0,intent,0);
        notification.setContentIntent(pendingIntent);
        note = notification.build();
        notificationManager.notify(AppConstant.NOTIFICATION_ID,note);

    }
}
