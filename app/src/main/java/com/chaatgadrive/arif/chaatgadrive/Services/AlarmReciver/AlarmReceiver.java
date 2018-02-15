package com.chaatgadrive.arif.chaatgadrive.Services.AlarmReciver;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;

@SuppressWarnings("ALL")
public class AlarmReceiver extends WakefulBroadcastReceiver {
    Ringtone ringtone;

    @Override
    public void onReceive(final Context context, Intent intent) {
      //  MainActivity.getTextView2().setText("Enough Rest. Do Work Now!");
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();
    }


}
