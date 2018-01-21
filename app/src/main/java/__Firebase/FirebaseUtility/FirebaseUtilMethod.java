package __Firebase.FirebaseUtility;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.time.TimeTCPClient;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import __Firebase.FirebaseRequest.GetCurrentTimeAndDate;
import __Firebase.ICallbacklisteners.ICallBackCurrentServerTime;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseUtilMethod {

    public FirebaseUtilMethod() {
    }

    public static boolean IsEmptyOrNull(String data) {
        if (data == null || data.trim().isEmpty()) return true;
        return false;
    }

    public static boolean getNetworkTime(final int type, final Context context, final ICallBackCurrentServerTime iCallBackCurrentServerTime) {
        GetCurrentTimeAndDate pendingTask = new GetCurrentTimeAndDate(type, context, iCallBackCurrentServerTime);
        pendingTask.execute();
        return true;
    }
}
