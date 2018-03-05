package com.chaatgadrive.arif.chaatgadrive.InternetConnection;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Arif on 3/6/2018.
 */

public class BackgroundConnectionService extends Service {

    private Handler handler = new Handler();
    private ConnectionCheck connectionCheck = new ConnectionCheck(this);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        return Service.START_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

              if(!connectionCheck.isGpsEnable() || !connectionCheck.isGpsEnable()){
                  Intent intent1 = new Intent(BackgroundConnectionService.this,InternetCheckActivity.class);
                  startActivity(intent1);
                  stopSelf();
              }


            }
        };
        handler.postDelayed(runnable, 2000);
        return null;
    }
}
