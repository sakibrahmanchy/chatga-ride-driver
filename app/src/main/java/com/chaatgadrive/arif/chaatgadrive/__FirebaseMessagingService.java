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

import __Firebase.FirebaseModel.ClientModel;
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
        ClientModel clientModel = FirebaseWrapper.getInstance().getClientModelInstance();

        if (remoteMessage.getData().size() > 0) {

            notificationModel.title = remoteMessage.getData().containsKey("title") ? remoteMessage.getData().get("title") : FirebaseConstant.Empty;
            notificationModel.body = remoteMessage.getData().containsKey("body") ? remoteMessage.getData().get("body") : FirebaseConstant.Empty;
            notificationModel.clientId = Long.parseLong(remoteMessage.getData().containsKey("clientId") ? remoteMessage.getData().get("clientId") : "0");
            notificationModel.clientName = remoteMessage.getData().containsKey("clientName") ? remoteMessage.getData().get("clientName") : FirebaseConstant.Empty;
            notificationModel.clientPhone = remoteMessage.getData().containsKey("clientPhone") ? remoteMessage.getData().get("clientPhone") : FirebaseConstant.Empty;
            notificationModel.clientDeviceToken = remoteMessage.getData().containsKey("clientDeviceToken") ? remoteMessage.getData().get("clientDeviceToken") : FirebaseConstant.Empty;
            notificationModel.riderId = Long.parseLong(remoteMessage.getData().containsKey("riderId") ? remoteMessage.getData().get("riderId") : "0");
            notificationModel.sourceName = remoteMessage.getData().containsKey("sourceName") ? remoteMessage.getData().get("sourceName") : FirebaseConstant.Empty;
            notificationModel.destinationName = remoteMessage.getData().containsKey("destinationName") ? remoteMessage.getData().get("destinationName") : FirebaseConstant.Empty;
            notificationModel.shortestTime = remoteMessage.getData().containsKey("shortestTime") ? remoteMessage.getData().get("shortestTime") : FirebaseConstant.Empty;
            notificationModel.shortestDistance = remoteMessage.getData().containsKey("shortestDistance") ? remoteMessage.getData().get("shortestDistance") : FirebaseConstant.Empty;
            notificationModel.sourceLatitude = Double.parseDouble(remoteMessage.getData().containsKey("sourceLatitude") ? remoteMessage.getData().get("sourceLatitude") : "0");
            notificationModel.sourceLongitude = Double.parseDouble(remoteMessage.getData().containsKey("sourceLongitude") ? remoteMessage.getData().get("sourceLongitude") : "0");
            notificationModel.destinationLatitude = Double.parseDouble(remoteMessage.getData().containsKey("destinationLatitude") ? remoteMessage.getData().get("destinationLatitude") : "0");
            notificationModel.destinationLongitude = Double.parseDouble(remoteMessage.getData().containsKey("destinationLongitude") ? remoteMessage.getData().get("destinationLongitude") : "0");

            clientModel.ClientID = notificationModel.clientId;
            clientModel.FullName = notificationModel.clientName;
            clientModel.PhoneNumber = Long.parseLong(notificationModel.clientPhone);
            clientModel.DeviceToken = notificationModel.clientDeviceToken;
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
