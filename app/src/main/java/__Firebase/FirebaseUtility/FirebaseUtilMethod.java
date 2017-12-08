package __Firebase.FirebaseUtility;

import android.util.Pair;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseUtilMethod {

    public FirebaseUtilMethod(){
    }

    public static double distance(Pair<Double, Double> Source, Pair<Double, Double> Destination) {
        double theta = Source.second - Destination.second;
        double dist = Math.sin(deg2rad(Source.first))
                * Math.sin(deg2rad(Destination.first))
                + Math.cos(deg2rad(Source.first))
                * Math.cos(deg2rad(Destination.first))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
