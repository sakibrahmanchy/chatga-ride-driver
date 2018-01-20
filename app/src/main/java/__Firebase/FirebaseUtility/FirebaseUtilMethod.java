package __Firebase.FirebaseUtility;

import android.util.Log;
import android.util.Pair;

import com.chaatgadrive.arif.chaatgadrive.AppConstant.AppConstant;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import __Firebase.ICallbacklisteners.ICallBackCurrentServerTime;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseUtilMethod {

    public FirebaseUtilMethod(){
    }

    public static boolean IsEmptyOrNull(String data) {
        if (data == null || data.trim().isEmpty()) return true;
        return false;
    }

    public static boolean getNetworkTime(final ICallBackCurrentServerTime iCallBackCurrentServerTime) throws IOException {

        final TimeInfo[] timeInfo = {null};
        Thread thread = new Thread(){
            @Override
            public void run(){
                NTPUDPClient timeClient = new NTPUDPClient();
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getByName(FirebaseConstant.TIME_SERVER);
                    timeInfo[0] = timeClient.getTime(inetAddress);
                    long value = timeInfo[0].getMessage().getReceiveTimeStamp().getTime();
                    iCallBackCurrentServerTime.OnResponseServerTime(value);

                    Log.d("CURRENT_TIME", String.valueOf(timeInfo[0].getMessage().getReceiveTimeStamp().getTime()));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        return true;
    }
}
