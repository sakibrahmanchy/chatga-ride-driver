package __Firebase.Exception;

import android.util.Log;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;
import com.crashlytics.android.Crashlytics;

import __Firebase.FirebaseUtility.FirebaseConstant;

/**
 * Created by User on 3/22/2018.
 */

public class FabricExceptionLog {

    public FabricExceptionLog() {
    }

    public static void sendLogToFabric(boolean isLogEnable, String... tagMessage) {
        String tagClass = tagMessage.length > 1 ? tagMessage[0] : FirebaseConstant.NO_TAG_CLASS_FOUND;
        String message = tagMessage.length > 2 ? tagMessage[1] : FirebaseConstant.NO_TAG_MESSAGE_FOUND;

        if (isLogEnable && AppConstant.IS_LOG_ENABLE) {
            Log.d(tagClass, message);
        }

        Throwable throwable = new Exception(message);
        Crashlytics.logException(throwable);
    }

    public static void sendLogToFabric(String message) {
        Throwable throwable = new Exception(message);
        Crashlytics.logException(throwable);
    }

    public static void printLog(String... tagMessage) {
        String tagClass = tagMessage.length > 1 ? tagMessage[0] : FirebaseConstant.NO_TAG_CLASS_FOUND;
        String message = tagMessage.length > 2 ? tagMessage[1] : FirebaseConstant.NO_TAG_MESSAGE_FOUND;

        if (AppConstant.IS_LOG_ENABLE) {
            Log.d(tagClass, message);
        }
    }
}
