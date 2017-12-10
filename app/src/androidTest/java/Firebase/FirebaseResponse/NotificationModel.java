package Firebase.FirebaseResponse;

import android.telecom.Call;

import Firebase.Callbacklisteners.CallBackListener;

/**
 * Created by User on 12/2/2017.
 */

public class NotificationModel {

    public String title, body;
    public long clientId;
    public double sourceLatitude, sourceLongitude, destinationLatitude, destinationLongitude;

    public NotificationModel(){}

    public NotificationModel(String title, String body, long clientId, double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude){
        this.title = title;
        this.body = body;
        this.clientId = clientId;
        this.sourceLatitude = sourceLatitude;
        this.sourceLongitude = sourceLongitude;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
    }
}
