package __Firebase.FirebaseResponse;

/**
 * Created by User on 12/2/2017.
 */

public class NotificationModel {

    public String title, body, clientName, clientPhone, sourceName, destinationName;
    public long clientId;
    public double sourceLatitude, sourceLongitude, destinationLatitude, destinationLongitude;

    public NotificationModel() {
    }

    public NotificationModel(String title, String body, String clientName, String clientPhone, String sourceName, String destinationName, long clientId, double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude) {
        this.title = title;
        this.body = body;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.sourceName = sourceName;
        this.destinationName = destinationName;
        this.sourceLatitude = sourceLatitude;
        this.sourceLongitude = sourceLongitude;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
    }
}
