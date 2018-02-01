package __Firebase.FirebaseUtility;

import __Firebase.FirebaseWrapper;

/**
 * Created by User on 2/1/2018.
 */

public class ClearData {
    public ClearData(){ }

    public static void ClearAllData(){
        ClearClientModel();
        ClearHistory();
    }

    private static void ClearClientModel(){
        FirebaseWrapper.getInstance().getClientModelInstance().ClearData();
    }

    private static void ClearHistory(){
        FirebaseWrapper.getInstance().getRidingHistoryModelModelInstance().ClearData();
    }
}
