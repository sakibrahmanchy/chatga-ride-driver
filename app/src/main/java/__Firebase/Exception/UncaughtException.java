package __Firebase.Exception;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

import __Firebase.FirebaseUtility.FirebaseConstant;

/**
 * Created by User on 3/22/2018.
 */

public class UncaughtException implements Thread.UncaughtExceptionHandler{

    public UncaughtException(){ }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.d(FirebaseConstant.UNCAUGHT_EXCEPTION, "Name: " + thread.getName() + " Message: " + throwable.getMessage());
        Crashlytics.logException(throwable);
    }
}
