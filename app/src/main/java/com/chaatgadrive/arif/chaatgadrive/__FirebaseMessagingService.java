package com.chaatgadrive.arif.chaatgadrive;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import __Firebase.FirebaseResponse.NotificationModel;
import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.FirebaseWrapper;

/**
 * Created by User on 12/2/2017.
 */

public class __FirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        NotificationModel notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();

        if (remoteMessage.getData().size() > 0) {
            notificationModel.title = remoteMessage.getData().get("title");
            notificationModel.body = remoteMessage.getData().get("body");
            notificationModel.clientId = Long.parseLong(remoteMessage.getData().get("clientId"));
            notificationModel.sourceLatitude = Double.parseDouble(remoteMessage.getData().get("sourceLatitude"));
            notificationModel.sourceLongitude = Double.parseDouble(remoteMessage.getData().get("sourceLongitude"));
            notificationModel.destinationLatitude = Double.parseDouble(remoteMessage.getData().get("destinationLatitude"));
            notificationModel.destinationLongitude = Double.parseDouble(remoteMessage.getData().get("destinationLongitude"));
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(FirebaseConstant.RIDE_NOTIFICATION, FirebaseConstant.RIDE_NOTIFICATION);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(sound);
        builder.setContentTitle(notificationModel.title);
        builder.setContentText(notificationModel.body);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

        Log.d(FirebaseConstant.NOTIFICATION_MESSAGE, FirebaseConstant.NOTIFICATION_MESSAGE);
    }

    @Override
    public void onDeletedMessages() {

    }
}
