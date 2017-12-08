package __Firebase.FirebaseResponse;

import __Firebase.Callbacklisteners.CallBackListener;
import __Firebase.FirebaseModel.RiderModel;

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
