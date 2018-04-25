package com.chaatgadrive.arif.chaatgadrive;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;

import java.io.IOException;

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

        if (remoteMessage.getData().containsKey("typeId")) {
            if (remoteMessage.getData().get("typeId").equals("1")) {

                Intent intent = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                String CHANNEL_ID = "my_channel_01";// The id of the channel.
                CharSequence name = "Arif";// The user-visible name of the channel.
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                }

                Notification builder =
                        new NotificationCompat.Builder(this)
                .setSound(sound)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("body"))
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setChannelId(CHANNEL_ID).build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationManager.createNotificationChannel(mChannel);
                }
                notificationManager.notify(1, builder);
            }
        } else if (remoteMessage.getData().containsKey(AppConstant.ACTION_TYPE)) {

            int action = Integer.parseInt(remoteMessage.getData().get(AppConstant.ACTION_TYPE));
            switch (action) {
                case AppConstant.RIDE_CANCEL_BY_CLIENT_NOTF: {
                    this.RIDE_CANCEL_BY_CLIENT_NOTF(action, remoteMessage);
                    break;
                }
            }
        } else {
            NotificationModel notificationModel = FirebaseWrapper.getInstance().getNotificationModelInstance();
            ClientModel clientModel = FirebaseWrapper.getInstance().getClientModelInstance();
            if (remoteMessage.getData().size() > 0) {
                notificationModel.title = remoteMessage.getData().containsKey("title") ? remoteMessage.getData().get("title") : FirebaseConstant.Empty;
                notificationModel.body = remoteMessage.getData().containsKey("body") ? remoteMessage.getData().get("body") : FirebaseConstant.Empty;
                notificationModel.clientId = Long.parseLong(remoteMessage.getData().containsKey("clientId") ? remoteMessage.getData().get("clientId") : "0");
                notificationModel.clientName = remoteMessage.getData().containsKey("clientName") ? remoteMessage.getData().get("clientName") : FirebaseConstant.Empty;
                notificationModel.clientPhone = remoteMessage.getData().containsKey("clientPhone") ? remoteMessage.getData().get("clientPhone") : FirebaseConstant.Empty;
                notificationModel.clientDeviceToken = remoteMessage.getData().containsKey("clientDeviceToken") ? remoteMessage.getData().get("clientDeviceToken") : FirebaseConstant.Empty;
                notificationModel.clientImageUrl = remoteMessage.getData().containsKey("clientImageUrl") ? remoteMessage.getData().get("clientImageUrl") : FirebaseConstant.Empty;
                notificationModel.clientRatting = remoteMessage.getData().containsKey("clientRatting") ? remoteMessage.getData().get("clientRatting") : FirebaseConstant.Empty;
                notificationModel.riderId = Long.parseLong(remoteMessage.getData().containsKey("riderId") ? remoteMessage.getData().get("riderId") : "0");
                notificationModel.sourceName = remoteMessage.getData().containsKey("sourceName") ? remoteMessage.getData().get("sourceName") : FirebaseConstant.Empty;
                notificationModel.destinationName = remoteMessage.getData().containsKey("destinationName") ? remoteMessage.getData().get("destinationName") : FirebaseConstant.Empty;
                notificationModel.shortestTime = remoteMessage.getData().containsKey("shortestTime") ? remoteMessage.getData().get("shortestTime") : FirebaseConstant.Empty;
                notificationModel.shortestDistance = remoteMessage.getData().containsKey("shortestDistance") ? remoteMessage.getData().get("shortestDistance") : FirebaseConstant.Empty;
                notificationModel.sourceLatitude = Double.parseDouble(remoteMessage.getData().containsKey("sourceLatitude") ? remoteMessage.getData().get("sourceLatitude") : "0");
                notificationModel.sourceLongitude = Double.parseDouble(remoteMessage.getData().containsKey("sourceLongitude") ? remoteMessage.getData().get("sourceLongitude") : "0");
                notificationModel.destinationLatitude = Double.parseDouble(remoteMessage.getData().containsKey("destinationLatitude") ? remoteMessage.getData().get("destinationLatitude") : "0");
                notificationModel.destinationLongitude = Double.parseDouble(remoteMessage.getData().containsKey("destinationLongitude") ? remoteMessage.getData().get("destinationLongitude") : "0");
                notificationModel.totalCost = Long.parseLong(remoteMessage.getData().containsKey("totalCost") ? remoteMessage.getData().get("totalCost") : "0");
                notificationModel.discountID = Long.parseLong(remoteMessage.getData().containsKey("discountID") ? remoteMessage.getData().get("discountID") : "0");
                notificationModel.time = Long.parseLong(remoteMessage.getData().containsKey("time") ? remoteMessage.getData().get("time") : "0");
                clientModel.ClientID = notificationModel.clientId;
                clientModel.FullName = notificationModel.clientName;
                clientModel.PhoneNumber = Long.parseLong(notificationModel.clientPhone);
                clientModel.DeviceToken = notificationModel.clientDeviceToken;

            }

            String picture = notificationModel.clientImageUrl;

            Bitmap bmp;
            try {
                if(picture.equals("users/default.png")){
                    bmp = Picasso.with(getApplicationContext()).load(R.drawable.profile_image).get();
                }
                else {
                    bmp = Picasso.with(getApplicationContext()).load(picture).get();
                }

                AppConstant.SHOW_ACTIVITY_FOR_ACCEPT_AND_REJECT = true;
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(FirebaseConstant.RIDE_NOTIFICATION, FirebaseConstant.RIDE_NOTIFICATION);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.siren);

//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(0, builder.build());
                int notifyID = 1;
                String CHANNEL_ID = "my_channel_01";// The id of the channel.
                CharSequence name = "Arif";// The user-visible name of the channel.
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                }

                Notification builder =
                        new NotificationCompat.Builder(this)
                                .setSound(sound)
                                .setContentTitle("Found a Ride !!")
                                .setContentText(notificationModel.totalCost+"Tk")
                                .setAutoCancel(true)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setLargeIcon(bmp)
                                .setStyle(new NotificationCompat
                                        .BigTextStyle()
                                        .bigText(notificationModel.sourceName +"\n"+"To"+"\n"+
                                                notificationModel.destinationName+"\n"))
                                .setWhen(System.currentTimeMillis())
                                .addAction(R.drawable.ic_google_map, "Tap to view details", pendingIntent)
                                .setContentIntent(pendingIntent)
                                .setChannelId(CHANNEL_ID).build();



                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mNotificationManager.createNotificationChannel(mChannel);
                }

