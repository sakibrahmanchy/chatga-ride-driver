package Firebase.FirebaseResponse;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;

import Firebase.Callbacklisteners.CallBackListener;
import Firebase.FirebaseModel.RiderModel;
import Firebase.FirebaseReqest.GetCurrentClient;
import Firebase.FirebaseUtility.FirebaseConstant;
import Firebase.FirebaseWrapper;

/**
 * Created by User on 11/16/2017.
 */

public class FirebaseResponse {

    public FirebaseResponse(){
    }

    public void ResponseUpdateLocation(final RiderModel Rider, final CallBackListener callBackListener){

        Thread thread = new Thread(){
            @Override
            public void run(){
                new ResponseUpdateLocation(Rider, callBackListener);
            }
        };
        thread.start();
    }
}
