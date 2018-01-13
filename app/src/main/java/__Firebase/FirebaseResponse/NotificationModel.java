package __Firebase.FirebaseResponse;

import __Firebase.FirebaseUtility.FirebaseConstant;

/**
 * Created by User on 12/2/2017.
 */

public class NotificationModel {

    public String title, body, clientName, clientPhone, sourceName, destinationName, clientDeviceToken;
    public long clientId;
    public long riderId;
    public double sourceLatitude, sourceLongitude, destinationLatitude, destinationLongitude;
    public String shortestTime;
    public String shortestDistance;

    public NotificationModel() {
    }

    public NotificationModel(String title, String body, String clientName, String clientPhone, String sourceName, String destinationName, long clientId, long riderId, double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude, String clientDeviceToken, String shortestTime, String shortestDistance) {
        this.title = title;
        this.body = body;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.riderId = riderId;
        this.sourceName = sourceName;
        this.destinationName = destinationName;
        this.sourceLatitude = sourceLatitude;
        this.sourceLongitude = sourceLongitude;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.clientDeviceToken = clientDeviceToken;
        this.shortestTime = shortestTime;
        this.shortestDistance = shortestDistance;
    }

    public void ClearData(){
        this.title = FirebaseConstant.Empty;
        this.body = FirebaseConstant.Empty;
        this.clientId = FirebaseConstant.UNKNOWN;
        this.clientName = FirebaseConstant.Empty;
        this.clientPhone = FirebaseConstant.Empty;
        this.riderId = FirebaseConstant.UNKNOWN;
        this.sourceName = FirebaseConstant.Empty;
        this.destinationName = FirebaseConstant.Empty;
        this.sourceLatitude = FirebaseConstant.UNKNOWN;
        this.sourceLongitude = FirebaseConstant.UNKNOWN;
        this.destinationLatitude = FirebaseConstant.UNKNOWN;
        this.destinationLongitude = FirebaseConstant.UNKNOWN;
        this.clientDeviceToken = FirebaseConstant.Empty;
        this.shortestTime = FirebaseConstant.Empty;
        this.shortestDistance = FirebaseConstant.Empty;
    }
}