// Issue the notification.
                mNotificationManager.notify(1 , builder);

                Log.d(FirebaseConstant.NOTIFICATION_MESSAGE, FirebaseConstant.NOTIFICATION_MESSAGE);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onDeletedMessages() {

    }

    private void RIDE_CANCEL_BY_CLIENT_NOTF(int action, RemoteMessage remoteMessage) {
        if(remoteMessage.getData().size() > 0){

            if(remoteMessage.getData().containsKey(AppConstant.CLIENT_ID)){
                long clientId = Long.parseLong(remoteMessage.getData().get(AppConstant.CLIENT_ID));
            }
            /*Your Own Pending Intent*/
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            this.Notify(AppConstant.CANCEL_RIDE_TITLE, AppConstant.CANCEL_RIDE_BODY, pendingIntent);

        }
    }

    private void Notify(String Title, String Body, PendingIntent pendingIntent){

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "Arif";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        }

        Notification builder =
                new NotificationCompat.Builder(this)
                        .setSound(sound)
                        .setContentTitle(Title)
                        .setContentText(Body)
                        .setAutoCancel(true)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntent)
                        .setChannelId(CHANNEL_ID).build();



        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(mChannel);
        }

// Issue the notification.
        mNotificationManager.notify(1 , builder);

    }
}
