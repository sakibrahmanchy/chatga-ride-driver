package __Firebase.FirebaseUtility;

import android.util.Pair;

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

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
